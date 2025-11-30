package com.canaristar.backend.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "carousel")
@Data
public class Carousel {

    @Id
    private String id;
    private String imageUrl;
    private String redirectUrl;
    @NotBlank
    private String title;
    private String description;
    private boolean featured = false;
    private LocalDateTime createdAt;
}
