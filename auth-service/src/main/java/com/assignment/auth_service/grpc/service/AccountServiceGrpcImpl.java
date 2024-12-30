package com.assignment.auth_service.grpc.service;

import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.repository.AccountRepository;
import com.assignment.common_library.AccountRequest;
import com.assignment.common_library.AccountResponse;
import com.assignment.common_library.AccountServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class AccountServiceGrpcImpl extends AccountServiceGrpc.AccountServiceImplBase {

    private final AccountRepository accountRepository;

    @Override
    public void deleteAccount(AccountRequest request, StreamObserver<AccountResponse> responseObserver) {

        boolean isSucceed = true;
        Optional<Account> account = accountRepository.findByIdAndStatus(request.getId(), true);
        if (account.isPresent()) {

            try {

                account.get().setStatus(false);
                accountRepository.save(account.get());
            } catch (Exception e) {

                isSucceed = false;
                log.info("Delete account fail");
            }
        } else {

            isSucceed = false;
            log.info("Invalid account with id: {}", request.getId());
        }
        AccountResponse response = AccountResponse.newBuilder()
                .setIsSucceeded(isSucceed)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
