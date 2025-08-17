package com.palani.learning.security.repository;

import com.palani.learning.security.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Employee entities.
 * <p>
 * Provides CRUD operations and query methods for Employee.
 * </p>
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
