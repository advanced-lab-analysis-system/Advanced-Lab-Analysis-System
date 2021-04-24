package org.alas.backend.services;

import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Module;
import org.alas.backend.repositories.ExamRepository;
import org.alas.backend.repositories.ModuleRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    private final ModuleRepository moduleRepository;
    private final ExamRepository examRepository;

    public ExamService(ModuleRepository moduleRepository, ExamRepository examRepository) {
        this.moduleRepository = moduleRepository;
        this.examRepository = examRepository;
    }

    public void createNewExamInModule(String moduleId, KeycloakPrincipal<KeycloakSecurityContext> principal, Exam exam) {
        try {
            String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
            exam.setAuthorId(authorId);
            Exam newExam = examRepository.save(exam);
            if (moduleRepository.findById(moduleId).isPresent()) {
                Module module = moduleRepository.findById(moduleId).get();
                module.addNewExam(newExam.getId());
                moduleRepository.save(module);
            } else {
                throw new Exception("No Module found");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public Exam getExamByExamId(String examId) {
        try {
            if (examRepository.findById(examId).isPresent())
                return examRepository.findById(examId).get();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return null;
    }

    public void updateExamByExamId(String examId, Exam exam) {
        try {
            examRepository.save(exam);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
