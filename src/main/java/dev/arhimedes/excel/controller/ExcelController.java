package dev.arhimedes.excel.controller;

import dev.arhimedes.excel.converter.ProductRefConverter;
import dev.arhimedes.excel.entity.ProductRef;
import dev.arhimedes.excel.utils.ExcelUtility;
import dev.arhimedes.product.repository.ProductRepository;
import dev.arhimedes.shipment.converters.ShipmentConverter;
import dev.arhimedes.shipment.dtos.ShipmentDTO;
import dev.arhimedes.shipment.repository.ShipmentRepository;
import dev.arhimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ProductRepository productRepository;

    private final ProductRefConverter productRefConverter;

    private final ShipmentRepository shipmentRepository;

    private final ShipmentConverter shipmentConverter;

    @PostMapping("/product/save")
    public ResponseEntity<?> save(@RequestParam("sheet") MultipartFile file) {
        ExcelUtility<ProductRef> excelUtility = new ExcelUtility<>();
        List<ProductRef> productRefs = excelUtility.read(file, ProductRef.class);

        try {
            productRepository.saveAll(
                    productRefs.stream().map(productRef -> productRefConverter.reverseConvert(productRef, null)).toList()
            );
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Product saved successfully")
                            .urlPath("/api/excel/save")
                            .date(LocalDateTime.now())
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    @GetMapping("/product/download")
    public ResponseEntity<?> downloadExcel(@RequestParam("date") String date) {
        ExcelUtility<ProductRef> excelUtility = new ExcelUtility<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<ProductRef> productRefs = productRepository.findAllProductRefByDate(LocalDate.parse(date, formatter));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelUtility.write(productRefs, ProductRef.class, outputStream, "Products");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=products" + LocalDate.now() + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));
    }



    @GetMapping("/shipment/download")
    public ResponseEntity<?> downloadShipment(@RequestParam("date") String date){

        ExcelUtility<ShipmentDTO> excelUtility = new ExcelUtility<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<ShipmentDTO> shipmentDTOS = shipmentRepository
                .findByDispatchDate(LocalDate.parse(date, formatter))
                .stream()
                .map(shipment -> shipmentConverter.convert(shipment, null))
                .toList();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        excelUtility.write(shipmentDTOS, ShipmentDTO.class ,outputStream, "Shipments");

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=shipments" + LocalDate.now() + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(inputStream));

    }


}
