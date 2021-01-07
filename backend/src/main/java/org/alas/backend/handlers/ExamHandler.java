package org.alas.backend.handlers;


import org.alas.backend.dto.ExamDTO;
import org.alas.backend.documents.Exam;
import org.alas.backend.dto.ExamDataDTO;
import org.alas.backend.dto.QuestionsDTO;
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
                new ExamDTO(exam.getExam_id(),
                        exam.getBatch_id(),
                        exam.getExam_name(),
                        exam.getExam_type(),
                        exam.getSubject(),
                        exam.getNo_of_questions(),
                        exam.getExam_date(),
                        exam.getExam_start_time(),
                        exam.getExam_end_time(),
                        exam.getAuthor(),
                        exam.getClass_and_section(),
                        exam.isExam_completed()
                ));
    }

    public Mono<Exam> getExamWithAnswersById(String exam_id) {
        return examRepository.findByExam_id(exam_id);
    }

    public Mono<ExamDataDTO> getExamWithoutAnswersById(String exam_id) {
        return examRepository.findByExam_id(exam_id).map(exam ->
                new ExamDataDTO(exam.getExam_id(),
                        exam.getBatch_id(),
                        exam.getExam_name(),
                        exam.getExam_type(),
                        exam.getSubject(),
                        exam.getNo_of_questions(),
                        exam.getExam_date(),
                        exam.getExam_start_time(),
                        exam.getExam_end_time(),
                        exam.getAuthor(),
                        exam.getClass_and_section(),
                        exam.isExam_completed(),
                        exam.getQuestions().stream().map(questionData ->
                                new QuestionsDTO(questionData.getQid(), questionData.getOptions())).collect(Collectors.toList())));
    }
}
