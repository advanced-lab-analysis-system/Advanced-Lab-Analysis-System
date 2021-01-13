package org.alas.backend.controllers;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.VisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.alas.backend.documents.Exam;

import org.alas.backend.dto.ExamDTO;
import org.alas.backend.dto.ExamDataDTO;

import org.alas.backend.handlers.ExamHandler;
import org.alas.backend.handlers.SubmissionHandler;

import java.time.Duration;

@RestController
public class ExamController {

    @Autowired
    private ExamHandler examHandler;

    @Autowired
    private SubmissionHandler submissionHandler;

    @PostMapping("/author/exam")
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {
        Mono<?> examDataMono = examHandler.createExam(exam);
        return new ResponseEntity<>(examDataMono, HttpStatus.CREATED);
    }

    @GetMapping("/author/exams/{examId}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String examId) {
        Mono<Exam> examDataDTOMono = examHandler.getExamWithAnswersById(examId);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
    }

    @GetMapping("/author/exams/{examId}/end")
    public ResponseEntity<?> endExam(@PathVariable String examId) {
        examHandler.endExamByExamId(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/candidate/exams")
    public ResponseEntity<?> getAllExams() {
        Flux<ExamDTO> examFlux = examHandler.getAllExams();
        return new ResponseEntity<>(examFlux, HttpStatus.OK);
    }

    @GetMapping("/candidate/exams/{examId}")
    public ResponseEntity<?> getExamWithoutAnswersById(@PathVariable String examId) {
        Mono<ExamDataDTO> examDataDTOMono = examHandler.getExamWithoutAnswersById(examId);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
    }

    @PostMapping("/candidate/submission")
    public ResponseEntity<?> newSubmission(@RequestParam String examId, @RequestParam String candidateId, @RequestBody VisitDTO visit) {
        System.out.println(visit);
        Mono<?> addedSubmission = submissionHandler.addVisit(examId, candidateId, visit);
        //addedSubmission.subscribe();
        return new ResponseEntity<>(addedSubmission, HttpStatus.CREATED);
    }
}
