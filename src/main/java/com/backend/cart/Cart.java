package com.backend.cart;

import com.backend.cartItem.CartItem;
import com.backend.placeOrder.PlaceOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity(name = "cart")
@Table(name = "cart")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private PlaceOrder placeOrder;

    public Cart(List<CartItem> cartItems, PlaceOrder placeOrder) {
        this.cartItems = cartItems;
        this.placeOrder = placeOrder;
    }
}
