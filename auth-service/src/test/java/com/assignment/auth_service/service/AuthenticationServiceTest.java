package com.assignment.auth_service.service;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.mapper.AccountMapper;
import com.assignment.auth_service.rabbitmq.publisher.RabbitMQProducer;
import com.assignment.auth_service.repository.AccountRepository;
import com.assignment.auth_service.service.interfaces.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
class AuthenticationServiceTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AccountMapper accountMapper;

    @MockBean
    private RabbitMQProducer rabbitMQProducer;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void registerTest() {

        CreateAccountDTO create = CreateAccountDTO
                .builder()
                .userName("admin")
                .password("password")
                .phone("0931234567")
                .email("email@gmail.com")
                .build();

        AccountDTO dto = AccountDTO
                .builder()
                .userName("admin")
                .phone("0931234567")
                .email("email@gmail.com")
                .build();

        Account account = Account
                .builder()
                .userName("admin")
                .password("passwordEncoded")
                .phone("0931234567")
                .email("email@gmail.com")
                .build();

        when(passwordEncoder.encode("password"))
                .thenReturn("passwordEncoded");

        when(accountMapper.createToEntity(create))
                .thenReturn(account);

        when(accountMapper.entityToDTO(account))
                .thenReturn(dto);

        when(accountRepository.save(account))
                .thenReturn(account);

        AccountDTO result = authenticationService.register(create);

        assertEquals(dto, result);
        verify(accountRepository, times(1)).save(account);
    }
}