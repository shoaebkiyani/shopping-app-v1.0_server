package com.backend.product;

import com.backend.category.Category;
import com.backend.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:5173/")
@CrossOrigin(origins = {"https://techzoneapi.netlify.app/", "http://localhost:5173/"})
@RequestMapping("api/v1")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<List<Product>>(productService.allProducts(), HttpStatus.OK);
    }

    @GetMapping("/single-product/{id}")
    public ResponseEntity<Optional<Product>> getSingleProduct(@PathVariable UUID id) {
        return new ResponseEntity<Optional<Product>>(productService.singleProduct(id), HttpStatus.OK);
    }

    @PostMapping("/add-product")
    public Product createProduct(@RequestBody ProductDTO productDTO) {
        UUID categoryId = productDTO.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElse(null);
        Product product = productMapper.toProduct(productDTO, category);
        product.setInStock(product.getQuantity() >= 1);
        return productService.createProduct(product);
    }

    @PutMapping("/edit-product/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
