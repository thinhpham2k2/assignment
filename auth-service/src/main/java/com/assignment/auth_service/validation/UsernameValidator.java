package com.assignment.auth_service.validation;

import com.assignment.auth_service.service.interfaces.AuthenticationService;
import com.assignment.auth_service.util.Constant;
import com.assignment.auth_service.validation.interfaces.UsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, Object> {

    private final MessageSource messageSource;

    private final AuthenticationService authenticationService;

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        try {

            constraintValidatorContext.disableDefaultConstraintViolation();
            if (value.toString().matches("[a-z0-9]{3,30}")) {

                String userName = value.toString();
                if (!authenticationService.isDuplicateUsername(userName)) {
                    return true;
                }
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(messageSource.getMessage
                                (Constant.INVALID_DUPLICATE_USERNAME, null, LocaleContextHolder.getLocale()))
                        .addConstraintViolation();
            } else {

                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(messageSource.getMessage
                                (Constant.INVALID_FORMAT_USERNAME, null, LocaleContextHolder.getLocale()))
                        .addConstraintViolation();
            }

            return false;
        } catch (Exception e) {

            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(messageSource.getMessage
                            (Constant.INVALID_USERNAME, null, LocaleContextHolder.getLocale()))
                    .addConstraintViolation();
            return false;
        }
    }
}
