package com.backend.cart;

import com.backend.product.ProductRepository;
import com.backend.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"https://techzoneapi.netlify.app/", "http://localhost:5173/"})
@RequestMapping("api/v1")
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getAllItems() {
        return new ResponseEntity<List<Cart>>(cartService.getAllItems(), HttpStatus.OK);
    }

    @PostMapping("/cart/{productId}")
    public Cart addToCart(@RequestBody AddToCartDTO quantity, @PathVariable(name = "productId") UUID productId) {
        return cartService.addToCart(quantity, productId);
    }

    @DeleteMapping("/cart/{itemId}")
    public void deleteCartItem(@PathVariable UUID itemId) {
        cartService.deleteCartItem(itemId);
    }
}
