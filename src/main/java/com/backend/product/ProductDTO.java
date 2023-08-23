package com.backend.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class ProductDTO {
    private UUID categoryId;
    private String title;
    private String description;
    private String imageURL;
    private double price;
    private int stock;
    private int quantity;
}
