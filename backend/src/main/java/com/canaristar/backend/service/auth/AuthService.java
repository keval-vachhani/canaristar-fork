package com.canaristar.backend.service.auth;

import com.canaristar.backend.entity.user.User;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.request.VerifyOtpRequest;
import com.canaristar.backend.response.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponse signup(User user) throws MessagingException;
    AuthResponse verifyOtp(VerifyOtpRequest request);
    AuthResponse signin(AuthRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);
    AuthResponse resendOtp(String email) throws MessagingException;
}
