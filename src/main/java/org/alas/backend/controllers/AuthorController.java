package org.alas.backend.controllers;

import org.alas.backend.documents.Exam;
import org.alas.backend.dataobjects.dto.ExamDTO;
import org.alas.backend.handlers.ExamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private ExamHandler examHandler;

    @GetMapping("/exams")
    public ResponseEntity<Flux<ExamDTO>> getAllAuthorExams(@RequestParam String authorId) {
        Flux<ExamDTO> examFlux = examHandler.getAllAuthorExams(authorId);
        return new ResponseEntity<>(examFlux, HttpStatus.OK);
    }


    @PostMapping("/exams")
    public ResponseEntity<String> createExam(@RequestBody Exam exam) {
        examHandler.createExam(exam);
        return new ResponseEntity<>("Exam Created", HttpStatus.CREATED);
    }

    @GetMapping("/exams/{examId}")
    public ResponseEntity<Mono<Exam>> getExamWithAnswersById(@PathVariable String examId) {
        Mono<Exam> examDataDTOMono = examHandler.getExamWithAnswers(examId);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
    }

    @GetMapping("/exams/{examId}/end")
    public ResponseEntity<String> endExam(@PathVariable String examId) {
        examHandler.endExam(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
