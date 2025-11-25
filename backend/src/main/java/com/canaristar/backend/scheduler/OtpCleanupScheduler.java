package com.canaristar.backend.scheduler;

import com.canaristar.backend.service.otp.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OtpCleanupScheduler {

    @Autowired
    private OtpService otpService;

    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredData() {

        if (otpService.getOtpMap().isEmpty() && otpService.getUnverifiedUsersMap().isEmpty()) {
            return;
        }

        long now = System.currentTimeMillis();

        otpService.getOtpMap().keySet().removeIf(email ->
                now - otpService.getOtpTime(email) > 2 * 60 * 1000
        );

        otpService.getUnverifiedUsersMap().keySet().removeIf(email ->
                now - otpService.getUnverifiedTime(email) > 10 * 60 * 1000
        );
    }
}
