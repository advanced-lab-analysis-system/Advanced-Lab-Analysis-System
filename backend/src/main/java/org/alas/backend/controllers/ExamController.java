package org.alas.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.alas.backend.dto.*;
import org.alas.backend.handlers.JudgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.alas.backend.documents.Exam;

import org.alas.backend.handlers.ExamHandler;
import org.alas.backend.handlers.SubmissionHandler;

import java.io.IOException;


@RestController
public class ExamController {

    @Autowired
    private ExamHandler examHandler;

    @Autowired
    private SubmissionHandler submissionHandler;

    @Autowired
    private JudgeHandler judgeHandler;

    @PostMapping("/author/exams")
    public ResponseEntity<?> createExam(@RequestBody Exam exam){
        examHandler.createExam(exam);
        return new ResponseEntity<>("Exam Created", HttpStatus.CREATED);
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
    public ResponseEntity<?> newSubmission(@RequestParam String examId, @RequestParam String candidateId,
                                           @RequestParam String questionType, @RequestBody String visit) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (questionType){
            case "mcq":
                VisitDTO  visitDTO = objectMapper.readValue(visit,VisitDTO.class);
                Mono<?> responseMono = submissionHandler.addVisit(examId, candidateId, visitDTO);
                return new ResponseEntity<>(responseMono,HttpStatus.CREATED);
            case "coding":
                CodeDTO codeDTO = objectMapper.readValue(visit,CodeDTO.class);
                JudgeRequestDTO judgeRequestDTO = new JudgeRequestDTO(70,codeDTO.getCode(),codeDTO.getCustomInput());
                Mono<GetSubmissionResponse> responseMono1 = judgeHandler.createSubmission(judgeRequestDTO)
                        .flatMap(creationResponse -> judgeHandler.getSubmission(creationResponse.getToken()));
                if(codeDTO.getSubmit())
                    responseMono1.map(getSubmissionResponse ->
                            submissionHandler.addCodeSubmission(examId,candidateId, codeDTO.getQuestionId(), getSubmissionResponse).subscribe()).subscribe();
                return new ResponseEntity<>(responseMono1,HttpStatus.CREATED);
            default:
                throw new IllegalStateException("Unexpected value: " + questionType);
        }
    }
}
