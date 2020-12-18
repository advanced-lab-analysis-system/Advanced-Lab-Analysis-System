package org.alas.backend.services;


import org.alas.backend.documents.Exam;
import org.alas.backend.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    public Mono<Exam> createExam(Exam exam) {
        return examRepository.save(exam);
    }
}
