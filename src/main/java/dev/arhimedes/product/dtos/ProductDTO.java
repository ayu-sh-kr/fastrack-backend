package dev.arhimedes.product.dtos;

import dev.arhimedes.utils.validators.annotations.ValidCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.arhimedes.product.entity.Product}
 */
@Getter
@Setter
public class ProductDTO implements Serializable {

    private String productId;

    @NotBlank
    private String productName;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    private double weight;

    @NotBlank
    @ValidCategory
    private String category;

    @NotBlank
    private String producerName;

}