package com.assignment.core_service.validation;

import com.assignment.core_service.service.interfaces.CategoryService;
import com.assignment.core_service.validation.interfaces.CategoryConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<CategoryConstraint, Object> {

    private final CategoryService categoryService;

    @Override
    public void initialize(CategoryConstraint constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        try {

            return categoryService.existsById(Long.parseLong(value.toString()));
        } catch (Exception e) {

            return false;
        }
    }
}
