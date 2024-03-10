package dev.arhimedes.shipment.dtos;

import dev.arhimedes.excel.utils.annotation.ExcelColumn;
import dev.arhimedes.excel.utils.annotation.ExcelSheet;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link dev.arhimedes.shipment.entity.Shipment}
 */
@Getter
@Setter
@ExcelSheet(name = "Shipment Details")
public class ShipmentDTO implements Serializable {

    private String shipmentId;

    @NotBlank
    @ExcelColumn(name = "Shipment Status", cellNumber = 1)
    private String shipmentStatus;


    private String originAddressId;

    @ExcelColumn(name = "Origin City", cellNumber = 2)
    private String originCity;

    private String destinationAddressId;

    @ExcelColumn(name = "Destination City", cellNumber = 3)
    private String destinationCity;

    @NotBlank
    @ExcelColumn(name = "Product Id", cellNumber = 4)
    private String productId;

    @ExcelColumn(name = "Delivery Cost", cellNumber = 7)
    private double cost;

    @ExcelColumn(name = "Dispatch Date", cellNumber = 5)
    private LocalDate dispatchDate;

    @ExcelColumn(name = "Delivered Date", cellNumber = 6)
    private LocalDateTime deliveredTime;

}