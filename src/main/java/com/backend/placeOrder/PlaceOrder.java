package com.backend.placeOrder;

import com.backend.cart.Cart;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "orders")
@Table(name = "orders")
@Data
@NoArgsConstructor
public class PlaceOrder {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID orderId;
    private String firstName;
    private String lastName;
    private String address;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public PlaceOrder(String firstName, String lastName, String address, Cart cart) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.cart = cart;
    }
}
