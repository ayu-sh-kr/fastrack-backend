package dev.arhimedes.global.controller;

import dev.arhimedes.global.service.contract.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EncryptionService encryptionService;
}
