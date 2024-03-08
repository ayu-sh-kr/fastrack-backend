package dev.arhimedes.authentication.controller;

import dev.arhimedes.authentication.dtos.LoginRequest;
import dev.arhimedes.global.service.contract.TokenService;
import dev.arhimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            );

            Authentication response = authenticationManager.authenticate(request);

            if(response.isAuthenticated()){
                String token = tokenService.generateToken(response);
                return new ResponseEntity<>(
                        ApiResponse.<String>builder()
                                .message("Login Successful")
                                .urlPath("/api/auth/login")
                                .date(LocalDateTime.now())
                                .body(token)
                                .build(), HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                    ApiResponse.<String>builder()
                            .message("Invalid credentials")
                            .date(LocalDateTime.now())
                            .urlPath("/api/auth/login")
                            .body("")
                            .build(), HttpStatus.BAD_REQUEST
            );

        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
