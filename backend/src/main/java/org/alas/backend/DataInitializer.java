package org.alas.backend;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alas.backend.documents.User;
import org.alas.backend.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//    @EventListener(value = ApplicationReadyEvent.class)
    public void init(){
        this.userRepository.deleteAll()
                .thenMany(
                        Flux.just("user", "admin")
                                .flatMap(username -> {
                                    List<String> roles = "user".equals(username) ?
                                            Arrays.asList("ROLE_USER") : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                                    User user = User.builder()
                                            .roles(roles)
                                            .username(username)
                                            .fullname("Test User")
                                            .password(passwordEncoder.encode("password"))
                                            .email(username + "@example.com")
                                            .build();

                                    return this.userRepository.save(user);
                                })
                ).subscribe();
    }
}
