package org.alas.backend.services;

import org.alas.backend.documents.Module;
import org.alas.backend.repositories.ModuleRepository;
import org.springframework.stereotype.Service;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void createModule(Module module) {
        try {
            moduleRepository.save(module);
        } catch (Exception e) {
            System.err.println(e.toString());
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
            System.err.println(e.toString());
        }
        return null;
    }
}
