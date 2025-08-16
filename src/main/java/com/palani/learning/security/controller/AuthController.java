package com.palani.learning.security.controller;

import com.palani.learning.security.dto.AuthRequest;
import com.palani.learning.security.dto.AuthResponse;
import com.palani.learning.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        try {
            logger.info("Attempting to authenticate user: {}", authRequest.getUsername());
            logger.info("Password length: {}", authRequest.getPassword() != null ? authRequest.getPassword().length() : "null");
            
            // Load user details first to verify they exist
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            logger.info("User details loaded successfully for: {}", authRequest.getUsername());
            
            // Attempt authentication
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            logger.info("Authentication successful for user: {}", authRequest.getUsername());
            
            final String token = jwtUtil.generateToken(userDetails.getUsername());
            logger.info("JWT token generated successfully");
            
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage(), e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }
}
