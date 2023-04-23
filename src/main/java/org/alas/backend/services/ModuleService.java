package org.alas.backend.services;

import org.alas.backend.documents.Batch;
import org.alas.backend.documents.Module;
import org.alas.backend.repositories.BatchRepository;
import org.alas.backend.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    BatchRepository batchRepository;

    public List<Batch> createModule(Module module) {
        try {
            List<Batch> updatedBatchList = new ArrayList<>();
            Module newModule = moduleRepository.save(module);
            System.out.println(newModule);
            List<String> batchList = newModule.getBatchList();
            for(String batchId: batchList){
                if(batchRepository.findById(batchId).isPresent()){
                    Batch batch = batchRepository.findById(batchId).get();
                    batch.addNewModule(newModule.getId());
                    System.out.println(batchRepository.save(batch));
                    updatedBatchList.add(batch);
                }
            }
            return updatedBatchList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Module getModuleForAuthor(String moduleId, String authorId) {
        try {
            Module module = moduleRepository.findById(moduleId).isPresent() ? moduleRepository.findById(moduleId).get() : null;
            if (module != null) {
                if (module.getOriginalAuthor().equals(authorId) || module.getAuthorList().containsKey(authorId)) {
                    return module;
                } else return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Module getModuleForCandidate(String moduleId, String candidateId) {
        try {
            if (moduleRepository.findById(moduleId).isPresent()) {
                return moduleRepository.findById(moduleId).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Module> getAllModulesByAuthorId(String authorId) {
        try {
            return moduleRepository.findAllByAuthorId(authorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllModulesByCandidateId(String candidateId) {
        try {
            Set<String> modules = new HashSet<>();
            List<Batch> batches = batchRepository.findAllByCandidateList(candidateId);
            batches.forEach(batch -> {
                modules.addAll(batch.getModuleList());
            });
            return new ArrayList<>(modules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateModuleById(String moduleId, Module module) {
        try {
            module.setId(moduleId);
            moduleRepository.save(module);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteModuleById(String moduleId) {
        try {
            moduleRepository.deleteById(moduleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllExamsByModuleId(String moduleId) {
        try {
            if (moduleRepository.findById(moduleId).isPresent()) {
                Module module = moduleRepository.findById(moduleId).get();
                return module.getExamList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Module> updateModulesWithNewBatch(Batch batch) {
        List<Module> moduleList = new ArrayList<>();
        try {
            for (String moduleId : batch.getModuleList()) {
                System.out.println("Module Name: " + moduleId);
                if(moduleRepository.findById(moduleId).isPresent()) {
                    Module module = moduleRepository.findById(moduleId).get();
                    List<String> batchList = module.getBatchList();
                    batchList.add(batch.getId());
                    module.setBatchList(batchList);
                    moduleRepository.save(module);
                    moduleList.add(module);
                    System.out.println(module);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(moduleList.toString());
        return moduleList;
    }
}
