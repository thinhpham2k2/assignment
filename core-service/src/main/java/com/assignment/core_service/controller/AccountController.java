package com.assignment.core_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "\uD83D\uDC64 Account API")
@RequestMapping("/api/v1/core/accounts")
@SecurityRequirement(name = "Authorization")
public class AccountController {
}
