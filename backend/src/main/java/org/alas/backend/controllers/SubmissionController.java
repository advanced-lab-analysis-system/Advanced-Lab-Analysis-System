package org.alas.backend.controllers;

import org.alas.backend.dto.QuestionMCQ;
import org.alas.backend.handlers.SubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class SubmissionController {

    @Autowired
    SubmissionHandler submissionHandler;

    @PostMapping("/v2/candidate/exams/{exam_id}")
    public ResponseEntity<?> addSubmission(@PathVariable String exam_id, @RequestHeader String candidate_id, @RequestBody QuestionMCQ questionMCQ){
        Mono<?> addedSubmission = submissionHandler.addSubmissionV2(exam_id,candidate_id, questionMCQ);
        return new ResponseEntity<>(addedSubmission,HttpStatus.CREATED);
    }
}
