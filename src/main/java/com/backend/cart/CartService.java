package com.backend.cart;

import com.backend.cartItem.AddToCartDTO;
import com.backend.cartItem.CartItem;
import com.backend.cartItem.CartItemRepository;
import com.backend.exceptions.productNotFoundException.ProductNotFoundException;
import com.backend.exceptions.quantityLimitExceptions.QuantityLimitNotFoundException;
import com.backend.product.Product;
import com.backend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public List<CartItem> getAllItems(UUID cartId) throws RuntimeException {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ProductNotFoundException("Cart could not be found"));
        return cart.getCartItems();
    }

    public CartItem addToCart(AddToCartDTO addToCartDTO, UUID cartId, UUID productId) throws RuntimeException {
        Cart cart;
        if (cartId == null) {
            cart = createCart();
        } else {
            cart = cartRepository.findById(cartId).
                    orElseThrow(() -> new RuntimeException("Cart not found"));
        }

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.isInStock()) {
            throw new ProductNotFoundException("Product is Out-of-Stock");
        }
        addToCartDTO.setQuantity(addToCartDTO.getQuantity());
        if (addToCartDTO.getQuantity() < 1) {
            throw new QuantityLimitNotFoundException("Quantity should be at least 1");
        }
        addToCartDTO.setTotalPrice(addToCartDTO.getQuantity() * product.getPrice());
        addToCartDTO.setCreatedAt(new Date());

        CartItem cartItem = new CartItem(addToCartDTO.getQuantity(), addToCartDTO.getTotalPrice(),
                addToCartDTO.getCreatedAt(), cart, product);
        return cartItemRepository.save(cartItem);
    }
}
