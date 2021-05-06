package org.alas.backend.services;

import org.alas.backend.documents.Batch;
import org.alas.backend.documents.Module;
import org.alas.backend.repositories.BatchRepository;
import org.alas.backend.repositories.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final BatchRepository batchRepository;

    public ModuleService(ModuleRepository moduleRepository, BatchRepository batchRepository) {
        this.moduleRepository = moduleRepository;
        this.batchRepository = batchRepository;
    }

    public void createModule(Module module) {
        try {
            moduleRepository.save(module);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            List<Batch> batches = batchRepository.findAllByCandidateId(candidateId);
            batches.forEach(batch -> {
                modules.addAll(batch.getModuleList());
            });
            return new ArrayList<String>(modules);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateModuleById(String moduleId, Module module) {
        try {
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
}
