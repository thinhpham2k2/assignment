package com.assignment.core_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUserDTO implements Serializable {

    private Long id;
    private String login;
    private String name;
    @JsonProperty("avatar_url")
    private String avatar;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String bio;
    @JsonProperty("twitter_username")
    private String twitterUsername;
    @JsonProperty("public_repos")
    private int publicRepos;
    private int followers;
    private int following;
    private boolean hireable;
}
