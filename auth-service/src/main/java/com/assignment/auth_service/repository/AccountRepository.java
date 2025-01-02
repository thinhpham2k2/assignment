package com.assignment.auth_service.repository;

import com.assignment.auth_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserNameAndStatus(String userName, boolean status);

    Boolean existsByUserName(String userName);

    Optional<Account> findByIdAndStatus(long id, boolean status);
}
