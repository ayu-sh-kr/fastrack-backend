package dev.arhimedes.product.service;

import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.product.converter.ProductConverter;
import dev.arhimedes.product.entity.Product;
import dev.arhimedes.product.enums.Category;
import dev.arhimedes.product.repository.ProductRepository;
import dev.arhimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final EncryptionService encryptionService;

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    public ResponseEntity<ApiResponse<?>> create(Product product){
        try {
            product = productRepository.save(product);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Product added successfully")
                            .urlPath("/api/product/create")
                            .date(LocalDateTime.now())
                            .body(Map.of(
                                    "productId", encryptionService.encrypt(
                                            String.valueOf(product.getProductId()))
                                    )
                            )
                            .build(),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<ApiResponse<?>> getById(int id){
        if(productRepository.existsById(id)){
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Product fetched successfully")
                            .date(LocalDateTime.now())
                            .urlPath("/api/product/get-by-id")
                            .body(
                                    productConverter.convert(productRepository.getReferenceById(id), null)
                            )
                            .build(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Product do not exists")
                        .date(LocalDateTime.now())
                        .urlPath("/api/product/get-by-id")
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ApiResponse<?>> getAll(){
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Product fetched successfully")
                        .urlPath("/api/product/get-all")
                        .date(LocalDateTime.now())
                        .body(
                                productRepository.findAll().stream()
                                        .map(product -> productConverter.convert(product, null))
                                        .toList()
                        )
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ApiResponse<?>> getProductsByCategory(Category category){
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Product fetched by category")
                        .date(LocalDateTime.now())
                        .urlPath("/api/product/get-by-category")
                        .body(
                                productRepository.getProductsByCategory(category)
                                        .stream()
                                        .map(product -> productConverter.convert(product, null))
                                        .toList()
                        )
                        .build(),
                HttpStatus.OK
        );
    }


}
