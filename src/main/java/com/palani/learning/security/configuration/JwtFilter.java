package com.palani.learning.security.configuration;

import com.palani.learning.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter for processing and validating JWT tokens in requests.
 * <p>
 * Skips public endpoints and sets authentication in the security context if the token is valid.
 * </p>
 */
@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Processes incoming requests, validates JWT tokens, and sets authentication if valid.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        final String authHeader = request.getHeader("Authorization");
        
        log.debug("Processing request: {} {}", request.getMethod(), requestURI);
        log.debug("Authorization header: {}", authHeader);

        // Skip JWT processing for login and public endpoints
        if (requestURI.startsWith("/auth/") || requestURI.startsWith("/public/") || 
            requestURI.startsWith("/swagger-ui/") || requestURI.startsWith("/v3/api-docs/") ||
            requestURI.startsWith("/swagger-resources/") || requestURI.startsWith("/webjars/") ||
            requestURI.equals("/swagger-ui.html") || requestURI.startsWith("/h2-console/")) {
            log.debug("Skipping JWT processing for public endpoint: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            log.debug("Extracted username: {}", username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authentication set for user: {}", username);
                } else {
                    log.debug("JWT validation failed for user: {}", username);
                }
            } catch (Exception e) {
                log.error("Error processing JWT for user: {}", username, e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
