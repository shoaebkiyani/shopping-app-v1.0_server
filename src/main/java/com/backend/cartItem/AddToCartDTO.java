package com.backend.cartItem;

import com.backend.cart.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AddToCartDTO {
    private Cart cart;
    private int quantity;
    private double totalPrice;
    private Date createdAt;
}
