package com.canaristar.backend.entity.cart;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private int quantity;
}
