package com.canaristar.backend.entity;

import com.canaristar.backend.enums.ProductCategory;
import com.canaristar.backend.enums.ProductSubCategory;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "products")
@Data
public class Products {

    @Id
    private String id;

    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @NotNull
    private ProductCategory productCategory;

    @NotNull
    private ProductSubCategory productSubCategory;

    @Positive
    private float sellingPrice = 0f;

    @Positive
    private float mrpPrice = 0f;

    @Positive
//    in grams
    private float weight = 0f;

    private List<String> imageUrls = new LinkedList<>();

    private boolean active = true;
    private boolean featured = false;
}
