package com.assignment.auth_service.validation;

import com.assignment.auth_service.util.Constant;
import com.assignment.auth_service.validation.interfaces.EmailConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailConstraint, Object> {

    private final MessageSource messageSource;

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        try {

            constraintValidatorContext.disableDefaultConstraintViolation();
            if (value.toString().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                return true;
            } else {

                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(messageSource.getMessage
                                (Constant.INVALID_FORMAT_EMAIL, null, LocaleContextHolder.getLocale()))
                        .addConstraintViolation();
            }
            return false;
        } catch (Exception e) {

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(messageSource.getMessage
                            (Constant.INVALID_EMAIL, null, LocaleContextHolder.getLocale()))
                    .addConstraintViolation();
            return false;
        }
    }
}
