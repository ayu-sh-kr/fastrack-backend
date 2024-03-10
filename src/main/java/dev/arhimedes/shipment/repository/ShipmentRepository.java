package dev.arhimedes.shipment.repository;

import dev.arhimedes.shipment.entity.Shipment;
import dev.arhimedes.shipment.entity.ShipmentAddress;
import dev.arhimedes.shipment.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
    @Transactional
    @Modifying
    @Query("update Shipment s set s.origin = ?1 where s.shipmentId = ?2")
    void updateOriginByShipmentId(ShipmentAddress origin, int shipmentId);

    @Transactional
    @Modifying
    @Query("update Shipment s set s.destination = ?1 where s.shipmentId = ?2")
    void updateDestinationByShipmentId(ShipmentAddress destination, int shipmentId);

    @Transactional
    @Modifying
    @Query("update Shipment s set s.shipmentStatus = ?1 where s.shipmentId = ?2")
    void updateShipmentStatusByShipmentId(ShipmentStatus shipmentStatus, int shipmentId);

    @Transactional
    @Modifying
    @Query("update Shipment s set s.shipmentStatus = ?1, s.dispatchDate = ?2 where s.shipmentId = ?3")
    void updateShipmentStatusAndDispatchDateByShipmentId(ShipmentStatus shipmentStatus, LocalDate dispatchDate, int shipmentId);

    @Transactional
    @Modifying
    @Query("update Shipment s set s.shipmentStatus = ?1, s.deliveredTime = ?2 where s.shipmentId = ?3")
    void updateShipmentStatusAndDeliveredTimeByShipmentId(ShipmentStatus shipmentStatus, LocalDateTime deliveredTime, int shipmentId);


    @Query("select s from Shipment s where s.dispatchDate = ?1")
    List<Shipment> findByDispatchDate(LocalDate date);

}
