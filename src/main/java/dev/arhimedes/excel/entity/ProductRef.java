package dev.arhimedes.excel.entity;

import dev.arhimedes.excel.utils.annotation.ExcelColumn;
import dev.arhimedes.excel.utils.annotation.ExcelSheet;
import dev.arhimedes.product.enums.Category;
import dev.arhimedes.utils.validators.annotations.ValidCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.time.LocalDate;

@ExcelSheet(name = "Product")
@JsonComponent
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRef {

    @ExcelColumn(name = "Product Name", cellNumber = 0)
    private String name;

    @ExcelColumn(name = "Description", cellNumber = 1)
    private String description;

    @ExcelColumn(name = "Producer Name", cellNumber = 2)
    private String producerName;

    @ExcelColumn(name = "Producer Email", cellNumber = 3)
    private String producerEmail;

    @ExcelColumn(name = "Producer Phone", cellNumber = 4)
    private Long producerPhone;

    @ExcelColumn(name = "Weight", cellNumber = 5)
    private Double weight;

    @ExcelColumn(name = "Category", cellNumber = 6)
    @ValidCategory
    private String category;

    @ExcelColumn(name = "Receiving Date", cellNumber = 7)
    private String date;

    public ProductRef(String productName, String description, String producerName, String producerEmail, long producerPhone,  Double weight, Category category, LocalDate receivingDate) {
        this.name = productName;
        this.description = description;
        this.producerName = producerName;
        this.producerEmail = producerEmail;
        this.producerPhone = producerPhone;
        this.weight = weight;
        this.category = category.name();
        this.date = receivingDate.toString();
    }

    @Override
    public String toString() {
        return "ProductRef{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", producerName='" + producerName + '\'' +
               ", producerEmail='" + producerEmail + '\'' +
               ", producerPhone='" + producerPhone + '\'' +
               ", weight='" + weight + '\'' +
               ", category='" + category + '\'' +
               ", date='" + date + '\'' +
               '}';
    }
}
