package com.assignment.core_service.repository;

import com.assignment.core_service.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository  extends JpaRepository<Supplier, Long> {

    Boolean existsByIdAndStatus(long id, boolean status);
}
