package com.assignment.core_service.resilience4j;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubControllerTest {

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
    void testCircuitBreaker() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(500));

        for (int i = 0; i < 5; i++) {

            this.mockMvc.perform(get("/api/v1/core/github/circuit-breaker/users/{username}", username)
                            .header("Accept-Language", "en"))
                    .andExpect(status().isBadRequest());
        }

        for (int i = 0; i < 3; i++) {

            this.mockMvc.perform(get("/api/v1/core/github/circuit-breaker/users/{username}", username)
                            .header("Accept-Language", "en"))
                    .andExpect(status().isServiceUnavailable());
        }

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(5));
    }

    @Test
    void testRetry() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(200));

        this.mockMvc.perform(get("/api/v1/core/github/retry/users/{username}", username)
                        .header("Accept-Language", "en"));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(1));

        mockServer.reset();

        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(500));

        this.mockMvc.perform(get("/api/v1/core/github/retry/users/{username}", username)
                        .header("Accept-Language", "en"))
                .andExpect(content().string("Fail to fetch GitHub profile"));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(3));
    }

    @Test
    void testTimeLimiter() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(200));

        this.mockMvc.perform(get("/api/v1/core/github/time-limiter/users/{username}", username)
                .header("Accept-Language", "en"));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(1));
    }
}
