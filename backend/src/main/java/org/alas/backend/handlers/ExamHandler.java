package org.alas.backend.handlers;


import org.alas.backend.dto.ExamDTO;
import org.alas.backend.documents.Exam;
import org.alas.backend.dto.ExamDataDTO;
import org.alas.backend.dto.QuestionDTO;
import org.alas.backend.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class ExamHandler {

    @Autowired
    private ExamRepository examRepository;

    public Mono<Exam> createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Flux<ExamDTO> getAllExams() {
        return examRepository.findAll().map(exam ->
                new ExamDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamDate(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus()
                ));
    }

    public Mono<Exam> getExamWithAnswersById(String exam_id) {
        return examRepository.findByExamId(exam_id);
    }

    public Mono<ExamDataDTO> getExamWithoutAnswersById(String exam_id) {
        return examRepository.findByExamId(exam_id).map(exam ->
                new ExamDataDTO(exam.getExamId(),
                        exam.getBatchId(),
                        exam.getExamName(),
                        exam.getSubject(),
                        exam.getNoOfQuestions(),
                        exam.getExamDate(),
                        exam.getExamStartTime(),
                        exam.getExamEndTime(),
                        exam.getAuthor(),
                        exam.getStatus(),
                        exam.getQuestions().stream().map(questionData ->
                                new QuestionDTO(questionData.getQuestionId(), questionData.getOptions())).collect(Collectors.toList())));
    }
}
