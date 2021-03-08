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

import java.util.List;

@Repository
public interface ExamRepository extends ReactiveMongoRepository<Exam, String>, CustomizedExamRepository {

    Mono<Exam> findByExamId(String examId);
}

interface CustomizedExamRepository {
    void addSubmissionsByExamId(List<String> questionList, String examId, Submission submission);
    void updateExamStatus(String examId);
}

class CustomizedExamRepositoryImpl implements CustomizedExamRepository {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public void addSubmissionsByExamId(List<String> questionsAnsweredCorrectly, String examId, Submission submission) {
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId));
        Update update = new Update();
        update.set("submissions." + submission.getCandidateId(), submission.getAllSubmissions());
        reactiveMongoTemplate.upsert(query, update,"Exams")
                .subscribe(null,null, () -> {
                    Update update1 = new Update();
                    update1.set("submissions." + submission.getCandidateId() + ".questionsAnsweredCorrectly", questionsAnsweredCorrectly);
                    update1.set("submissions." + submission.getCandidateId() + ".totalScore", questionsAnsweredCorrectly.size());
                    reactiveMongoTemplate.upsert(query, update1, "Exams").subscribe();
                });
    }

    @Override
    public void updateExamStatus(String examId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId));
        Update update = new Update();
        update.set("status","ended");
        reactiveMongoTemplate.upsert(query,update,"Exams").subscribe();
    }
}