package com.palani.learning.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication responses.
 * <p>
 * Contains the JWT token returned after successful authentication.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
}