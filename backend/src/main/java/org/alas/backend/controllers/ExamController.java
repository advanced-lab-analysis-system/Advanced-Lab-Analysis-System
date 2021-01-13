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

    @GetMapping("/author/exams/{exam_id}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String exam_id) {
        Mono<Exam> examDataDTOMono = examHandler.getExamWithAnswersById(exam_id);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
    }

    @GetMapping("/candidate/exams")
    public ResponseEntity<?> getAllExams() {
        Flux<ExamDTO> examFlux = examHandler.getAllExams();
        return new ResponseEntity<>(examFlux, HttpStatus.OK);
    }

    @GetMapping("/candidate/exams/{exam_id}")
    public ResponseEntity<?> getExamWithoutAnswersById(@PathVariable String exam_id) {
        Mono<ExamDataDTO> examDataDTOMono = examHandler.getExamWithoutAnswersById(exam_id);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
    }

    @PostMapping("/candidate/submission")
    public ResponseEntity<?> newSubmission(@RequestParam String examId, @RequestParam String candidateId, @RequestBody VisitDTO visit) {
        System.out.println(visit);
        Mono<?> addedSubmission = submissionHandler.addVisit(examId, candidateId, visit);
        return new ResponseEntity<>(Duration.between(visit.getVisitStartTime(), visit.getVisitEndTime()).getSeconds(), HttpStatus.CREATED);
    }
}
