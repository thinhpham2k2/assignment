package com.assignment.core_service.validation;

import com.assignment.core_service.service.interfaces.SupplierService;
import com.assignment.core_service.validation.interfaces.SupplierConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SupplierValidator implements ConstraintValidator<SupplierConstraint, Object> {

    private final SupplierService supplierService;

    @Override
    public void initialize(SupplierConstraint constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        try {

            return supplierService.existsById(Long.parseLong(value.toString()));
        } catch (Exception e) {

            return false;
        }
    }
}
