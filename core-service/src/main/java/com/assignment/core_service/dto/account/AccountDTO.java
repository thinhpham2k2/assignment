package com.assignment.core_service.dto.account;

import com.assignment.core_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Serializable {

    private Long id;
    private Role role;
    private String userName;
    private String phone;
    private String email;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Boolean state;
    private Boolean status;
}
