package dev.arhimedes.admin.controller;

import dev.arhimedes.admin.service.AdminService;
import dev.arhimedes.global.converter.EmployeeConverter;
import dev.arhimedes.global.dtos.EmployeeDTO;
import dev.arhimedes.global.service.contract.EncryptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final EmployeeConverter employeeConverter;

    private final EncryptionService encryptionService;

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO){
        return adminService.createEmployee(
                employeeConverter.reverseConvert(employeeDTO, null)
        );
    }

    @PatchMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestParam("employeeId") String id){
        return adminService.updateStatus(
                Integer.parseInt(encryptionService.decrypt(id)), true
        );
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<?> deactivateAccount(@RequestParam("employeeId") String id){
        return adminService.updateStatus(
                Integer.parseInt(encryptionService.decrypt(id)), false
        );
    }

}
