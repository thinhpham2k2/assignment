package com.assignment.core_service.repository;

import com.assignment.core_service.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByIdAndStatus(long id, boolean status);

    Optional<Category> findByIdAndStatus(long id, boolean status);

    @Query("SELECT c FROM Category c " +
            "WHERE c.status = ?1 " +
            "AND c.categoryName LIKE %?2% " +
            "OR c. description LIKE %?2%")
    Page<Category> findAllByCondition(boolean status, String search, Pageable pageable);
}
