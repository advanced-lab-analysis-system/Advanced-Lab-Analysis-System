package org.alas.backend.services;


import org.alas.backend.dto.Exam;
import org.alas.backend.documents.ExamData;
import org.alas.backend.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    public Mono<ExamData> createExam(ExamData examData) {
        return examRepository.save(examData);
    }

    public Flux<Exam> getAllExams(){
        return examRepository.findAll().map(examData ->
                new Exam(examData.getExam_id(),
                examData.getBatch_id(),
                examData.getExam_name(),
                examData.getExam_type(),
                examData.getSubject(),
                examData.getNo_of_questions(),
                examData.getExam_date(),
                examData.getExam_start_time(),
                examData.getExam_end_time(),
                examData.getAuthor(),
                examData.getClass_and_section(),
                examData.isExam_completed()
                ));
    }

    public Mono<ExamData> getExamById(String exam_id){
        return examRepository.findByExam_id(exam_id);
    }
}
