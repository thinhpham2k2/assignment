package com.assignment.core_service.mockserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubControllerIntegrationTest {

    static ClientAndServer mockServer;

    @BeforeAll
    static void beforeAll() {

        mockServer = ClientAndServer.startClientAndServer();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {

        registry.add("github.api.base-url", () -> "http://localhost:" + mockServer.getPort());
    }

    @AfterAll
    static void afterAll() {

        mockServer.stop();
    }

    @BeforeEach
    void setUp() {

        mockServer.reset();
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetGithubUserProfile() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(JsonBody.json("""
                                {
                                	"login": "%s",
                                	"name": null,
                                	"twitter_username": null,
                                	"public_repos": 7
                                }
                                """.formatted(username))));

        this.mockMvc.perform(get("/api/v1/core/github/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(username)))
                .andExpect(jsonPath("$.name").value(nullValue()))
                .andExpect(jsonPath("$.twitter_username").value(nullValue()))
                .andExpect(jsonPath("$.public_repos", is(7)));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(1));
    }

    @Test
    void shouldGetFailureResponseWhenGitHubApiFailed() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(500));

        String expectedError = "Fail to fetch GitHub profile";
        this.mockMvc.perform(get("/api/v1/core/github/users/{username}", username)
                        .header("Accept-Language", "en"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("text/plain"))
                .andExpect(content().string(expectedError));
    }
}
