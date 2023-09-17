package com.backend.cart;

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
    private ProductRepository productRepository;

    public List<Cart> getAllItems() {
        return cartRepository.findAll();
    }

    public Cart addToCart(AddToCartDTO addToCartDTO, UUID productId) throws RuntimeException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product could not be found"));
        if (!product.isInStock()) {
            throw new ProductNotFoundException("Product could not be found");
        }

        addToCartDTO.setQuantity(addToCartDTO.getQuantity());
        if (addToCartDTO.getQuantity() < 1) {
            throw new QuantityLimitNotFoundException("Quantity should be at least 1");
        }
        addToCartDTO.setTotalPrice(addToCartDTO.getQuantity() * product.getPrice());
        addToCartDTO.setCreatedAt(new Date());
        Cart cart = new Cart(addToCartDTO.getQuantity(), addToCartDTO.getTotalPrice(), addToCartDTO.getCreatedAt(), product);
        return cartRepository.save(cart);
    }

    public void deleteCartItem(UUID itemId) {
        cartRepository.deleteById(itemId);
    }
}
