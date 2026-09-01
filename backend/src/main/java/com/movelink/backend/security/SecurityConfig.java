package com.movelink.backend.config;

import com.movelink.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )

                .csrf(csrf ->
                        csrf.disable()
                )

                .authorizeHttpRequests(auth -> auth

                        // CORS
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // General
                        .requestMatchers(
                                "/",
                                "/error"
                        ).permitAll()

                        // Authentication
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // TEMPORARY:
                        // Used to reset driver availability
                        .requestMatchers(
                                "/api/driver-admin/**"
                        ).permitAll()

                        // TEMPORARY:
                        // Used to verify drivers while testing
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/drivers/*/verify"
                        ).permitAll()

                        // Admin
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        // Driver registration
                        .requestMatchers(
                                "/api/drivers/register"
                        ).permitAll()

                        // Driver documents
                        .requestMatchers(
                                "/api/driver-documents/**"
                        ).hasAnyRole(
                                "DRIVER",
                                "ADMIN"
                        )

                        // Driver location
                        .requestMatchers(
                                "/api/drivers/*/location"
                        ).permitAll()

                        // Other driver endpoints
                        .requestMatchers(
                                "/api/drivers/**"
                        ).hasAnyRole(
                                "DRIVER",
                                "ADMIN"
                        )

                        // Quotes
                        .requestMatchers(
                                "/api/quotes/**"
                        ).permitAll()

                        // Rides
                        .requestMatchers(
                                "/api/rides/**"
                        ).permitAll()

                        // Ratings
                        .requestMatchers(
                                "/api/rating/**"
                        ).hasAnyRole(
                                "CUSTOMER",
                                "DRIVER",
                                "ADMIN"
                        )

                        // Everything else
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}