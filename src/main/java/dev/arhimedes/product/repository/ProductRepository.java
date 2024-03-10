package dev.arhimedes.product.repository;

import dev.arhimedes.excel.entity.ProductRef;
import dev.arhimedes.product.entity.Product;
import dev.arhimedes.product.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.category = ?1")
    List<Product> getProductsByCategory(Category category);


    @Query("""
    select new dev.arhimedes.excel.entity.ProductRef(p.productName, p.description,
     p.producerName, p.producerEmail, p.producerNumber, p.weight, p.category, p.receivingDate) from Product p
""")
    List<ProductRef> findAllProductInProductRef();

    @Query("""
    select new dev.arhimedes.excel.entity.ProductRef(p.productName, p.description,
     p.producerName, p.producerEmail, p.producerNumber, p.weight, p.category, p.receivingDate) 
     from Product p
     where p.receivingDate = ?1
""")
    List<ProductRef> findAllProductRefByDate(LocalDate date);



    @Query("select p from Product p where p.receivingDate = ?1")
    List<Product> findByDate(LocalDate date);
}
