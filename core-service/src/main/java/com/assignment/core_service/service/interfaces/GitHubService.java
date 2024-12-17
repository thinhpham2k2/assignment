package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.response.GitHubUserDTO;

public interface GitHubService {

    GitHubUserDTO getGithubUserProfile(String username);
}
