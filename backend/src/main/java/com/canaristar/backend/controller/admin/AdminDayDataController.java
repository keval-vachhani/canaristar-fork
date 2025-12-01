package com.canaristar.backend.controller.admin;

import com.canaristar.backend.entity.DayData;
import com.canaristar.backend.service.dayDataService.DayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/day-data")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDayDataController {

    @Autowired
    private DayDataService dayDataService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllDayData(){
        List<DayData> list = dayDataService.findAll();

        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/today")
    public ResponseEntity<DayData> getTodayData() {
        DayData data = dayDataService.getTodayData();

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{date}")
    public ResponseEntity<DayData> getDataByDate(@PathVariable String date) {
        DayData data = dayDataService.getDataByDate(date);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/range")
    public ResponseEntity<?> getRange(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(dayDataService.getDataBetween(startDate, endDate));
    }

    @PostMapping("/reset-today")
    public ResponseEntity<?> resetToday() {
        dayDataService.resetToday();

        return ResponseEntity.ok("DayData reset successfully");
    }
}
