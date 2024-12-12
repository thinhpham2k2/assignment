package com.assignment.core_service.repository;

import com.assignment.core_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByIdAndStatus(long id, boolean status);

    Optional<Product> findByIdAndStatus(long id, boolean status);

    @Query("SELECT p FROM Product p " +
            "WHERE p.status = ?1 " +
            "AND (:#{#categoryIds.size()} = 0 OR p.category.id IN ?2) " +
            "AND (:#{#supplierIds.size()} = 0 OR p.supplier.id IN ?3) " +
            "AND (p.productName LIKE %?4% " +
            "OR p.category.categoryName LIKE %?4% " +
            "OR p.supplier.supplierName LIKE %?4% " +
            "OR p.description LIKE %?4%)")
    Page<Product> findAllByCondition(boolean status, List<Long> categoryIds, List<Long> supplierIds,
                                     String search, Pageable pageable);
}
