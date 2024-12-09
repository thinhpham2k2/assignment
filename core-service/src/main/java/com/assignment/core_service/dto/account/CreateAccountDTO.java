package com.assignment.core_service.dto.account;

import com.assignment.core_service.validation.interfaces.EmailConstraint;
import com.assignment.core_service.validation.interfaces.UsernameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO implements Serializable {

    @NotNull(message = "{message.username.require}")
    @UsernameConstraint
    private String userName;

    @NotNull(message = "{message.password.require}")
    @Size(min = 2, max = 100, message = "{message.password.size}")
    private String password;

    @NotNull(message = "{message.phone.require}")
    @Size(min = 7, max = 20, message = "{message.phone.size}")
    private String phone;

    @NotNull(message = "{message.email.require}")
    @EmailConstraint
    private String email;
}
