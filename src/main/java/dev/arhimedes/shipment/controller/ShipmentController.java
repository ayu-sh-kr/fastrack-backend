package dev.arhimedes.shipment.controller;

import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.shipment.converters.ShipmentAddressConverter;
import dev.arhimedes.shipment.converters.ShipmentConverter;
import dev.arhimedes.shipment.dtos.ShipmentAddressDTO;
import dev.arhimedes.shipment.enums.ShipmentStatus;
import dev.arhimedes.shipment.service.ShipmentService;
import dev.arhimedes.utils.validators.annotations.ValidShipmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipment")
@RequiredArgsConstructor
public class ShipmentController{

    private final EncryptionService encryptionService;

    private final ShipmentService shipmentService;

    private final ShipmentConverter shipmentConverter;

    private final ShipmentAddressConverter shipmentAddressConverter;


    @GetMapping("/create")
    public ResponseEntity<?> create(@RequestParam("/create") String productId){
        return shipmentService.createShipment(
                Integer.parseInt(encryptionService.decrypt(productId))
        );
    }

    @PatchMapping("/add-origin")
    public ResponseEntity<?> addOrigin(@RequestParam("shipmentId") String shipmentId, @RequestBody ShipmentAddressDTO origin){
        return shipmentService.addOrigin(
                shipmentAddressConverter.reverseConvert(origin, null), Integer.parseInt(encryptionService.decrypt(shipmentId))
        );
    }


    @PatchMapping("/add-destination")
    public ResponseEntity<?> addDestination(@RequestParam("shipmentId") String shipmentId, @RequestBody ShipmentAddressDTO destination){
        return shipmentService.addDestination(
                shipmentAddressConverter.reverseConvert(destination, null), Integer.parseInt(encryptionService.decrypt(shipmentId))
        );
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam("status") @ValidShipmentStatus String status, @RequestParam("shipmentId") String shipmentId){
        return shipmentService.updateStatus(
                ShipmentStatus.valueOf(status), Integer.parseInt(encryptionService.decrypt(shipmentId))
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteShipment(@RequestParam("shipmentId") String shipmentId){
        return shipmentService.deleteShipment(Integer.parseInt(encryptionService.decrypt(shipmentId)));
    }

}
