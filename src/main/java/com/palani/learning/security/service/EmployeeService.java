package com.palani.learning.security.service;

import com.palani.learning.security.entity.Employee;
import com.palani.learning.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }
}
