package org.alas.backend.controllers;

import org.alas.backend.dto.Exam;
import org.alas.backend.documents.ExamData;
import org.alas.backend.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class ExamController {

    @Autowired
    ExamService examService;

    @PostMapping("/author/exam")
    public ResponseEntity<?> createExam(@RequestBody ExamData examData) {
        examService.createExam(examData).subscribe();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/candidate/exams")
    public ResponseEntity<?> getAllExams(){
        Flux<Exam> examlist=examService.getAllExams();
        return new ResponseEntity<>(examlist,HttpStatus.OK);
    }

    @GetMapping("/candidate/exams/{exam_id}")
    public ResponseEntity<?> getExamById(@PathVariable String exam_id){
        Mono<ExamData> examDataMono=examService.getExamById(exam_id);
        return new ResponseEntity<>(examDataMono,HttpStatus.OK);
    }
}
