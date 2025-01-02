package com.assignment.core_service.service;

import com.assignment.common_library.AccountRequest;
import com.assignment.common_library.AccountResponse;
import com.assignment.common_library.AccountServiceGrpc;
import com.assignment.core_service.mapper.UserMapper;
import com.assignment.core_service.util.Constant;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

    private final UserMapper userMapper;

    @GrpcClient("account-service")
    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // gRPC
        AccountRequest request = AccountRequest.newBuilder()
                .setUsername(username).setStatus(true).build();
        AccountResponse response = accountServiceBlockingStub.getAccountByUsername(request);

        if(!response.getIsExist()){

            throw new UsernameNotFoundException(
                    messageSource.getMessage(Constant.INVALID_ACCOUNT, null, LocaleContextHolder.getLocale()));
        }

        return userMapper.responseToUserDetail(response);
    }
}
