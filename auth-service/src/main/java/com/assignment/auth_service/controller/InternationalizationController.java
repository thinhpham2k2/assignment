package com.assignment.auth_service.controller;

import com.assignment.auth_service.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "\uD83C\uDF0D I18n API")
@RequestMapping("/api/v1/i8n")
public class InternationalizationController {

    private final MessageSource messageSource;

    @GetMapping("/locale")
    @Operation(summary = "ABC")
    public ResponseEntity<?> readDeclaration() {

        StringBuilder sb = new StringBuilder();

        sb.append(messageSource.getMessage(Constant.NOT_FOUND, null, LocaleContextHolder.getLocale()));

        return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
    }
}
