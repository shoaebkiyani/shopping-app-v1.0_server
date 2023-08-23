package com.backend.product;

import com.backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "product")
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String title;
    private String description;
    private String imageURL;
    private double price;
    private int stock;
    private int quantity;


    @ManyToOne(optional = false)
    private Category category;

    public Product(String title, String description, String imageURL, double price,
                   int stock, int quantity, Category category) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.stock = stock;
        this.quantity = quantity;
        this.category = category;
    }
}
