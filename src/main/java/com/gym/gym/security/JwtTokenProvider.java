package com.gym.gym.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // Generates JWT token with roles, ensuring each role has the ROLE_ prefix
    public String generateToken(String email, long id,List<String> roles) {
        List<String> prefixedRoles = roles.stream()
                .map(role -> "ROLE_" + role.toUpperCase())  // Add 'ROLE_' prefix
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(email)
                .claim("id",id)  
                .claim("roles", prefixedRoles)  // Add roles to the token
                .setIssuedAt(new Date())  // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Set the expiration time
                .signWith(SignatureAlgorithm.HS512, secretKey)  // Sign the token with the secret key
                .compact();  // Create the token
    }

    // Resolves and returns the JWT token from the "Authorization" header
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // Validates the JWT token
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build();
            parser.parseClaimsJws(token);  // Validates the JWT token claims
            return true;  // Token is valid
        } catch (Exception e) {
            return false;  // Token is invalid or expired
        }
    }

    // Extracts the username from the token
    public String getEmailFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();
        return parser.parseClaimsJws(token)
                .getBody()
                .getSubject();  // Extracts the username from the token
    }
    public long getIdFromToken(String token) {
    JwtParser parser = Jwts.parser()
            .setSigningKey(secretKey)
            .build();

    Claims claims = parser.parseClaimsJws(token)
            .getBody();

    // Extract the "id" claim as a long value
    return claims.get("id", Long.class);
}

    // Extracts roles from the token, assuming they are prefixed with 'ROLE_'
    public List<String> getRolesFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();
        return (List<String>) parser.parseClaimsJws(token)
                .getBody()
                .get("roles");  // Extracts the roles from the token claims
    }
}
