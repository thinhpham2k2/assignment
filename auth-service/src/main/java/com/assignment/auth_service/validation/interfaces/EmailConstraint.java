package com.assignment.auth_service.validation.interfaces;

import com.assignment.auth_service.util.Constant;
import com.assignment.auth_service.validation.EmailValidator;
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
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailConstraint {

    String message() default "{" + Constant.INVALID_EMAIL + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
