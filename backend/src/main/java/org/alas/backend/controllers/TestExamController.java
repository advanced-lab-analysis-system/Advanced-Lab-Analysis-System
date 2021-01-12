package org.alas.backend.controllers;

import org.alas.backend.documents.TestExam;
import org.alas.backend.handlers.TestExamHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestExamController {
    final
    TestExamHandler testExamHandler;

    public TestExamController(TestExamHandler testExamHandler) {
        this.testExamHandler = testExamHandler;
    }

    @PostMapping("/v2/author/exam")
    public ResponseEntity<?> createExam(@RequestBody TestExam testExam) {
        Mono<?> examDataMono = testExamHandler.createExam(testExam);
        return new ResponseEntity<>(examDataMono, HttpStatus.CREATED);
    }

    @GetMapping("/v2/author/exams/{exam_id}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String exam_id) {
        Mono<TestExam> testExamMono = testExamHandler.getExamWithAnswersById(exam_id);
        return new ResponseEntity<>(testExamMono, HttpStatus.OK);
    }

    @GetMapping("/v2/candidate/exams")
    public ResponseEntity<?> getAllExams() {
        Flux<TestExam> testExamFlux = testExamHandler.getAllExams();
        return new ResponseEntity<>(testExamFlux, HttpStatus.OK);
    }
}
