package org.alas.backend.controllers;

import org.alas.backend.dataobjects.exam.ExamSummary;
import org.alas.backend.services.ExamService;
import org.alas.backend.services.ModuleService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class CandidateController {

    private final ModuleService moduleService;
    private final ExamService examService;

    public CandidateController(ModuleService moduleService, ExamService examService) {
        this.moduleService = moduleService;
        this.examService = examService;
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
}
