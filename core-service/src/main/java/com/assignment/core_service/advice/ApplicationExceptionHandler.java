package com.assignment.core_service.advice;

import com.assignment.core_service.util.Constant;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({CallNotPermittedException.class})
    public ResponseEntity<?> handleCallNotPermittedException(CallNotPermittedException ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.SERVICE_UNAVAILABLE, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<?> handleTimeoutException(TimeoutException ex) {

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.REQUEST_TIMEOUT, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler({BulkheadFullException.class})
    public ResponseEntity<?> handleBulkheadFullException(BulkheadFullException ex) {

        return ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.BANDWIDTH_LIMIT_EXCEEDED, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler({RequestNotPermitted.class})
    public ResponseEntity<?> handleRequestNotPermitted(RequestNotPermitted ex) {

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.TOO_MANY_REQUESTS, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.INVALID_PARAMETER, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {

        if (ex instanceof AccessDeniedException) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.TEXT_PLAIN)
                    .body(messageSource.getMessage(Constant.FORBIDDEN, null, LocaleContextHolder.getLocale()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.INTERNAL_SERVER_ERROR, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                .body(messageSource.getMessage(Constant.INVALID_PARAMETER, null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, List<String>> errorMap = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            errorMap.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(errorMap);
    }
}
