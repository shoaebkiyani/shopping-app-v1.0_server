package com.springsecurity.springsecurity.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/register", "/api/v1/login")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(withDefaults()).formLogin();
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/register")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(HttpMethod.GET, "/api/v1/users")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .httpBasic(Customizer.withDefaults())
//                .formLogin();


        return http.build();
    }
}
