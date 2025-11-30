package com.canaristar.backend.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "product-data")
@Data
public class ProductData {

    @Id
    private String id;
    @NotBlank
    private String productId;
    private int productViews = 0;
    private List<String> ordersId =  new ArrayList<>();
    private float rating = 0.0f;
    private float totalRatingSum = 0.0f;
    private int totalRatingCount = 0;
}