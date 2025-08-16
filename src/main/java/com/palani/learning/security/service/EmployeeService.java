package com.palani.learning.security.service;

import com.palani.learning.security.entity.Employee;
import com.palani.learning.security.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployeeList() {
        log.debug("Retrieving employee list from repository");
        return employeeRepository.findAll();
    }
}
