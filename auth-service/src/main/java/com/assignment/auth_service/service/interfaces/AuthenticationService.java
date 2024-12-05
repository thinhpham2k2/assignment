package com.assignment.auth_service.service.interfaces;

import com.assignment.auth_service.enitity.Account;

public interface AuthenticationService {

    Account authenticate(String userName, String password);
}
