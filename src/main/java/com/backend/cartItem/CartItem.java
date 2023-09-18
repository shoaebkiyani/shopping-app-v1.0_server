package com.backend.cartItem;

import com.backend.cart.Cart;
import com.backend.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity(name = "cart_item")
@Table(name = "cart_item")
@Data
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID cartItemId;

    private int quantity;

    private double totalPrice;

    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItem(int quantity, double totalPrice, Date createdAt, Cart cart, Product product) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.cart = cart;
        this.product = product;
    }
}
