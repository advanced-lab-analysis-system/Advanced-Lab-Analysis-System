package org.alas.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PingController {

    @GetMapping(value = "/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("Server Alive", HttpStatus.OK);
    }
}
