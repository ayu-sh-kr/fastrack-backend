package dev.arhimedes;

import dev.arhimedes.global.entities.Employee;
import dev.arhimedes.global.enums.Role;
import dev.arhimedes.global.repositories.EmployeeRepository;
import dev.arhimedes.global.service.contract.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartupRunnerElement implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final EncryptionService encryptionService;

    @Value("${application.admin.username}")
    private String adminName;

    @Value("${application.admin.email}")
    private String adminEmail;

    @Value("${application.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        Employee employee = Employee.builder()
                .name(adminName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .active(true)
                .role(Role.ADMIN)
                .build();

        employee = employeeRepository.save(employee);
        log.info("Employee created with id: " + employee.getEmployeeId());
        log.info("Encrypted employee id: " + encryptionService.encrypt(String.valueOf(employee.getEmployeeId())));
    }
}
