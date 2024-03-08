package dev.arhimedes.admin.service;

import dev.arhimedes.global.entities.Employee;
import dev.arhimedes.global.repositories.EmployeeRepository;
import dev.arhimedes.global.service.contract.EncryptionService;
import dev.arhimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmployeeRepository employeeRepository;

    private final EncryptionService encryptionService;

    public ResponseEntity<ApiResponse<String>> createEmployee(Employee employee){
        employee = employeeRepository.save(employee);
        return new ResponseEntity<>(ApiResponse
                .<String>builder()
                .message("Employee created with id: " +
                        encryptionService.encrypt(String.valueOf(employee.getEmployeeId()))
                )
                .body("")
                .urlPath("/api/admin/create-employee")
                .date(LocalDateTime.now())
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse<?>> updateStatus(int id, boolean status){

        if(employeeRepository.existsById(id)){
            int res = employeeRepository.updateActiveByEmployeeId(status, id);
            if(res > 0){
                return new ResponseEntity<>(
                        ApiResponse.builder()
                                .message("Status updated successfully")
                                .date(LocalDateTime.now())
                                .urlPath("/api/admin/activate")
                                .build(), HttpStatus.ACCEPTED
                );
            }

            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Failed to update status")
                            .date(LocalDateTime.now())
                            .urlPath("/api/admin/activate")
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Invalid employee id")
                        .date(LocalDateTime.now())
                        .urlPath("/api/admin/activate")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
