package com.palani.learning.security.dto;

import lombok.Data;

/**
 * Data Transfer Object for employee information.
 * <p>
 * Used to transfer employee data between layers.
 * </p>
 */
@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private String address;
}
