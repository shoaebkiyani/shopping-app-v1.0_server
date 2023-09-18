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

    @GetMapping("/cart/{cartId}")
    public List<CartItem> getAllItems(@PathVariable UUID cartId) {
        return cartService.getAllItems(cartId);
    }

    @PostMapping("/cart")
    public Cart createCart() {
        return cartService.createCart();
    }

    @PostMapping("/cart/add-to-cart/{cartId}/{productId}")
    public CartItem addToCart(@RequestBody AddToCartDTO quantity, @PathVariable UUID cartId,
                              @PathVariable UUID productId
    ) {
        return cartService.addToCart(quantity, cartId, productId);
    }
}
