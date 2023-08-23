package com.backend.product;

import com.backend.category.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductDTO product, Category category) {
        return new Product(product.getTitle(), product.getDescription(), product.getImageURL(), product.getPrice(),
                product.getStock(), product.getQuantity(), category);
    }
}
