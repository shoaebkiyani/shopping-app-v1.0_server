package com.backend.cartItem;

import com.backend.cart.Cart;
import com.backend.product.Product;

public class AddToCartMapper {
    public CartItem toCartItem(AddToCartDTO cartItem, Product product, Cart cart) {
        return new CartItem(cartItem.getQuantity(), cartItem.getTotalPrice(), cartItem.getCreatedAt(), cart, product);
    }
}
