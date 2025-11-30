package com.canaristar.backend.controller;

import com.canaristar.backend.entity.user.User;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.request.VerifyOtpRequest;
import com.canaristar.backend.response.AuthResponse;
import com.canaristar.backend.service.auth.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody User user) throws MessagingException {
        return ResponseEntity.ok(authService.signup(user));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return ResponseEntity.ok(authService.verifyOtp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody AuthRequest request,
                                               HttpServletRequest httpRequest,
                                               HttpServletResponse httpResponse) {
        return ResponseEntity.ok(authService.signin(request, httpRequest, httpResponse));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");

        response.addCookie(cookie);

        return ResponseEntity.ok().body("Logout Successful");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<AuthResponse> resendOtp(@RequestBody String email) throws MessagingException {
        return ResponseEntity.ok(authService.resendOtp(email));
    }
}
