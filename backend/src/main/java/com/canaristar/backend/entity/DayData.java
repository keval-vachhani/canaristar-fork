package com.canaristar.backend.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document(collection = "day-data")
@Data
public class DayData {

    @Id
    private String id;
    private String date;
    private int visitors;
    private double revenue;
    private int newUsers;
    private List<String> orderIds;
    private Map<String, Integer> productViews;
}
