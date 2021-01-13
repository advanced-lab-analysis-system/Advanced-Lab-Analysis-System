package org.alas.backend.controllers;

import org.alas.backend.spark.services.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@RestController
public class PingController {
    @Autowired
    WordCountService wordCountService;

    @GetMapping(value = "/ping")
    public ResponseEntity<?> savePing() {
        List<String> wordList = Arrays.asList("word 1", "word 1", "word 2", "word 3", "word 3", "word 3");
        Map<String, Long> wordCount = wordCountService.getCount(wordList);

        System.out.println(wordCount);

        return new ResponseEntity<>("Server Alive", HttpStatus.OK);
    }
}
