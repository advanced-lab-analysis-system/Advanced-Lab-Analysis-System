package org.alas.backend.controllers;

import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Module;
import org.alas.backend.services.ExamService;
import org.alas.backend.services.ModuleService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final ModuleService moduleService;
    private final ExamService examService;

    public AuthorController(ModuleService moduleService, ExamService examService) {
        this.moduleService = moduleService;
        this.examService = examService;
    }

    /*
     *
     * Returns all modules related to the given Author
     *  input:
     *   authorId - the id of the author making the request
     *  output:
     *   list of modules associated with the author
     *
     * */
    @GetMapping("/modules")
    public ResponseEntity<List<Module>> getAllModules(KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
            return new ResponseEntity<>(moduleService.getAllModulesByAuthorId(authorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * Creates a new module with the given details
     *  input:
     *   authorId - the id of the author creating the module
     *   moduleName - name of the module
     *   moduleDescription - description of the module
     *   batches - list of batches associated with the module
     *   authors - list of authors associated with the module
     *  output:
     *   if (module created) - 200 OK
     *   else - error
     *
     * */
    @PostMapping("/modules")
    public ResponseEntity<?> createNewModule(KeycloakPrincipal<KeycloakSecurityContext> principal, @RequestBody Module module) {
        try {
            String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
            module.setOriginalAuthor(authorId);
            moduleService.createModule(module);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * @return
     *  Module Object
     * */
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<Module> getModuleData(@PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        TODO: write logic here
        String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
        try {
            Module module = moduleService.getModuleForAuthor(moduleId, authorId);
            return new ResponseEntity<>(module, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * Updates given module's data
     *  input:
     *   moduleId: Id of the required Module
     *   moduleData: Data object containing changed information(only changed information)
     *  output:
     *   if(updated with no error): 200 OK
     *   else: error
     *
     * */
    @PutMapping("/module/{moduleId}")
    public ResponseEntity<?> updateModuleData(@PathVariable String moduleId, @RequestBody Module module, KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        TODO: write logic here
        try {
            moduleService.updateModuleById(moduleId, module);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * delete the given module
     *  input:
     *   moduleId: id of the required module
     *   authorId: id of the author making the request
     *  output:
     *   if(author has right and module is deleted without problem): 200 OK
     *   else: error
     *
     * */
    @DeleteMapping("/module/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        TODO: Write logic here
        try {
            moduleService.deleteModuleById(moduleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/module/{moduleId}/exams")
    public ResponseEntity<?> getAllAuthorExams(@PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            return new ResponseEntity<>(moduleService.getAllExamsByModuleId(moduleId), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * Create a new Exam
     * */
    @PostMapping("/module/{moduleId}/exams")
    public ResponseEntity<String> createExam(@RequestBody Exam exam, @PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
            examService.createNewExamInModule(moduleId, principal, exam);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * @return
     *  Exam Object by given examId
     * */
    @GetMapping("/exams/{examId}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String examId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
        try {
            return new ResponseEntity<>(examService.getExamByExamId(examId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * TODO: Update exam using given examId
     *  input:
     *   examId: id of the required exam
     *   examData: changed exam data
     *  output:
     *  @return
     *   if(updated properly): 200 OK
     *   else: error
     * */
    @PutMapping("/exam/{examId}")
    public ResponseEntity<?> updateExamByExamId(@PathVariable String examId, @RequestBody Exam exam, KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        TODO: Write logic here
        try {
            examService.updateExamByExamId(examId, exam);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * Delete exam using given examId
     *  input:
     *   examId: id of the required exam
     *   moduleId: id of the module associated for the exam
     *  output:
     *   if(deleted properly): 200 OK
     *   else: error
     * */
    @DeleteMapping("module/{moduleId}/exam/{examId}")
    public ResponseEntity<?> deleteExamByExamId(@PathVariable String examId, @PathVariable String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal) {
//        TODO: Write logic here
        try {
            examService.deleteExamByExamId(examId, moduleId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *
     * TODO: Send a signal in all connected WebSockets to stop the exam(in theory)
     * */
    @GetMapping("/exams/{examId}/end")
    public ResponseEntity<String> endExam(@PathVariable String examId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
