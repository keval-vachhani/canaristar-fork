package com.canaristar.backend.controller;

import com.canaristar.backend.entity.User;
import com.canaristar.backend.enums.Role;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.request.VerifyOtpRequest;
import com.canaristar.backend.response.AuthResponse;
import com.canaristar.backend.service.email.EmailService;
import com.canaristar.backend.service.otp.OtpService;
import com.canaristar.backend.service.user.UserService;
import com.canaristar.backend.utils.jwt.JwtProvider;
import com.canaristar.backend.utils.otp.OTPUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws MessagingException {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "User already exists", null));
        }

        String otp = OTPUtils.generateOTP();
        otpService.saveOtp(user.getEmail(), otp);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        otpService.saveUnverifiedUser(user.getEmail(), user);

        emailService.sendVerificationOtpMail(user.getEmail(), otp);
        return ResponseEntity.ok(new AuthResponse(true, "OTP sent successfully", null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {

        String email = request.getEmail();
        String otp = request.getOtp();

        String storedOtp = otpService.getOtp(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        User user = otpService.getUnverifiedUser(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("No user found for verification");
        }

        User savedUser = userService.saveUser(user);
        otpService.removeOtp(email);
        otpService.removeUnverifiedUser(email);

        return ResponseEntity.ok(new AuthResponse(true, savedUser.getId(), null));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthRequest request,
                                    HttpServletRequest httpRequest,
                                    HttpServletResponse httpResponse) {

        String existingToken = null;

        if (httpRequest.getCookies() != null) {
            for (Cookie c : httpRequest.getCookies()) {
                if (c.getName().equals("jwt")) {
                    existingToken = c.getValue();
                }
            }
        }

        if (existingToken != null && jwtProvider.validateToken(existingToken)) {
            return ResponseEntity.ok(new AuthResponse(true, "Already authenticated", existingToken));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtProvider.generateToken(authentication);

        Optional<User> optionalUser = userService.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()) {
            return  ResponseEntity.badRequest().body(new AuthResponse(false, "User not found", null));
        }

        User user  = optionalUser.get();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setAttribute("SameSite", "Lax");

        httpResponse.addCookie(cookie);

        return ResponseEntity.ok(new AuthResponse(true, user.getId(), "Cookie Set"));
    }

}
