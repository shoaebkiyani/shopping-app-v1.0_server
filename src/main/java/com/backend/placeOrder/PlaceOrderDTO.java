package com.backend.placeOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaceOrderDTO {
    private String firstName;
    private String lastName;
    private String address;
    private List<CartItemRequest> cartItems;
}

