package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.category.CategoryDTO;
import com.assignment.core_service.dto.category.CreateCategoryDTO;
import com.assignment.core_service.dto.category.UpdateCategoryDTO;
import org.springframework.data.web.PagedModel;

public interface CategoryService {

    boolean existsById(long id);

    CategoryDTO findById(long id);

    PagedModel<CategoryDTO> findAllByCondition(String search, String sort, int page, int limit);

    void create(CreateCategoryDTO create);

    void update(UpdateCategoryDTO update, long id);

    void delete(long id);
}
