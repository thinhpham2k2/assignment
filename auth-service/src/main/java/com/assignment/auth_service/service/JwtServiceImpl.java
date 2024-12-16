package com.assignment.auth_service.service;

import com.assignment.auth_service.config.JwtConfig;
import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.service.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    private static final String DEFAULT_KEY = "Y29udHJvbHRpcmVkdHJhcHNob290aHVuZHJlZGxhdWdoc29sZHdpc2Vwcm91ZGRlYXQ=";

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            AccountDTO account) {

        String key = StringUtils.isBlank(jwtConfig.getKey()) ? DEFAULT_KEY : jwtConfig.getKey();
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(account.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String getUserNameFromJWT(String token) {
        return getAllClaims(token).getSubject();
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {

        String userName = getUserNameFromJWT(token);


        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return getAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaims(String token) {

        String key = StringUtils.isBlank(jwtConfig.getKey()) ? DEFAULT_KEY : jwtConfig.getKey();
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
