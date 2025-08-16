package com.palani.learning.security.configuration;

import com.palani.learning.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String requestURI = request.getRequestURI();
        final String authHeader = request.getHeader("Authorization");
        
        logger.debug("Processing request: {} {}", request.getMethod(), requestURI);
        logger.debug("Authorization header: {}", authHeader);

        // Skip JWT processing for login and public endpoints
        if (requestURI.startsWith("/auth/") || requestURI.startsWith("/public/") || 
            requestURI.startsWith("/swagger-ui/") || requestURI.startsWith("/v3/api-docs/") ||
            requestURI.startsWith("/swagger-resources/") || requestURI.startsWith("/webjars/") ||
            requestURI.equals("/swagger-ui.html") || requestURI.startsWith("/h2-console/")) {
            logger.debug("Skipping JWT processing for public endpoint: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            logger.debug("Extracted username: {}", username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Authentication set for user: {}", username);
                } else {
                    logger.debug("JWT validation failed for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Error processing JWT for user: {}", username, e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
