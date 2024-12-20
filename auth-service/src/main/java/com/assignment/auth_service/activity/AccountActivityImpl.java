package com.assignment.auth_service.activity;

import com.assignment.auth_service.activity.interfaces.AccountActivity;
import com.assignment.auth_service.dto.account.UpdateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.mapper.AccountMapper;
import com.assignment.auth_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountActivityImpl.class);

    @Override
    public void updateAccount(UpdateAccountDTO update, long id) {

        Optional<Account> account = accountRepository.findByIdAndStatus(id, true);
        if (account.isPresent()) {

            try {

                accountRepository.save(accountMapper.updateToEntity(update, account.get()));
            } catch (Exception e) {

                LOGGER.info("Update account fail with id: {}", id);
            }
        }
    }
}
