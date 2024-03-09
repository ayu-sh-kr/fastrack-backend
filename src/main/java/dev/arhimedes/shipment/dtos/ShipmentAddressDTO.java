package dev.arhimedes.shipment.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.arhimedes.shipment.entity.ShipmentAddress}
 */
@Getter
@Setter
public class ShipmentAddressDTO implements Serializable {
    private String id;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String state;
}