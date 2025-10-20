package com.example.ecommerce.repo;

import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Search by category
    List<Product> findByCategory(String category);

    // Search by name or description or category
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Product> searchProducts(@Param("searchTerm") String searchTerm);

    // Search with price range
    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> searchProductsWithPriceRange(
            @Param("searchTerm") String searchTerm,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice
    );

    // Find products by price range
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}