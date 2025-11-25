package com.canaristar.backend.service.auth;

import com.canaristar.backend.entity.user.User;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${app.env}")
    private String appEnv;

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

    @Override
    public AuthResponse signup(User user) throws MessagingException {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return new AuthResponse(false, "User already exists", null);
        }

        String otp = OTPUtils.generateOTP();
        otpService.saveOtp(user.getEmail(), otp);

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        otpService.saveUnverifiedUser(user.getEmail(), user);

        emailService.sendVerificationOtpMail(user.getEmail(), otp);
        return new AuthResponse(true, "OTP sent successfully", null);
    }

    @Override
    public AuthResponse verifyOtp(VerifyOtpRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();

        String storedOtp = otpService.getOtp(email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return new AuthResponse(false, "Invalid OTP", null);
        }

        User user = otpService.getUnverifiedUser(email);
        if (user == null) {
            return new AuthResponse(false, "No user found for verification", null);
        }

        User savedUser = userService.saveUser(user);
        otpService.removeOtp(email);
        otpService.removeUnverifiedUser(email);

        return new AuthResponse(true, savedUser.getId(), null);
    }

    @Override
    public AuthResponse signin(AuthRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

        System.out.println(appEnv);

        String existingToken = null;
        if (httpRequest.getCookies() != null) {
            for (Cookie c : httpRequest.getCookies()) {
                if (c.getName().equals("jwt")) {
                    existingToken = c.getValue();
                }
            }
        }

        if (existingToken != null && jwtProvider.validateToken(existingToken)) {
            return new AuthResponse(true, "Already authenticated", null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtProvider.generateToken(authentication);

        User user = userService.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return new AuthResponse(false, "User not found", null);
        }

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setAttribute("SameSite", "None");

        if (appEnv.equals("dev")) {
            cookie.setSecure(false);
        } else {
            cookie.setSecure(true);
        }

        httpResponse.addCookie(cookie);

        return new AuthResponse(true, user.getId(), null);
    }

    @Override
    public AuthResponse resendOtp(String email) throws MessagingException {
        String otp = otpService.getOtp(email);

        if (otp != null) {
            otpService.removeOtp(email);
        }

        otp = OTPUtils.generateOTP();
        otpService.saveOtp(email, otp);

        emailService.sendVerificationOtpMail(email, otp);

        return new AuthResponse(true, "OTP sent successfully", null);
    }
}
