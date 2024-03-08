package dev.arhimedes.global.service.security;

import dev.arhimedes.global.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(employeeRepository.existsByEmail(username)){
            return new CustomUserDetails(employeeRepository.findByEmail(username));
        }
        throw new RuntimeException("Employee not exist");
    }
}
