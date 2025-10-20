package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // Search endpoint
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String q) {
        System.out.println("Search API called with query: " + q);
        if (q == null || q.trim().isEmpty()) {
            return productService.getAllProducts();
        }
        return productService.searchProducts(q);
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // Advanced search with price range
    @GetMapping("/search/advanced")
    public List<Product> advancedSearch(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "999999") double maxPrice) {

        System.out.println("Advanced search: " + q + ", Price: " + minPrice + "-" + maxPrice);
        return productService.searchProductsWithPrice(q, minPrice, maxPrice);
    }
}