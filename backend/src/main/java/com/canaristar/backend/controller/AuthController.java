package com.canaristar.backend.controller;

import com.canaristar.backend.entity.User;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.response.AuthResponse;
import com.canaristar.backend.service.email.EmailService;
import com.canaristar.backend.service.otp.OtpService;
import com.canaristar.backend.service.user.UserService;
import com.canaristar.backend.utils.jwt.JwtProvider;
import com.canaristar.backend.utils.otp.OTPUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws MessagingException {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(false, "User already exists", null));
        }

        String otp = OTPUtils.generateOTP();
        otpService.saveOtp(user.getEmail(), otp);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        otpService.saveUnverifiedUser(user.getEmail(), user);

        emailService.sendVerificationOtpMail(user.getEmail(), otp);
        return ResponseEntity.ok(new AuthResponse(true, "OTP sent successfully", null));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String otp, @RequestBody Map<String, String> request) {
        String email = request.get("email");

        String storedOtp = otpService.getOtp(email);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        User user = otpService.getUnverifiedUser(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("No user found for verification");
        }

        userService.saveUser(user);
        otpService.removeOtp(email);
        otpService.removeUnverifiedUser(email);

        return ResponseEntity.ok(new AuthResponse(true, "Registration successful", null));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        String existingToken = httpRequest.getHeader("Authorization");
        System.out.println(existingToken);

//        If a valid JWT is already provided, skip re-login
        if (existingToken != null && jwtProvider.validateToken(existingToken)) {
            String email = jwtProvider.getEmailFromToken(existingToken);
            return ResponseEntity.ok(new AuthResponse(true, "Already authenticated", existingToken));
        }

//        Validate credentials
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

//        Create authentication object for token generation
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
        String token = jwtProvider.generateToken(auth);

        return ResponseEntity.ok(new AuthResponse(true, "Login successful", token));
    }
}
