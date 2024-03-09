package dev.arhimedes.shipment.entity;

import dev.arhimedes.product.entity.Product;
import dev.arhimedes.shipment.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipmentId;

    @OneToOne
    @JoinColumn(name = "destination_address")
    private ShipmentAddress destination;

    @OneToOne
    @JoinColumn(name = "origin_address")
    private ShipmentAddress origin;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;

    @Temporal(TemporalType.DATE)
    private LocalDate dispatchDate;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deliveredTime;

}
