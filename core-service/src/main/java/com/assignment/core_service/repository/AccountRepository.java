package com.assignment.core_service.repository;

import com.assignment.core_service.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByIdAndStatus(long id, boolean status);

    Optional<Account> findByIdAndStatus(long id, boolean status);

    @Query("SELECT a FROM Account a " +
            "WHERE a.status = ?1 " +
            "AND (a.userName LIKE %?2% " +
            "OR a.phone LIKE %?2% " +
            "OR a.email LIKE %?2%)")
    Page<Account> findAllByCondition(boolean status, String search, Pageable pageable);
}
