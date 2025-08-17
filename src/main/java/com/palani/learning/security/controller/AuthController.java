package com.palani.learning.security.controller;

import com.palani.learning.security.dto.AuthRequest;
import com.palani.learning.security.dto.AuthResponse;
import com.palani.learning.security.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication endpoints.
 * <p>
 * Handles user login and JWT token generation.
 * </p>
 */
@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authenticates a user and returns a JWT token if successful.
     *
     * @param authRequest the authentication request containing username and password
     * @return a ResponseEntity containing the JWT token or an error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Attempting to authenticate user: {}", authRequest.getUsername());
            log.info("Password length: {}", authRequest.getPassword() != null ? authRequest.getPassword().length() : "null");

            // Load user details first to verify they exist
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            log.info("User details loaded successfully for: {}", authRequest.getUsername());

            // Attempt authentication
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            log.info("Authentication successful for user: {}", authRequest.getUsername());

            final String token = jwtUtil.generateToken(userDetails.getUsername());
            log.info("JWT token generated successfully");

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage(), e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
