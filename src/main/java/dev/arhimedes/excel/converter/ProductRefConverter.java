package dev.arhimedes.excel.converter;

import dev.arhimedes.excel.entity.ProductRef;
import dev.arhimedes.global.service.contract.Converter;
import dev.arhimedes.product.entity.Product;
import dev.arhimedes.product.enums.Category;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component("ProductRefConverter")
public class ProductRefConverter implements Converter<Product, ProductRef> {



    @Override
    public ProductRef convert(Product product, ProductRef productRef) {

        if(null == productRef){
            productRef = new ProductRef();
        }

        productRef.setName(product.getProductName());
        productRef.setDescription(product.getDescription());
        productRef.setProducerName(product.getProducerName());

        productRef.setWeight(product.getWeight());
        productRef.setCategory(product.getCategory().name());

        productRef.setProducerEmail(product.getProducerEmail());
        productRef.setProducerPhone(product.getProducerNumber());

        productRef.setDate(String.valueOf(product.getReceivingDate()));

        return productRef;
    }

    @Override
    public Product reverseConvert(ProductRef productRef, Product product) {

        if(null == product){
            product = new Product();
        }
        product.setProductName(productRef.getName());
        product.setDescription(productRef.getDescription());
        product.setProducerName(productRef.getProducerName());

        product.setWeight(productRef.getWeight());
        product.setCategory(Category.valueOf(productRef.getCategory()));

        product.setProducerEmail(productRef.getProducerEmail());
        product.setProducerNumber(productRef.getProducerPhone());


        if(StringUtils.isNotBlank(productRef.getDate())){
            System.out.println(productRef.getDate());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(productRef.getDate(), formatter);
            product.setReceivingDate(date);
        }

        return product;
    }
}
