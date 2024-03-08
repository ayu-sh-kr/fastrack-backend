package dev.arhimedes.global.entities;

import dev.arhimedes.global.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String name;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

}
