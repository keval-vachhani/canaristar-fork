package com.canaristar.backend.service.cart;

import com.canaristar.backend.entity.cart.Cart;
import com.canaristar.backend.entity.cart.CartItem;
import com.canaristar.backend.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart getCart(String email) {
        return cartRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(String id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart addItem(String email, String productId, int quantity) {

        // Fetch existing cart or create a new one for first-time users
        Cart cart = cartRepository.findByEmail(email).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setEmail(email);
            cart = cartRepository.save(cart);
        }

        CartItem target = null;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId().equals(productId)) {
                target = item;
                break;
            }
        }

        if (target != null) {
            target.setQuantity(target.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            cart.getCartItems().add(item);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItem(String email, String productId, int quantity) {
        Cart cart = getCart(email);
        if (cart == null) return null;

        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }

        return saveCart(cart);
    }

    @Override
    public Cart removeItem(String email, String productId) {
        Cart cart = getCart(email);
        if (cart == null) return null;

        CartItem target = null;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProductId().equals(productId)) {
                target = item;
                break;
            }
        }

        if (target != null) {
            cart.getCartItems().remove(target);
        }

        return saveCart(cart);
    }

    @Override
    public Cart clearCart(String email) {
        Cart cart = getCart(email);

        if (cart == null) return null;

        cart.getCartItems().clear();

        return saveCart(cart);
    }

    @Override
    public void deleteCart(String email) {
        Cart cart = getCart(email);

        if (cart != null) {
            cartRepository.deleteById(cart.getId());
        }
    }
}
