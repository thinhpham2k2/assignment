package com.assignment.core_service.controller;

import com.assignment.core_service.dto.response.GitHubUserDTO;
import com.assignment.core_service.entity.Supplier;
import com.assignment.core_service.service.interfaces.GitHubService;
import com.assignment.core_service.util.Constant;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Tag(name = "#\uFE0F⃣ GitHub API")
@RequestMapping("/api/v1/core/github")
public class GitHubController {

    private final GitHubService gitHubService;

    private final MessageSource messageSource;

    @GetMapping("/users/{username}")
    @Operation(summary = "Get GitHub profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfile(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
    }

    @GetMapping("/circuit-breaker/users/{username}")
    @CircuitBreaker(name = "gitHubController")
    @Operation(summary = "Get GitHub profile (Circuit Breaker Pattern)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "503", description = "Service Unavailable", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfileCircuitBreaker(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
    }

    @GetMapping("/retry/users/{username}")
    @Retry(name = "gitHubController", fallbackMethod = "fallbackAfterRetry")
    @Operation(summary = "Get GitHub profile (Retry Pattern)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfileRetry(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
    }

    public String fallbackAfterRetry(Exception ex) {

        return messageSource.getMessage(Constant.SERVICE_UNAVAILABLE, null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/time-limiter/users/{username}")
    @TimeLimiter(name = "gitHubController")
    @Operation(summary = "Get GitHub profile (Time Limiter Pattern)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "408", description = "Request Timeout", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public CompletableFuture<?> getGithubUserProfileTimeLimiter(@PathVariable String username) {

        gitHubService.getGithubUserProfile(username);
        return CompletableFuture.supplyAsync(() -> {

            try {

                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            return new Supplier();
        });
    }

    @GetMapping("/bulkhead/users/{username}")
    @Bulkhead(name = "gitHubController")
    @Operation(summary = "Get GitHub profile (Bulkhead Pattern)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "509", description = "Bandwidth Limit Exceeded", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfileBulkhead(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
    }

    @GetMapping("/rate-limiter/users/{username}")
    @RateLimiter(name = "gitHubController")
    @Operation(summary = "Get GitHub profile (Rate Limiter Pattern)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = GitHubUserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "429", description = "Too Many Requests", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfileRateLimiter(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);
        return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
    }
}
