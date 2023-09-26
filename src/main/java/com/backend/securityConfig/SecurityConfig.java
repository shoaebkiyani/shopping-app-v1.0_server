package com.backend.securityConfig;

import com.backend.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/register", "/api/v1/login",
                        "/api/v1/products",
                        "/api/v1/single-product/{id}",
                        "/api/v1/category",
                        "/api/v1/cart",
                        "/api/v1/cart/create-cart",
                        "/api/v1/cart/add-to-cart/{productId}",
                        "/api/v1/cart/delete-item/{itemId}",
                        "/api/v1/order",
                        "/api/v1/order/place-order/{cartId}")
                .permitAll()
                .requestMatchers("/api/v1/users", "/api/v1/user/{id}",
                        "/api/v1/add-product",
                        "/api/v1/edit-product/{id}",
                        "/api/v1/delete-product/{id}",
                        "/api/v1/add-category",
                        "/api/v1/edit-category/{id}",
                        "/api/v1/delete-category/{id}"
                ).hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic(withDefaults()).formLogin()
                .and()
                // Add JWT token filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
