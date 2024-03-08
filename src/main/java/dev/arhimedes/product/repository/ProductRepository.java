package dev.arhimedes.product.repository;

import dev.arhimedes.product.entity.Product;
import dev.arhimedes.product.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.category = ?1")
    List<Product> getProductsByCategory(Category category);

}
