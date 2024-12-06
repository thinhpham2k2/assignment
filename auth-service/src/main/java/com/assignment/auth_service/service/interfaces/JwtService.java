package com.assignment.auth_service.service.interfaces;

import com.assignment.auth_service.dto.account.AccountDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateToken(Map<String, Object> extraClaims, AccountDTO account);

    String getUserNameFromJWT(String token);

    boolean isValidToken(String token, UserDetails userDetails);
}
