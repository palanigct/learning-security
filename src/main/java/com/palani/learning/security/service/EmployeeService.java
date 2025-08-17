package com.palani.learning.security.service;

import com.palani.learning.security.entity.Employee;
import com.palani.learning.security.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for employee-related business logic.
 * <p>
 * Handles operations related to Employee entities.
 * </p>
 */
@Log4j2
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Retrieves all employees from the repository.
     *
     * @return a list of Employee entities
     */
    public List<Employee> getEmployeeList() {
        log.debug("Retrieving employee list from repository");
        return employeeRepository.findAll();
    }
}
