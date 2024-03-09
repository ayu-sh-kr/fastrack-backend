package dev.arhimedes.shipment.service;

import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.product.repository.ProductRepository;
import dev.arhimedes.shipment.entity.Shipment;
import dev.arhimedes.shipment.entity.ShipmentAddress;
import dev.arhimedes.shipment.enums.ShipmentStatus;
import dev.arhimedes.shipment.repository.ShipmentRepository;
import dev.arhimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final EncryptionService encryptionService;

    private final ProductRepository productRepository;

    private final String BASEURL = "/api/shipment";

    public ResponseEntity<?> createShipment(int productId){
        if(productRepository.existsById(productId)){
            Shipment shipment = new Shipment();
            shipment.setProduct(productRepository.getReferenceById(productId));
            shipment.setShipmentStatus(ShipmentStatus.PROCESSING);
            shipment = shipmentRepository.save(shipment);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Shipment entity created")
                            .urlPath(BASEURL + "/create")
                            .date(LocalDateTime.now())
                            .body(Map.of("shipmentId", encryptionService.encrypt(String.valueOf(shipment.getShipmentId()))))
                            .build(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Invalid product id")
                        .urlPath(BASEURL + "/create")
                        .date(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> addOrigin(ShipmentAddress origin, int shipmentId){
        if(shipmentRepository.existsById(shipmentId)){
            shipmentRepository.updateOriginByShipmentId(origin, shipmentId);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Shipment origin added")
                            .urlPath(BASEURL + "/add-origin")
                            .date(LocalDateTime.now())
                            .build(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Shipment does not exists")
                        .urlPath(BASEURL + "/add-origin")
                        .date(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    public ResponseEntity<?> addDestination(ShipmentAddress destination, int shipmentId){
        if(shipmentRepository.existsById(shipmentId)){
            shipmentRepository.updateDestinationByShipmentId(destination, shipmentId);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Shipment destination added")
                            .urlPath(BASEURL + "/add-destination")
                            .date(LocalDateTime.now())
                            .build(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Shipment does not exists")
                        .urlPath(BASEURL + "/add-destination")
                        .date(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> updateStatus(ShipmentStatus shipmentStatus, int shipmentId){
        if(shipmentRepository.existsById(shipmentId)){
            if(ShipmentStatus.DISPATCHED.equals(shipmentStatus)){
                shipmentRepository.updateShipmentStatusAndDispatchDateByShipmentId(shipmentStatus, LocalDate.now(), shipmentId);
            }

            else if(ShipmentStatus.DELIVERED.equals(shipmentStatus)){
                shipmentRepository.updateShipmentStatusAndDeliveredTimeByShipmentId(shipmentStatus, LocalDateTime.now(), shipmentId);
            }

            else shipmentRepository.updateShipmentStatusByShipmentId(shipmentStatus, shipmentId);

            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Status changed successfully")
                            .urlPath(BASEURL + "/change-status")
                            .date(LocalDateTime.now())
                            .build(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Shipment does not exists")
                        .urlPath(BASEURL + "/change-status")
                        .date(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> deleteShipment(int shipmentId){
        if(shipmentRepository.existsById(shipmentId)){
            shipmentRepository.deleteById(shipmentId);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Shipment deleted successfully")
                            .urlPath(BASEURL + "/delete")
                            .date(LocalDateTime.now())
                            .build(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Shipment does not exists")
                        .urlPath(BASEURL + "/delete")
                        .date(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
