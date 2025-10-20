package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Search functionality
    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return productRepository.findAll();
        }
        System.out.println("Searching for: " + searchTerm);
        List<Product> results = productRepository.searchProducts(searchTerm.trim());
        System.out.println("Found " + results.size() + " products");
        return results;
    }

    // Search by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // Search with price filter
    public List<Product> searchProductsWithPrice(String searchTerm, double minPrice, double maxPrice) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        }
        return productRepository.searchProductsWithPriceRange(searchTerm.trim(), minPrice, maxPrice);
    }
}