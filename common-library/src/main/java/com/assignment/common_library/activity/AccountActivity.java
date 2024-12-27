package com.assignment.common_library.activity;

import com.assignment.common_library.dto.account.UpdateAccountDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface AccountActivity {


    @ActivityMethod
    void updateAccount(UpdateAccountDTO update, long id);
}
