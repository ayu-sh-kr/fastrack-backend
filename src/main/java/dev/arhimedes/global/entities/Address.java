package dev.arhimedes.global.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String houseNo;
    private String street;
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String district;
    private String state;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

}
