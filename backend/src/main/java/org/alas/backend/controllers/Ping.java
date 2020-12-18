package org.alas.backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class Ping {

    @GetMapping(value = "/ping")
    public String getPing(){
        return "Server Online";
    }

}
