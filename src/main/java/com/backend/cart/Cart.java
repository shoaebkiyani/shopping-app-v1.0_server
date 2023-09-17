package com.backend.cart;

import com.backend.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
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

    private int quantity;

    private double totalPrice;

    private Date createdAt;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Cart(int quantity, double totalPrice, Date createdAt, Product product) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.product = product;
    }
}
