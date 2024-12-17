package com.assignment.core_service.controller;

import com.assignment.core_service.dto.response.GitHubUserDTO;
import com.assignment.core_service.service.interfaces.GitHubService;
import com.assignment.core_service.util.Constant;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "404", description = "Not Found", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))}),
    })
    public ResponseEntity<?> getGithubUserProfile(@PathVariable String username) {

        GitHubUserDTO githubUserProfile = gitHubService.getGithubUserProfile(username);

        if (githubUserProfile != null) {

            return ResponseEntity.status(HttpStatus.OK).body(githubUserProfile);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body(
                    messageSource.getMessage(Constant.INVALID_GITHUB_PROFILE, null, LocaleContextHolder.getLocale()));
        }
    }
}
