package com.assignment.core_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(
                        description = "API Gateway",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8082"
                )
        })
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Core Service")
                        .version("1.0")
                        .description("The Core Service in Spring Boot provides essential business logic and data" +
                                " manipulation functionalities")
                        .contact(new Contact()
                                .name("thinhpham2k2")
                                .email("thinhphamquoc9999@gmail.com")));
    }
}