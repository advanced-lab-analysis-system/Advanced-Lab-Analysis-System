package org.alas.backend.controllers;

import org.alas.backend.documents.Ping;
import org.alas.backend.handlers.PingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
public class PingController {

    @Autowired
    private PingHandler pingHandler;

    @GetMapping(value = "/ping/save")
    public ResponseEntity<?> savePing(){
        Date date = new Date();
        Ping ping = new Ping(date.toString());
        pingHandler.savePing(ping);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/ping/all")
    public ResponseEntity<?> getPings(){
        Flux<Ping> pings = pingHandler.getPings();
        return new ResponseEntity<>(pings, HttpStatus.OK);
    }
}
