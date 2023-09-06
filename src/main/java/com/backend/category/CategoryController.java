package com.backend.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "http://localhost:5173/")
@CrossOrigin(origins = "https://techzoneapi.netlify.app/")
@RequestMapping("api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<List<Category>>(categoryService.allCategories(), HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public Category createOne(@RequestBody Category category) {
        return categoryService.createOne(category);
    }

    @PutMapping("/edit-category/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/delete-category/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
