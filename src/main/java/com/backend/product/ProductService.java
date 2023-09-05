package com.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> singleProduct(UUID id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product product) {
        Product updateProduct = productRepository.findById(id).orElse(null);
        if (updateProduct == null) {
            return null;
        }
        updateProduct.setTitle(product.getTitle());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setImageURL(product.getImageURL());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        return productRepository.save(updateProduct);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

}
