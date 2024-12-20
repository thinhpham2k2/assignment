package com.assignment.core_service.activity;

import com.assignment.core_service.activity.interfaces.AccountActivity;
import com.assignment.core_service.dto.account.UpdateAccountDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

    @Override
    public void updateAccount(UpdateAccountDTO update, long id) {

    }
}
