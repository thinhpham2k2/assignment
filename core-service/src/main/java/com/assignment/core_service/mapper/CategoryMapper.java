package com.assignment.core_service.mapper;

import com.assignment.core_service.dto.category.CategoryDTO;
import com.assignment.core_service.dto.category.CreateCategoryDTO;
import com.assignment.core_service.dto.category.UpdateCategoryDTO;
import com.assignment.core_service.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO entityToDTO(Category entity);

    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Category createToEntity(CreateCategoryDTO create);

    @Mapping(target = "productList", ignore = true)
    Category updateToEntity(UpdateCategoryDTO update, @MappingTarget Category entity);
}
