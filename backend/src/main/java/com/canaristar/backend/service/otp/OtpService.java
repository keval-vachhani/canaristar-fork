package com.canaristar.backend.service.otp;

import com.canaristar.backend.entity.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, User> unverifiedUsers = new HashMap<>();

    public void saveOtp(String email, String otp) {
        otpStorage.put(email, otp);
    }

    public String getOtp(String email) {
        return otpStorage.get(email);
    }

    public void removeOtp(String email) {
        otpStorage.remove(email);
    }

    public void saveUnverifiedUser(String email, User user) {
        unverifiedUsers.put(email, user);
    }

    public User getUnverifiedUser(String email) {
        return unverifiedUsers.get(email);
    }

    public void removeUnverifiedUser(String email) {
        unverifiedUsers.remove(email);
    }
}
