package com.palani.learning.security.controller;

import com.palani.learning.security.entity.Employee;
import com.palani.learning.security.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/get/all")
    public List<Employee> getEmployeeDetails() {
        return employeeService.getEmployeeList();
    }
}
