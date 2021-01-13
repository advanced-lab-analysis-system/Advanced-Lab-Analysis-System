package org.alas.backend;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alas.backend.documents.User;
import org.alas.backend.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/*
 * IMP - Use this Component to create test users with predefined data and roles
 * */

//@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        this.userRepository.deleteAll()
                .thenMany(
                        Flux.just("author", "admin")
                                .flatMap(username -> {
                                    List<String> roles = "author".equals(username) ?
                                            Arrays.asList("ROLE_AUTHOR") : Arrays.asList("ROLE_ADMIN");

                                    User user = User.builder()
                                            .roles(roles)
                                            .username(username)
                                            .fullName("Test " + username.substring(0, 1).toUpperCase() + username.substring(1))
                                            .password(passwordEncoder.encode("password"))
                                            .email(username + "@example.com")
                                            .build();

                                    return this.userRepository.save(user);
                                })
                ).subscribe();

        Flux.just("user_1", "user_2", "user_3", "user_4", "user_5", "user_6", "user_7", "user_8", "user_9", "user_10")
                .flatMap(username -> {
                    List<String> roles = Arrays.asList("ROLE_CANDIDATE");

                    User user = User.builder()
                            .roles(roles)
                            .username(username)
                            .fullName("Test " + username.substring(0, 1).toUpperCase() + username.substring(1))
                            .password(passwordEncoder.encode("password"))
                            .email(username + "@example.com")
                            .build();

                    return this.userRepository.save(user);
                }).subscribe();
    }
}
