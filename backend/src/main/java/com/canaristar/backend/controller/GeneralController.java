package com.canaristar.backend.controller;

import com.canaristar.backend.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
public class GeneralController {

    @GetMapping("/")
    public ResponseEntity<GeneralResponse> live() {
        ZoneId indiaZone = ZoneId.of("Asia/Kolkata");
        LocalDateTime indiaTime = LocalDateTime.now(indiaZone);
        String formattedTime = indiaTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        GeneralResponse gr = new GeneralResponse("live at " + formattedTime);
        return new ResponseEntity<>(gr, HttpStatus.OK);
    }


}
