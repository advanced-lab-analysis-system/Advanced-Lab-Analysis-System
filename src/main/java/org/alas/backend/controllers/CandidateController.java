package org.alas.backend.controllers;

import org.alas.backend.dataobjects.exam.ExamSummary;
import org.alas.backend.services.ExamService;
import org.alas.backend.services.ModuleService;
import org.alas.backend.services.SubmissionService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    private final ModuleService moduleService;
    private final ExamService examService;
    private final SubmissionService submissionService;

    public CandidateController(ModuleService moduleService, ExamService examService, SubmissionService submissionService) {
        this.moduleService = moduleService;
        this.examService = examService;
        this.submissionService = submissionService;
    }

    /*
     * Return all modules associated with the batches the candidate is present in.
     * */
    @GetMapping("/modules")
    public ResponseEntity<?> getAllModulesForCandidate(KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            String candidateId = principal.getKeycloakSecurityContext().getToken().getSubject();
            List<String> modules = moduleService.getAllModulesByCandidateId(candidateId);
            return new ResponseEntity<>(modules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<?> getModuleData(@PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            String candidateId = principal.getKeycloakSecurityContext().getToken().getSubject();
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
    public ResponseEntity<?> getExam(@PathVariable String examId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
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
    public ResponseEntity<ExamSummary> getExamSummary(@PathVariable String examId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            return new ResponseEntity<>(examService.getExamSummary(examId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exam/{examId}/submission")
    public ResponseEntity<?> addVisit(@PathVariable String examId, KeycloakPrincipal<KeycloakSecurityContext> principal, @RequestBody String visit, @RequestParam String questionType) {
        try {
            String candidateId = principal.getKeycloakSecurityContext().getToken().getSubject();
            return submissionService.newVisit(questionType, visit, candidateId, examId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
