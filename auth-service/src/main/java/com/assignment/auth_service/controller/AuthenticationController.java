package com.assignment.auth_service.controller;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.dto.auth.JwtResponseDTO;
import com.assignment.auth_service.dto.auth.LoginFormDTO;
import com.assignment.auth_service.service.interfaces.AuthenticationService;
import com.assignment.auth_service.service.interfaces.JwtService;
import com.assignment.auth_service.util.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@RestController
@Tag(name = "\uD83D\uDD10 Authenticate API")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final MessageSource messageSource;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "User register account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> register(
            @RequestBody @Validated CreateAccountDTO createDto
    ) throws MethodArgumentTypeMismatchException {

        AccountDTO account = authenticationService.register(createDto);
        String token = jwtService.generateToken(new HashMap<>(), account);
        if (account != null) {

            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(token, account);
            return ResponseEntity.ok().body(jwtResponseDTO);
        } else {

            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.REGISTER_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }

    @PostMapping("/authenticate")
    @Operation(summary = "User login to system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = JwtResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Fail", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> authenticate(
            @RequestBody @Validated LoginFormDTO request
    ) throws MethodArgumentTypeMismatchException {

        String userName = request.getUserName();
        String password = request.getPassword();

        if (userName == null || userName.isEmpty()) {

            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.USERNAME_MISSING, null, LocaleContextHolder.getLocale()));
        }

        if (password == null || password.isEmpty()) {

            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.PASSWORD_MISSING, null, LocaleContextHolder.getLocale()));
        }
        try {

            AccountDTO account = authenticationService.authenticate(userName, password);
            String token = jwtService.generateToken(new HashMap<>(), account);
            if (account != null) {

                JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(token, account);
                return ResponseEntity.ok().body(jwtResponseDTO);
            } else {

                return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(
                        messageSource.getMessage(Constant.LOGIN_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } catch (Exception e) {

            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.LOGIN_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }
}
