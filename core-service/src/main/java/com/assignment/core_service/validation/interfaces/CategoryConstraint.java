package com.assignment.core_service.validation.interfaces;

import com.assignment.core_service.util.Constant;
import com.assignment.core_service.validation.CategoryValidator;
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
@Constraint(validatedBy = CategoryValidator.class)
public @interface CategoryConstraint {

    String message() default "{" + Constant.INVALID_CATEGORY + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
