package dev.arhimedes.global.converter;

import dev.arhimedes.global.dtos.EmployeeDTO;
import dev.arhimedes.global.entities.Employee;
import dev.arhimedes.global.enums.Role;
import dev.arhimedes.global.service.contract.Converter;
import dev.arhimedes.global.service.contract.EncryptionService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("EmployeeConverter")
@RequiredArgsConstructor
public class EmployeeConverter implements Converter<Employee, EmployeeDTO> {

    private final EncryptionService encryptionService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDTO convert(Employee employee, EmployeeDTO employeeDTO) {

        if(null == employeeDTO){
            employeeDTO = new EmployeeDTO();
        }

        if(employee.getEmployeeId() != 0){
            employeeDTO.setEmployeeId(
                    encryptionService.encrypt(String.valueOf(employee.getEmployeeId()))
            );
        }

        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());

        employeeDTO.setDob(employee.getDob().toString());
        employeeDTO.setActive(employee.isActive());
        employeeDTO.setRole(employee.getRole().name());

        return employeeDTO;
    }

    @Override
    public Employee reverseConvert(EmployeeDTO employeeDTO, Employee employee) {

        if(null == employee){
            employee = new Employee();
        }

        if(StringUtils.isNotBlank(employeeDTO.getEmployeeId())){
            employee.setEmployeeId(
                    Integer.parseInt(encryptionService.decrypt(employeeDTO.getEmployeeId()))
            );
        }

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        employee.setDob(LocalDate.parse(employeeDTO.getDob(), dateFormat));

        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setRole(Role.valueOf(employeeDTO.getRole().toUpperCase()));
        employee.setActive(employeeDTO.isActive());

        return employee;
    }
}
