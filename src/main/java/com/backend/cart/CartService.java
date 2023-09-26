package com.backend.cart;

import com.backend.cartItem.AddToCartDTO;
import com.backend.cartItem.CartItem;
import com.backend.cartItem.CartItemRepository;
import com.backend.exceptions.productNotFoundException.ProductNotFoundException;
import com.backend.exceptions.quantityLimitExceptions.QuantityLimitNotFoundException;
import com.backend.product.Product;
import com.backend.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart createCart() {
        List<Cart> carts = cartRepository.findAll();
        UUID cartId = null;
        if (!carts.isEmpty()) {
            cartId = carts.get(0).getCartId();
            Cart cart = cartRepository.findById(cartId).orElse(null);
            assert cart != null;
            return cartRepository.save(cart);
        }
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public List<Cart> getAllItems() throws RuntimeException {
        List<Cart> cartItemsList = cartRepository.findAll();
        if (cartItemsList.isEmpty()) {
            throw new ProductNotFoundException("Cart could not be found");
        }
        cartItemsList.stream().map(Cart::getCartItems).collect(Collectors.toList());
        return cartItemsList;
    }
//    public List<CartItem> getAllItems() throws RuntimeException {
//        return cartItemRepository.findAll();
//    }

    public CartItem addToCart(AddToCartDTO addToCartDTO, UUID productId) throws RuntimeException {

        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.isInStock()) {
            throw new ProductNotFoundException("Product is Out-of-Stock");
        }
        UUID cartId = null;
        List<Cart> cartList = cartRepository.findAll();
        if (!cartList.isEmpty()) {
            for (int i = 0; i < cartList.size(); i++) {
                cartId = cartList.get(0).getCartId();
                Cart cart = cartRepository.findById(cartId).orElse(null);
                addToCartDTO.setCart(cart);
            }
        } else {
            Cart cart = createCart();
            addToCartDTO.setCart(cart);
        }
        CartItem newCartItem = null;

        List<CartItem> cartItems = addToCartDTO.getCart().getCartItems();
        if (cartContainsProduct(cartItems, product)) {
            for (CartItem cartItem : cartItems) {
                Product productInCart = cartItem.getProduct();
                if (productInCart.equals(product)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setTotalPrice(cartItem.getQuantity() * product.getPrice());
                    return cartItemRepository.save(cartItem);
                }
            }
        } else {
            addToCartDTO.setQuantity(addToCartDTO.getQuantity());
            if (addToCartDTO.getQuantity() < 1) {
                throw new QuantityLimitNotFoundException("Quantity should be at least 1");
            }
            addToCartDTO.setTotalPrice(addToCartDTO.getQuantity() * product.getPrice());
            addToCartDTO.setCreatedAt(new Date());
            addToCartDTO.setQuantity(addToCartDTO.getQuantity());
            addToCartDTO.setTotalPrice(addToCartDTO.getQuantity() * product.getPrice());
            addToCartDTO.setCreatedAt(addToCartDTO.getCreatedAt());

            CartItem updatedCartItem = new CartItem(addToCartDTO.getQuantity(), addToCartDTO.getTotalPrice(),
                    addToCartDTO.getCreatedAt(), addToCartDTO.getCart(), product);

            newCartItem = cartItemRepository.save(updatedCartItem);
        }
        return newCartItem;
    }

    public boolean cartContainsProduct(List<CartItem> cartItems, Product productToCheck) {
        for (CartItem cartItem : cartItems) {
            Product productInCart = cartItem.getProduct();
            if (productInCart.equals(productToCheck)) {
                return true;
            }
        }
        return false;
    }

    public List<CartItem> deleteCartItem(UUID itemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(itemId);
        if (cartItem.isPresent()) {
            CartItem cartItem1 = cartItem.get();
            cartItemRepository.delete(cartItem1);

            return cartItem.get().getCart().getCartItems();
        } else {
            // Handle the case where the CartItem with the given ID doesn't exist
            throw new EntityNotFoundException("CartItem not found with ID: " + itemId);
        }
    }
}
