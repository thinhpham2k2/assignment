package com.assignment.core_service.service;

import com.assignment.core_service.repository.AccountRepository;
import com.assignment.core_service.service.interfaces.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public boolean isDuplicateUsername(String userName) {

        return accountRepository.existsByUserName(userName);
    }
}
