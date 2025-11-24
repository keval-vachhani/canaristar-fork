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

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getCartByEmail(@PathVariable String email) {
        Cart cart = cartService.getCart(email);
        if (cart == null) {
            cart = new Cart();
            cart.setEmail(email);
            cart = cartService.saveCart(cart);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(
            @RequestParam String email,
            @RequestParam String productId,
            @RequestParam int quantity) {

        Cart cart = cartService.addItem(email, productId, quantity);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(
            @RequestParam String email,
            @RequestParam String productId,
            @RequestParam int quantity) {

        Cart cart = cartService.updateItem(email, productId, quantity);
        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItem(
            @RequestParam String email,
            @RequestParam String productId) {

        Cart cart = cartService.removeItem(email, productId);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear/{email}")
    public ResponseEntity<?> clearCart(@PathVariable String email) {
        Cart cart = cartService.clearCart(email);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteCart(@PathVariable String email) {
        cartService.deleteCart(email);
        return new ResponseEntity<>("Cart deleted", HttpStatus.OK);
    }
}
