package com.assignment.core_service.resilience4j;


import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Test
    void testBulkhead() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(200));

        Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {

            executorService.execute(() -> {

                try {

                    ResultActions resultActions = this.mockMvc.perform(get(
                            "/api/v1/core/github/bulkhead/users/{username}", username)
                            .header("Accept-Language", "en"));

                    int statusCode = resultActions.andReturn().getResponse().getStatus();
                    responseStatusCount.merge(statusCode, 1, Integer::sum);
                    latch.countDown();
                } catch (Exception ignored) {
                }
            });
        }
        latch.await();
        executorService.shutdown();

        Assertions.assertEquals(2, responseStatusCount.size());
        Assertions.assertTrue(responseStatusCount.containsKey(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value()));
        Assertions.assertTrue(responseStatusCount.containsKey(HttpStatus.OK.value()));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(3));
    }

    @Test
    void testRateLimiter() throws Exception {

        String username = "thinhpham2k2";
        mockServer.when(HttpRequest.request().withMethod("GET").withPath("/users/.*"))
                .respond(HttpResponse.response().withStatusCode(200));

        Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++){

            ResultActions resultActions = this.mockMvc.perform(get(
                    "/api/v1/core/github/rate-limiter/users/{username}", username)
                    .header("Accept-Language", "en"));

            int statusCode = resultActions.andReturn().getResponse().getStatus();
            responseStatusCount.merge(statusCode, 1, Integer::sum);
        }

        Assertions.assertEquals(2, responseStatusCount.size());
        Assertions.assertTrue(responseStatusCount.containsKey(HttpStatus.TOO_MANY_REQUESTS.value()));
        Assertions.assertTrue(responseStatusCount.containsKey(HttpStatus.OK.value()));

        mockServer.verify(HttpRequest.request().withMethod("GET").withPath("/users/.*"),
                VerificationTimes.exactly(5));
    }
}
