package com.canaristar.backend.entity.cart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cart")
@Data
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<CartItem> cartItems = new ArrayList<>();
}
