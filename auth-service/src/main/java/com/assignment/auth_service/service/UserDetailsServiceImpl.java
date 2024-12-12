package com.assignment.auth_service.service;

import com.assignment.auth_service.repository.AccountRepository;
import com.assignment.auth_service.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MessageSource messageSource;

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return accountRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageSource.getMessage(Constant.INVALID_ACCOUNT, null, LocaleContextHolder.getLocale())));
    }
}
