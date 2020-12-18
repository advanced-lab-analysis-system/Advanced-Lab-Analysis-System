package org.alas.backend.controllers;

import org.alas.backend.documents.Exam;
import org.alas.backend.repositories.ExamsRepository;
import org.alas.backend.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ExamController {

    @Autowired
    ExamService examService;

    @PostMapping("/exam")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createExam(@RequestBody Exam exam){
        examService.createExam(exam);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
