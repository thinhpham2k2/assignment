package com.assignment.auth_service.activity.interfaces;

import com.assignment.auth_service.dto.account.UpdateAccountDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface AccountActivity {

    @ActivityMethod
    void updateAccount(UpdateAccountDTO update, long id);
}
