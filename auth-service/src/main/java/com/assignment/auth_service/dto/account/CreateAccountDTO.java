package com.assignment.auth_service.dto.account;

import com.assignment.auth_service.util.Constant;
import com.assignment.auth_service.validation.interfaces.EmailConstraint;
import com.assignment.auth_service.validation.interfaces.UsernameConstraint;
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

    @NotNull(message = "{" + Constant.USERNAME_REQUIRE + "}")
    @UsernameConstraint
    private String userName;

    @NotNull(message = "{" + Constant.PASSWORD_REQUIRE + "}")
    @Size(min = 2, max = 100, message = "{" + Constant.PASSWORD_SIZE + "}")
    private String password;

    @NotNull(message = "{" + Constant.PHONE_REQUIRE + "}")
    @Size(min = 7, max = 20, message = "{" + Constant.PHONE_SIZE + "}")
    private String phone;

    @NotNull(message = "{" + Constant.EMAIL_REQUIRE + "}")
    @EmailConstraint
    private String email;
}
