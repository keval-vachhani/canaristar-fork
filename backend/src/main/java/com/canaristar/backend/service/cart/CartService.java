package com.canaristar.backend.service.cart;

import com.canaristar.backend.entity.cart.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Cart getCart(String email);
    Cart saveCart(Cart cart);
    Cart getCartById(String id);
    Cart addItem(String email, String productId, int quantity);
    Cart updateItem(String email, String productId, int quantity);
    Cart removeItem(String email, String productId);
    Cart clearCart(String email);
    void deleteCart(String email);
}
