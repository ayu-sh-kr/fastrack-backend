package dev.arhimedes.global.dtos;

import dev.arhimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.arhimedes.global.entities.Employee}
 */
@Getter
@Setter
public class EmployeeDTO implements Serializable {
    private String employeeId;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String dob;

    @NotBlank
    @ValidRole
    private String role;
    private boolean active;
}