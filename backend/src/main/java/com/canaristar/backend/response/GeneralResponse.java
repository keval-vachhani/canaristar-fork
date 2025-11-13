package com.canaristar.backend.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class GeneralResponse {
    private String status;
    private String time;

    public GeneralResponse(String status) {
        this.status = status;
        this.time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }
}
