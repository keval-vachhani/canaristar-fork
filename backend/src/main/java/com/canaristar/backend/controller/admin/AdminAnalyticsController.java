package com.canaristar.backend.controller.admin;

import com.canaristar.backend.entity.DayData;
import com.canaristar.backend.service.dayDataService.DayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/analytics")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAnalyticsController {

    @Autowired
    private DayDataService dayDataService;

    @GetMapping("/revenue/total")
    public ResponseEntity<?> getTotalRevenue() {
        List<DayData> dayData = dayDataService.findAll();
        double totalRevenue = 0;

        for (DayData day : dayData) {
            totalRevenue +=  day.getRevenue();
        }

        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/revenue/between")
    public ResponseEntity<?> getRevenueBetween(@RequestParam String startDate, @RequestParam String endDate) {
        List<DayData> dayData = dayDataService.getDataBetween(startDate, endDate);
        double totalRevenue = 0;

        for (DayData day : dayData) {
            totalRevenue +=  day.getRevenue();
        }

        return ResponseEntity.ok(totalRevenue);
    }
}
