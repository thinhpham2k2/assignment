package com.assignment.core_service.service;

import com.assignment.core_service.dto.response.GitHubUserDTO;
import com.assignment.core_service.service.interfaces.GitHubService;
import com.assignment.core_service.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;

@Service
@RequiredArgsConstructor
public class GitHubServiceImpl implements GitHubService {

    @Value("${github.api.base-url}")
    private String githubApiBaseUrl;

    private final MessageSource messageSource;

    @Override
    public GitHubUserDTO getGithubUserProfile(String username) {

        try {

            ResponseEntity<GitHubUserDTO> rs = new RestTemplate().getForEntity(
                    githubApiBaseUrl + "/users/" + username, GitHubUserDTO.class);

            return rs.getBody();
        } catch (Exception e) {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_GITHUB_PROFILE, null, LocaleContextHolder.getLocale()));
        }
    }
}
