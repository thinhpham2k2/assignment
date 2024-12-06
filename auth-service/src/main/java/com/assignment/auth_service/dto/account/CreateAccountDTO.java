package com.assignment.auth_service.dto.account;

import com.assignment.auth_service.validation.interfaces.UsernameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    @NotNull(message = "User name is required")
    @UsernameConstraint
    private String userName;

    @NotNull(message = "Password is required")
    @Size(min = 2, max = 100, message = "The length of password is from 2 to 100 characters")
    private String password;

    @NotNull(message = "Phone is required")
    @Size(min = 7, max = 20, message = "The length of phone is from 8 to 20 characters")
    private String phone;

    private String email;
}
