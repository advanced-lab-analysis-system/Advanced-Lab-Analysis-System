package org.alas.backend.controllers;

import org.alas.backend.documents.Exam;
import org.alas.backend.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ExamController {

    @Autowired
    ExamService examService;

    @PostMapping("/author/exam")
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        examService.createExam(exam).subscribe();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/candidate/exams")
    public ResponseEntity<?> getAllExams(){
        Flux<Exam> examlist=examService.getAllExams();
        return new ResponseEntity<>(examlist,HttpStatus.OK);
    }

}
