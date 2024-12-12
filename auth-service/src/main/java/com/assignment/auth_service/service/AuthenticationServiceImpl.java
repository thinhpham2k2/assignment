package com.assignment.auth_service.service;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.mapper.AccountMapper;
import com.assignment.auth_service.rabbitmq.publisher.RabbitMQProducer;
import com.assignment.auth_service.repository.AccountRepository;
import com.assignment.auth_service.service.interfaces.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final RabbitMQProducer rabbitMQProducer;

    private final AccountRepository accountRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public AccountDTO authenticate(String userName, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return accountMapper.entityToDTO((Account) authentication.getPrincipal());
    }

    @Override
    public AccountDTO register(CreateAccountDTO create) {

        create.setPassword(passwordEncoder.encode(create.getPassword()));

        try {

            rabbitMQProducer.sendMessage(create);
        }catch (Exception e){

            LOGGER.info("Send message fail -> {}", create);
        }

        return accountMapper.entityToDTO(accountRepository.save(accountMapper.createToEntity(create)));
    }

    @Override
    public boolean isDuplicateUsername(String userName) {

        return accountRepository.existsByUserName(userName);
    }
}
