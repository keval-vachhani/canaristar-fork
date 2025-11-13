package com.canaristar.backend.controller;

import com.canaristar.backend.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class General {

    @GetMapping("/")
    public ResponseEntity<GeneralResponse> live() {
        GeneralResponse gr = new GeneralResponse("live");

        return new ResponseEntity<>(gr, HttpStatus.OK);
    }
}
