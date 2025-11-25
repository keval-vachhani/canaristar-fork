package com.canaristar.backend.controller;

import com.canaristar.backend.entity.cart.Cart;
import com.canaristar.backend.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable String id) {
        return new ResponseEntity<>(cartService.getCartById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable String userId) {
        Cart cart = cartService.getCart(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartService.saveCart(cart);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam int quantity) {

        Cart cart = cartService.addItem(userId, productId, quantity);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(
            @RequestParam String userId,
            @RequestParam String productId,
            @RequestParam int quantity) {

        Cart cart = cartService.updateItem(userId, productId, quantity);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItem(
            @RequestParam String userId,
            @RequestParam String productId
    ) {

        Cart cart = cartService.removeItem(userId, productId);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId) {
        Cart cart = cartService.clearCart(userId);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteCart(@PathVariable String userId) {
        cartService.deleteCart(userId);

        return new ResponseEntity<>("Cart deleted", HttpStatus.OK);
    }
}
