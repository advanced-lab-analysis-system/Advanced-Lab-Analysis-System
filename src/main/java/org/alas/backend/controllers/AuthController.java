package org.alas.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.alas.backend.dataobjects.AuthenticationRequest;
import org.alas.backend.repositories.UserRepository;
import org.alas.backend.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider tokenProvider;

    private final ReactiveAuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<AuthenticationRequest> authRequest) {

        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
                        .map(this.tokenProvider::createToken).map(token -> {
                            String[] tokenAndRole = new String[2];
                            tokenAndRole[0] = token;
                            tokenAndRole[1] = userRepository.findByUsername(login.getUsername()).block().getRoles().get(0);
                            return tokenAndRole;
                        })
                )
                .map(tokenAndRole -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAndRole[0]);
                    Map<String, String> tokenBody = new HashMap<>();
                    tokenBody.put("access_token", tokenAndRole[0]);
                    tokenBody.put("role", tokenAndRole[1]);
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });

    }

    @ExceptionHandler
    public ResponseEntity<?> badCredentials(BadCredentialsException e){
        return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }

}
