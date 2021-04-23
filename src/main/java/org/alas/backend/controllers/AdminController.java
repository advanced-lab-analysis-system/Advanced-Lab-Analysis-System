package org.alas.backend.controllers;


import org.alas.backend.services.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final BatchService batchService;

    public AdminController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/batches")
    public ResponseEntity<?> getAllBatches() {
        try {
            return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
