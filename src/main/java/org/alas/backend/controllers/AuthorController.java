package org.alas.backend.controllers;

import org.alas.backend.dataobjects.dto.ExamDTO;
import org.alas.backend.documents.Exam;
import org.alas.backend.handlers.ExamHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final ExamHandler examHandler;

    public AuthorController(ExamHandler examHandler) {
        this.examHandler = examHandler;
    }

    /*
     *
     * TODO: Extract All Modules related the author.
     *  input:
     *   authorId - the id of the author making the request
     *  output:
     *   list of modules associated with the author
     *
     * */
    @GetMapping("/modules")
    public ResponseEntity<?> getAllModules(@RequestParam String authorId) {
//        TODO: write logic here.
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /*
     *
     * TODO: Create a new module with the given details
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
    public ResponseEntity<?> createNewModule(@RequestParam String authorId) {
//        TODO : write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * TODO: Return all data associated with the given moduleId
     *  input:
     *   moduleId: ID of the required Module
     *  output:
     *   name: module name
     *   desc: module Description
     *   batches: list of batches associated with the module
     *   originalAuthor: id of original author
     *   authorList: List of authors with their permissions
     *   examsList: List of exams associated with the module
     * */
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<?> getModuleData(@PathVariable String moduleId) {
//        TODO: write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * TODO: Update given module's data
     *  input:
     *   moduleId: Id of the required Module
     *   moduleData: Data object containing changed information(only changed information)
     *  output:
     *   if(updated with no error): 200 OK
     *   else: error
     *
     * */
    @PutMapping("/module/{moduleId}")
    public ResponseEntity<?> updateModuleData(@PathVariable String moduleId) {
//        TODO: write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * TODO: delete the given module
     *  input:
     *   moduleId: id of the required module
     *   authorId: id of the author making the request
     *  output:
     *   if(author has right and module is deleted without problem): 200 OK
     *   else: error
     *
     * */
    @DeleteMapping("/module/{moduleId}")
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId) {
//        TODO: Write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exams")
    public ResponseEntity<Flux<ExamDTO>> getAllAuthorExams(@RequestParam String authorId) {
        Flux<ExamDTO> examFlux = examHandler.getAllAuthorExams(authorId);
        return new ResponseEntity<>(examFlux, HttpStatus.OK);
    }

    /*
     * Create a new Exam
     * */
    @PostMapping("/exams")
    public ResponseEntity<String> createExam(@RequestBody Exam exam) {
        examHandler.createExam(exam);
        return new ResponseEntity<>("Exam Created", HttpStatus.CREATED);
    }

    /*
     *
     * TODO: Review function and refactor
     * */
    @GetMapping("/exams/{examId}")
    public ResponseEntity<Mono<Exam>> getExamWithAnswersById(@PathVariable String examId) {
        Mono<Exam> examDataDTOMono = examHandler.getExamWithAnswers(examId);
        return new ResponseEntity<>(examDataDTOMono, HttpStatus.OK);
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
    public ResponseEntity<?> updateExamByExamId(@PathVariable String examId) {
//        TODO: Write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * TODO: Delete exam using given examId
     *  input:
     *   examId: id of the required exam
     *  output:
     *   if(deleted properly): 200 OK
     *   else: error
     * */
    @DeleteMapping("/exam/{examId}")
    public ResponseEntity<?> deleteExamByExamId(@PathVariable String examId) {
//        TODO: Write logic here
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * end the exam prematurely
     * */
    @GetMapping("/exams/{examId}/end")
    public ResponseEntity<String> endExam(@PathVariable String examId) {
        examHandler.endExam(examId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
