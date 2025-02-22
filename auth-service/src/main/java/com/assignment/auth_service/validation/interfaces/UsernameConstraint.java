package com.assignment.auth_service.validation.interfaces;

import com.assignment.auth_service.util.Constant;
import com.assignment.auth_service.validation.UsernameValidator;
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
@Constraint(validatedBy = UsernameValidator.class)
public @interface UsernameConstraint {

    String message() default "{" + Constant.INVALID_USERNAME + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
