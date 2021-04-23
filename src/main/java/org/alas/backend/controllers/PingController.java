package org.alas.backend.controllers;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<String> ping(KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        String user_id = principal.getKeycloakSecurityContext().getToken().getSubject();
//        return ResponseEntity.ok(user_id);
        return ResponseEntity.ok("Server Alive");
    }
}
