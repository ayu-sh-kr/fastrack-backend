package dev.arhimedes.shipment.dtos;

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
public class ShipmentDTO implements Serializable {

    private String shipmentId;

    @NotBlank
    private String shipmentStatus;

    private String originAddressId;

    private String originCity;

    private String destinationAddressId;

    private String destinationCity;

    @NotBlank
    private String productId;

    private LocalDate dispatchDate;

    private LocalDateTime deliveredTime;

}