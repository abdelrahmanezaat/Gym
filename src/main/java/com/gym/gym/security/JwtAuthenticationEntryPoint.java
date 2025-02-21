package com.gym.gym.security;

import org.springframework.security.core.AuthenticationException;  // Exception thrown when authentication fails.
import org.springframework.security.web.AuthenticationEntryPoint;  // Interface to handle authentication errors.
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component  // Marks this class as a Spring component to be injected where needed.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method is called when a request fails authentication (e.g., invalid JWT).
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");  // Sends a 401 Unauthorized response.
    }
}
