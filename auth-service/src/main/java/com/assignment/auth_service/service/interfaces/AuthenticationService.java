package com.assignment.auth_service.service.interfaces;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;

public interface AuthenticationService {

    AccountDTO authenticate(String userName, String password);

    AccountDTO register(CreateAccountDTO create);

    boolean isDuplicateUsername(String userName);
}
