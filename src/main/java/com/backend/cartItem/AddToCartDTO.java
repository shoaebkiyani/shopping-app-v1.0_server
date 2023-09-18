package com.backend.cartItem;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AddToCartDTO {
    private int quantity;
    private double totalPrice;
    private Date createdAt;
}
