package org.alas.backend.handlers;

import org.alas.backend.documents.TestExam;
import org.alas.backend.repositories.TestExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestExamHandler {

    @Autowired
    TestExamRepository testExamRepository;

    public Mono<TestExam> createExam(TestExam testExam) {
        return testExamRepository.save(testExam);
    }

    public Flux<TestExam> getAllExams() {
        return testExamRepository.findAll();
    }

    public Mono<TestExam> getExamWithAnswersById(String exam_id) {
        return testExamRepository.findByTestExam_id(exam_id);
    }
}
