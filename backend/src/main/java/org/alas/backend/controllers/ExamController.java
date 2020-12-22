package org.alas.backend.controllers;

import org.alas.backend.dto.Exam;
import org.alas.backend.documents.ExamData;
import org.alas.backend.dto.ExamDataDTO;
import org.alas.backend.services.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class ExamController {

    final
    ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/author/exam")
    public ResponseEntity<?> createExam(@RequestBody ExamData examData) {
        Mono<?> examDataMono=examService.createExam(examData);
        return new ResponseEntity<>(examDataMono,HttpStatus.CREATED);
    }

    @GetMapping("/author/exams/{exam_id}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String exam_id){
        //Mono<ExamData> examDataMono=examService.getExamById(exam_id);
        Mono<ExamData> examDataDTOMono=examService.getExamWithAnswersById(exam_id);
        return new ResponseEntity<>(examDataDTOMono,HttpStatus.OK);
    }

    @GetMapping("/candidate/exams")
    public ResponseEntity<?> getAllExams(){
        Flux<Exam> examFlux=examService.getAllExams();
        return new ResponseEntity<>(examFlux,HttpStatus.OK);
    }

    @GetMapping("/candidate/exams/{exam_id}")
    public ResponseEntity<?> getExamWithoutAnswersById(@PathVariable String exam_id){
        //Mono<ExamData> examDataMono=examService.getExamById(exam_id);
        Mono<ExamDataDTO> examDataDTOMono=examService.getExamWithoutAnswersById(exam_id);
        return new ResponseEntity<>(examDataDTOMono,HttpStatus.OK);
    }
}
