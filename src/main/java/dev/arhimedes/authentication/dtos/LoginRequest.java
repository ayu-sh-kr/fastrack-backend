package dev.arhimedes.authentication.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
