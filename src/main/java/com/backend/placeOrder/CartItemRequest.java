package com.backend.placeOrder;

import com.backend.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CartItemRequest {
    private UUID cartId;
    private int quantity;
    private double totalPrice;
    private Date createdAt;
    private Product product;
}
