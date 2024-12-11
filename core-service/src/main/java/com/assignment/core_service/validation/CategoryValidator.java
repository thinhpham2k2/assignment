package com.assignment.core_service.validation;

import com.assignment.core_service.service.interfaces.CategoryService;
import com.assignment.core_service.util.Constant;
import com.assignment.core_service.validation.interfaces.CategoryConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<CategoryConstraint, Object> {

    private final MessageSource messageSource;

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

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(messageSource.getMessage
                            (Constant.INVALID_CATEGORY, null, LocaleContextHolder.getLocale()))
                    .addConstraintViolation();
            return false;
        }
    }
}
