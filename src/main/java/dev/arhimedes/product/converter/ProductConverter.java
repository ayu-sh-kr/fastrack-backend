package dev.arhimedes.product.converter;

import dev.arhimedes.global.service.contract.Converter;
import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.product.dtos.ProductDTO;
import dev.arhimedes.product.entity.Product;
import dev.arhimedes.product.enums.Category;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ProductConverter")
@RequiredArgsConstructor
public class ProductConverter implements Converter<Product, ProductDTO> {

    private final EncryptionService encryptionService;

    @Override
    public ProductDTO convert(Product product, ProductDTO productDTO) {

        if(null == productDTO){
            productDTO = new ProductDTO();
        }

        if(0 != product.getProductId()){
            productDTO.setProductId(
                    encryptionService.encrypt(String.valueOf(product.getProductId()))
            );
        }

        productDTO.setProductName(product.getProductName());
        productDTO.setDescription(product.getDescription());
        productDTO.setWeight(product.getWeight());
        productDTO.setProducerName(product.getProducerName());
        productDTO.setCategory(product.getCategory().name());
        productDTO.setReceivingDate(product.getReceivingDate());

        return productDTO;
    }

    @Override
    public Product reverseConvert(ProductDTO productDTO, Product product) {

        if(null == product){
            product = new Product();
        }

        if(StringUtils.isNotBlank(productDTO.getProductId())){
            product.setProductId(
                    Integer.parseInt(encryptionService.decrypt(productDTO.getProductId()))
            );
        }

        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setWeight(productDTO.getWeight());
        product.setProducerName(productDTO.getProducerName());
        product.setCategory(Category.valueOf(productDTO.getCategory().toUpperCase()));
        product.setReceivingDate(productDTO.getReceivingDate());

        return product;
    }
}
