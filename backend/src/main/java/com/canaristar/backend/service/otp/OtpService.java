package com.canaristar.backend.service.otp;

import com.canaristar.backend.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpService {

    private final Map<String, String> emailOtpStorage = new HashMap<>();
    private final Map<String, Long> otpTimestamp = new HashMap<>();

    private final Map<String, User> unverifiedUsers = new HashMap<>();
    private final Map<String, Long> unverifiedTimestamp = new HashMap<>();

    private final Map<String, String> tempPasswordStorage = new HashMap<>();

    public void saveOtp(String email, String otp) {
        emailOtpStorage.put(email, otp);
        otpTimestamp.put(email, System.currentTimeMillis());
    }

    public String getOtp(String email) {
        return emailOtpStorage.get(email);
    }

    public Long getOtpTime(String email) {
        return otpTimestamp.get(email);
    }

    public void removeOtp(String email) {
        emailOtpStorage.remove(email);
        otpTimestamp.remove(email);
    }

    public void saveUnverifiedUser(String email, User user) {
        unverifiedUsers.put(email, user);
        unverifiedTimestamp.put(email, System.currentTimeMillis());
    }

    public User getUnverifiedUser(String email) {
        return unverifiedUsers.get(email);
    }

    public Long getUnverifiedTime(String email) {
        return unverifiedTimestamp.get(email);
    }

    public void removeUnverifiedUser(String email) {
        unverifiedUsers.remove(email);
        unverifiedTimestamp.remove(email);
    }

    public void setTempPassword(String email, String password) {
        tempPasswordStorage.put(email, password);
    }

    public String getTempPassword(String email) {
        return tempPasswordStorage.get(email);
    }

    public void removeTempPassword(String email) {
        tempPasswordStorage.remove(email);
    }

    public Map<String, String> getOtpMap() {
        return emailOtpStorage;
    }

    public Map<String, User> getUnverifiedUsersMap() {
        return unverifiedUsers;
    }
}
