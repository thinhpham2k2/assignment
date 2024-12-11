package com.assignment.core_service.repository;

import com.assignment.core_service.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository  extends JpaRepository<Supplier, Long> {

    Boolean existsByIdAndStatus(long id, boolean status);

    Optional<Supplier> findByIdAndStatus(long id, boolean status);

    @Query("SELECT s FROM Supplier s " +
            "WHERE s.status = ?1 " +
            "AND (s.supplierName LIKE %?2% " +
            "OR s. description LIKE %?2%)")
    Page<Supplier> findAllByCondition(boolean status, String search, Pageable pageable);
}
