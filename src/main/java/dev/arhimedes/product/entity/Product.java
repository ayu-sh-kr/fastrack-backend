package dev.arhimedes.product.entity;

import dev.arhimedes.product.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    private String description;


    private double weight;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String producerName;

    private String producerEmail;

    private Long producerNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

}
