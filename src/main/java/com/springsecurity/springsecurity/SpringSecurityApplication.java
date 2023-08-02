package com.springsecurity.springsecurity;

import com.springsecurity.springsecurity.user.User;
import com.springsecurity.springsecurity.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.isUsernameExists("admin")) return;
            User adminUser = new User("admin", passwordEncoder.encode("admin"), User.Role.ADMIN);
            userService.createUser(adminUser);
        };
    }
}
