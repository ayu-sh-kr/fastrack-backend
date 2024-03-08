package dev.arhimedes.product.controller;

import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.product.converter.ProductConverter;
import dev.arhimedes.product.dtos.ProductDTO;
import dev.arhimedes.product.enums.Category;
import dev.arhimedes.product.service.ProductService;
import dev.arhimedes.utils.validators.annotations.ValidCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final EncryptionService encryptionService;

    private final ProductConverter productConverter;

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO productDTO){
        return productService.create(
                productConverter.reverseConvert(productDTO, null)
        );
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getProductById(@RequestParam("productId") String id){
        return productService.getById(Integer.parseInt(encryptionService.decrypt(id)));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProduct(){
        return productService.getAll();
    }

    @GetMapping("/get-by-category")
    public ResponseEntity<?> getProductByCategory(@RequestParam("category") @ValidCategory String category){
        return productService.getProductsByCategory(Category.valueOf(category));
    }

}
