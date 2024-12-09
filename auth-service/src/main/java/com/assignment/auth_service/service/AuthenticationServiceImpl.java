package com.assignment.auth_service.service;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.mapper.AccountMapper;
import com.assignment.auth_service.repository.AccountRepository;
import com.assignment.auth_service.service.interfaces.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final AccountMapper accountMapper;

    private final AccountRepository accountRepository;

    @Override
    public AccountDTO authenticate(String userName, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return accountMapper.entityToDTO((Account) authentication.getPrincipal());
    }

    @Override
    public AccountDTO register(CreateAccountDTO createDto) {

        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));
        Account account = accountRepository.save(accountMapper.createToEntity(createDto));
        account.setDateUpdated(null);
        return accountMapper.entityToDTO(account);
    }

    @Override
    public boolean isDuplicateUsername(String userName) {

        return accountRepository.existsByUserName(userName);
    }
}
