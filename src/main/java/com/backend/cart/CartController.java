package com.backend.cart;

import com.backend.cartItem.AddToCartDTO;
import com.backend.cartItem.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://techzoneapi.netlify.app/", "http://localhost:5173/"})
@RequestMapping("api/v1")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public List<CartItem> getAllItems() {
        return cartService.getAllItems();
    }

    @PostMapping("/cart/create-cart")
    public Cart createCart() {
        return cartService.createCart();
    }

    @PostMapping("/cart/add-to-cart/{productId}")
    public CartItem addToCart(@RequestBody AddToCartDTO quantity,
                              @PathVariable UUID productId) {
        return cartService.addToCart(quantity, productId);
    }

    @PutMapping("/cart/{itemId}")
    public CartItem updateCartItem(@RequestBody AddToCartDTO quantity, @PathVariable UUID itemId) {
        return cartService.updateCartItem(quantity, itemId);
    }

    @DeleteMapping("/cart/delete-item/{itemId}")
    public List<CartItem> deleteCartItem(@PathVariable UUID itemId) {
        return cartService.deleteCartItem(itemId);
    }
}
