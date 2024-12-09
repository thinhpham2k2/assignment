package com.assignment.auth_service.dto.auth;

import com.assignment.auth_service.dto.account.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO implements Serializable {

    private String token;
    private AccountDTO user;
}
