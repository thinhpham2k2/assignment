package com.assignment.core_service.dto.account;

import com.assignment.core_service.entity.Role;
import com.assignment.core_service.util.Constant;
import com.assignment.core_service.validation.interfaces.EmailConstraint;
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
public class UpdateAccountDTO implements Serializable {

    @NotNull(message = "{" + Constant.ROLE_REQUIRE + "}")
    private Role role;

    @NotNull(message = "{" + Constant.PHONE_REQUIRE + "}")
    @Size(min = 7, max = 20, message = "{" + Constant.PHONE_SIZE + "}")
    private String phone;

    @NotNull(message = "{" + Constant.EMAIL_REQUIRE + "}")
    @EmailConstraint
    private String email;

    private Boolean state;
}
