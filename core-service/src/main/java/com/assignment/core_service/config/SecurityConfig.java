package com.assignment.core_service.config;

import com.assignment.core_service.util.Constant;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityConfig {

    private final MessageSource messageSource;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(
                "/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setContentType("text/plain; charset=UTF-8");
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setHeader("message", "Unauthorized");
                                    response.getWriter().write(
                                            messageSource.getMessage(Constant.UNAUTHORIZED, null, LocaleContextHolder.getLocale()));
                                }).accessDeniedHandler(
                                (request, response, accessDeniedException) -> {
                                    response.setContentType("text/plain; charset=UTF-8");
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.setHeader("message", "Access Denied!");
                                    response.getWriter().write(
                                            messageSource.getMessage(Constant.FORBIDDEN, null, LocaleContextHolder.getLocale()));
                                }));

        return http.build();
    }
}
