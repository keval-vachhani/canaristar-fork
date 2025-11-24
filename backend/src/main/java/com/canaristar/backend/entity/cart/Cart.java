package com.canaristar.backend.entity.cart;

import com.canaristar.backend.entity.Products;
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
    @NotBlank
    private String email;
    private List<CartItem> cartItems = new ArrayList<>();
}
