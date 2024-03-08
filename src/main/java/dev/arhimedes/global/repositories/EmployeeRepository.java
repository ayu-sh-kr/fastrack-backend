package dev.arhimedes.global.repositories;

import dev.arhimedes.global.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Transactional
    @Modifying
    @Query("update Employee e set e.active = ?1 where e.employeeId = ?2")
    int updateActiveByEmployeeId(boolean active, int employeeId);

    boolean existsByEmail(String username);

    Employee findByEmail(String username);

    @Query("select e.employeeId from Employee e where e.email = ?1")
    String findIdByEmail(String email);

}
