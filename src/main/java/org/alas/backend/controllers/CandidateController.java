package org.alas.backend.controllers;

import org.alas.backend.dataobjects.exam.ExamSummary;
import org.alas.backend.services.ExamService;
import org.alas.backend.services.ModuleService;
import org.alas.backend.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    ExamService examService;

    @Autowired
    SubmissionService submissionService;

    /*
     * Return all modules associated with the batches the candidate is present in.
     * */
    @GetMapping("/modules")
    public ResponseEntity<?> getAllModulesForCandidate() {
        try {
            String candidateId = "testUser";
            List<String> modules = moduleService.getAllModulesByCandidateId(candidateId);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<?> getModuleData(@PathVariable String moduleId) {
        try {
            String candidateId = "testUser";
            return new ResponseEntity<>(moduleService.getModuleForCandidate(moduleId, candidateId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    /*
     *
     * Return Exam Data for Candidate
     * */

    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getExam(@PathVariable String examId) {
        try {
            return new ResponseEntity<>(examService.getExamWithoutAnswers(examId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /*
     *
     * @return
     *   ExamSummary
     * */
    @GetMapping("/exam/{examId}/summary")
    public ResponseEntity<ExamSummary> getExamSummary(@PathVariable String examId) {
        try {
            return new ResponseEntity<>(examService.getExamSummary(examId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exam/{examId}/submission")
    public ResponseEntity<?> addVisit(@PathVariable String examId, @RequestBody String visit, @RequestParam String questionType) {
        try {
            String candidateId = "testUser";
            return submissionService.newVisit(questionType, visit, candidateId, examId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
