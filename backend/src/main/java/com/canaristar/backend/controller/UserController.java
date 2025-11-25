package com.canaristar.backend.controller;

import com.canaristar.backend.entity.user.User;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.response.AuthResponse;
import com.canaristar.backend.service.email.EmailService;
import com.canaristar.backend.service.otp.OtpService;
import com.canaristar.backend.service.user.UserService;
import com.canaristar.backend.utils.otp.OTPUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        Optional<User> userOpt = userService.findById(id);

        return userOpt.orElse(null);
    }

    @GetMapping("/get-id")
    public ResponseEntity<?> getIdByEmail(@RequestParam String email) {
        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userOpt.get().getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthResponse> deleteUserById(@PathVariable String id) {
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(userOpt.get());

        return new ResponseEntity<>(
                new AuthResponse(true, "Account deleted.", null),
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> existing = userService.findById(user.getId());

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

//        use cant update this without verification
        user.setEmail(existing.get().getEmail());
        user.setPassword(existing.get().getPassword());
        user.setRole(existing.get().getRole());

        userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> requestResetPassword(@RequestBody AuthRequest request) throws MessagingException {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String otp = OTPUtils.generateOTP();
        otpService.saveOtp(request.getEmail(), otp);
        otpService.setTempPassword(request.getEmail(), request.getPassword());

        emailService.sendVerificationOtpMail(request.getEmail(), otp);
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/password/verify")
    public ResponseEntity<?> verifyPasswordOtp(@RequestParam String email, @RequestParam String otp) {
        String storedOtp = otpService.getOtp(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String newPassword = otpService.getTempPassword(email);
        if (newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body("Invalid Password");
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);

        otpService.removeOtp(email);
        otpService.removeTempPassword(email);

        return ResponseEntity.ok("Password updated");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return ResponseEntity.ok().body("Logout Successful");
    }
}
