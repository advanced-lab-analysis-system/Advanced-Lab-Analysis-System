package org.alas.backend.services;

import org.alas.backend.dataobjects.exam.ExamSummary;
import org.alas.backend.dataobjects.exam.question.Question;
import org.alas.backend.dataobjects.exam.question.coding.CodingQuestion;
import org.alas.backend.dataobjects.exam.question.mcq.MCQQuestion;
import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Module;
import org.alas.backend.repositories.ExamRepository;
import org.alas.backend.repositories.ModuleRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            if (moduleRepository.findById(moduleId).isPresent()) {
                String authorId = principal.getKeycloakSecurityContext().getToken().getSubject();
                exam.setAuthorId(authorId);
                Exam newExam = examRepository.save(exam);
                Module module = moduleRepository.findById(moduleId).get();
                module.addNewExam(newExam.getId());
                moduleRepository.save(module);
            } else {
                throw new Exception("No Module found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Exam getExamByExamId(String examId) {
        try {
            if (examRepository.findById(examId).isPresent())
                return examRepository.findById(examId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Exam getExamWithoutAnswers(String examId) {
        try {
            if (examRepository.findById(examId).isPresent()) {
                Exam exam = examRepository.findById(examId).get();
                List<Object> questionList = exam.getQuestionList();
                List<Question> newQuestionList = new ArrayList<>();
                questionList.forEach(question -> {
                    Question currQuestion = (Question) question;
                    switch (currQuestion.getQuestionType()) {
                        case "mcq":
                            newQuestionList.add((MCQQuestion) question);
                            break;
                        case "coding":
                            newQuestionList.add((CodingQuestion) question);
                            break;
                        default:
                            break;
                    }
                });
                exam.setQuestionList(Collections.singletonList(newQuestionList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ExamSummary getExamSummary(String examId) {
        try {
            if (examRepository.findById(examId).isPresent()) {
                Exam exam = examRepository.findById(examId).get();
                return new ExamSummary(exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateExamByExamId(String examId, Exam exam) {
        try {
            examRepository.save(exam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteExamByExamId(String examId, String moduleId) {
        try {
            if (moduleRepository.findById(moduleId).isPresent() && examRepository.findById(examId).isPresent()) {
                Module module = moduleRepository.findById(moduleId).get();
                module.deleteExam(examId);
                examRepository.deleteById(examId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
