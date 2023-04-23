package org.alas.backend.controllers;

import org.alas.backend.dataobjects.exam.ExamSummary;
import org.alas.backend.documents.Batch;
import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Module;
import org.alas.backend.services.BatchService;
import org.alas.backend.services.ExamService;
import org.alas.backend.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    ExamService examService;

    @Autowired
    BatchService batchService;

    /*
     *
     * Get all batches to select during creation
     * */

    @GetMapping("/batches")
    public ResponseEntity<List<Batch>> getAllBatches() {
        try {
            return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<List<Module>> getAllModules() {
        try {
            String authorId = "testUser";
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
    public ResponseEntity<List<Batch>> createNewModule(@RequestBody Module module) {
        try {
            String authorId = "testUser";
            module.setOriginalAuthor(authorId);
            return new ResponseEntity<>(moduleService.createModule(module), HttpStatus.OK);
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
    public ResponseEntity<Module> getModuleData(@PathVariable String moduleId) {
//        TODO: write logic here
        String authorId = "testUser";
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
    public ResponseEntity<?> updateModuleData(@PathVariable String moduleId, @RequestBody Module module) {
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
    public ResponseEntity<?> deleteModule(@PathVariable String moduleId) {
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
    public ResponseEntity<?> getAllAuthorExams(@PathVariable String moduleId) {
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
    public ResponseEntity<String> createExam(@RequestBody Exam exam, @PathVariable String moduleId) {
        try {
            examService.createNewExamInModule(moduleId, exam);
            return new ResponseEntity<>(HttpStatus.CREATED);
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

    /*
     *
     * @return
     *  Exam Object by given examId
     * */
    @GetMapping("/exam/{examId}")
    public ResponseEntity<?> getExamWithAnswersById(@PathVariable String examId) {
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
    public ResponseEntity<?> updateExamByExamId(@PathVariable String examId, @RequestBody Exam exam) {
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
    public ResponseEntity<?> deleteExamByExamId(@PathVariable String examId, @PathVariable String moduleId) {
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
