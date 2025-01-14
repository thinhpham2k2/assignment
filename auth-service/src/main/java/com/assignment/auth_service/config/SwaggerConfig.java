package com.assignment.auth_service.config;

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
                        url = "http://localhost:8080/auth"
                ),
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8081"
                )
        })
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Authenticate Service")
                        .version("1.0")
                        .description("The Authenticate Service is a crucial component of applications, " +
                                "responsible for verifying the identity of users or systems")
                        .contact(new Contact()
                                .name("thinhpham2k2")
                                .email("thinhphamquoc9999@gmail.com")));
    }
}
