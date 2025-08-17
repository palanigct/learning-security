package com.palani.learning.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot Security application.
 * <p>
 * This application demonstrates modern Spring Boot security practices, JWT authentication,
 * H2 database integration, and robust logging using Log4j2 and Lombok.
 * </p>
 */
@SpringBootApplication
public class SecurityApplication {

	/**
	 * Starts the Spring Boot application.
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

}
