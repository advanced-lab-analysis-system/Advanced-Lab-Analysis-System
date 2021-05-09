package org.alas.backend.services;

import org.alas.backend.documents.Batch;
import org.alas.backend.documents.Module;
import org.alas.backend.repositories.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final ModuleService moduleService;

    public BatchService(BatchRepository batchRepository, ModuleService moduleService) {
        this.batchRepository = batchRepository;
        this.moduleService = moduleService;
    }

    public List<Module> createBatch(Batch batch) {
        List<Module> moduleList = new ArrayList<>();
        try {
            Batch newBatch = batchRepository.save(batch);
            System.out.println(newBatch.toString());
            moduleList = moduleService.updateModulesWithNewBatch(newBatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleList;
    }

    public List<Batch> getAllBatches() {
        try {
            return batchRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void deleteBatch(String batchId) {
        try {
            batchRepository.deleteById(batchId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Batch getBatchById(String batchId) {
        try {
            if (batchRepository.findById(batchId).isPresent())
                return batchRepository.findById(batchId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
