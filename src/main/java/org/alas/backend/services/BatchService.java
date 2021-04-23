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

    public void createBatch(Batch batch) {
        try {
            batchRepository.save(batch);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public List<Batch> getAllBatches() {
        try {
            return batchRepository.findAll();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }

    /*
     *
     * TODO: change logic to update based on changed values instead of whole document being upserted
     * */
    public void updateBatch(String batchId, Batch batch) {
        try {
            batchRepository.save(batch);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void deleteBatch(String batchId) {
        try {
            batchRepository.deleteById(batchId);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public Batch getBatchById(String batchId) {
        try {
            if (batchRepository.findById(batchId).isPresent())
                return batchRepository.findById(batchId).get();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
