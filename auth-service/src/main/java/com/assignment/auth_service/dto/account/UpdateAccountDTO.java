package com.assignment.auth_service.dto.account;

import com.assignment.auth_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO implements Serializable {

    private Role role;
    private String phone;
    private String email;
    private Boolean state;
}
