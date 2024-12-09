package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.category.CategoryDTO;
import com.assignment.core_service.dto.category.CreateCategoryDTO;
import com.assignment.core_service.dto.category.UpdateCategoryDTO;
import org.springframework.data.domain.Page;

import java.sql.SQLException;

public interface CategoryService {

    boolean existsById(long id);

    CategoryDTO findById(long id);

    Page<CategoryDTO> findAllByCondition(String search, String sort, int page, int limit);

    void create(CreateCategoryDTO create) throws SQLException;

    void update(UpdateCategoryDTO update, Long id);

    void delete(Long id);
}
