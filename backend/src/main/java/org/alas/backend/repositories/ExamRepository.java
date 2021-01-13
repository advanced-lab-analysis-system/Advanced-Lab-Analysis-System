package org.alas.backend.repositories;

import org.alas.backend.documents.Exam;
import org.alas.backend.documents.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExamRepository extends ReactiveMongoRepository<Exam, String>, CustomizedExamRepository {

    Mono<Exam> findByExamId(String examId);
}

interface CustomizedExamRepository {
    Mono<?> addSubmissionsByExamId(String examId, Submission submission);
}

class CustomizedExamRepositoryImpl implements CustomizedExamRepository {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<?> addSubmissionsByExamId(String examId, Submission submission) {
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId));
        Update update = new Update();

        update.set("submissions." + submission.getCandidateId(), submission.getAllSubmissions());
        System.out.println(submission);
        return reactiveMongoTemplate.upsert(query, update, "Exam");
    }
}