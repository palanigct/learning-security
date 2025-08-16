package com.palani.learning.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user details for username: {}", username);
        
        // Replace this with your own logic to fetch from DB
        if ("admin".equals(username)) {
            // Use a known working BCrypt hash for "password"
            String encodedPassword = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";
            logger.info("Found admin user with encoded password: {}", encodedPassword);
            
            // Create user with proper authorities
            User user = new User("admin", encodedPassword, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            logger.info("Created user: {}, enabled: {}, accountNonExpired: {}, credentialsNonExpired: {}, accountNonLocked: {}", 
                user.getUsername(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked());
            
            return user;
        } else {
            logger.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
    }
}
