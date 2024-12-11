package com.assignment.core_service.validation.interfaces;

import com.assignment.core_service.util.Constant;
import com.assignment.core_service.validation.SupplierValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SupplierValidator.class)
public @interface SupplierConstraint {

    String message() default "{" + Constant.INVALID_SUPPLIER + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
