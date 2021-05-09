package org.alas.backend.controllers;


import org.alas.backend.documents.Batch;
import org.alas.backend.documents.Module;
import org.alas.backend.services.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final BatchService batchService;

    public AdminController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/batches")
    public ResponseEntity<List<Batch>> getAllBatches() {
        try {
            return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/batches")
    public ResponseEntity<List<Module>> createNewBatch(@RequestBody Batch batch) {
        try {
            return new ResponseEntity<>(batchService.createBatch(batch), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/batch/{batchId}")
    public ResponseEntity<String> updateBatch(@RequestBody Batch batch, @PathVariable String batchId) {
        try {
            batchService.updateBatch(batchId, batch);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/batch/{batchId}")
    public ResponseEntity<String> deleteBatch(@PathVariable String batchId) {
        try {
            batchService.deleteBatch(batchId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<Batch> getBatchById(@PathVariable String batchId) {
        try {
            return new ResponseEntity<>(batchService.getBatchById(batchId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
