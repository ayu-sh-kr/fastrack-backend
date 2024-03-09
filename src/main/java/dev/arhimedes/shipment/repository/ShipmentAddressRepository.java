package dev.arhimedes.shipment.repository;

import dev.arhimedes.shipment.entity.ShipmentAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentAddressRepository extends JpaRepository<ShipmentAddress, Integer> {
}
