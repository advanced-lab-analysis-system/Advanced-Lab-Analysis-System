package org.alas.backend.services;

import org.alas.backend.documents.Batch;
import org.alas.backend.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public boolean createBatch(Batch batch) {
        try {
            batchRepository.save(batch);
            return true;
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return false;
    }

    public List<Batch> getAllBatches() {
        try {
            return batchRepository.findAll();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
