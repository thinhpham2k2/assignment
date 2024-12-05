package com.assignment.auth_service.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String getUserNameFromJWT(String token);

    boolean isValidToken(String token, UserDetails userDetails);
}
