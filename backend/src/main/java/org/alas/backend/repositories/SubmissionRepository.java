package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SubmissionRepository extends ReactiveMongoRepository<Submission,String>, CustomizedSubmissionRepository {

    Flux<Submission> findAllByExamId(String examId);
    Mono<?> deleteAllByExamId(String examId);
}

interface CustomizedSubmissionRepository {
    Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, VisitDTO visit);
    Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, GetSubmissionResponse getSubmissionResponse);
}

class CustomizedSubmissionRepositoryImpl implements CustomizedSubmissionRepository {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    public Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, VisitDTO visit) {
        String key = visit.getQuestionId();
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.push("allSubmissions." + key + ".visits", new VisitDetails(visit.getVisitStartTime(), visit.getVisitEndTime(), visit.getSelectedAnswer()));
        update.set("allSubmissions." + key + ".finalAnswer", visit.getSelectedAnswer());
        update.inc("allSubmissions." + key + ".totalVisits");
        return reactiveMongoTemplate.upsert(query, update, Submission.class);
    }

    public Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, GetSubmissionResponse getSubmissionResponse){
        System.out.println("In update method" + questionId);
        System.out.println(getSubmissionResponse);
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.push("allSubmissions." + questionId + ".allCodeSubmissions", getSubmissionResponse);
        //update.push("allSubmissions." + questionId + ".visits", new VisitDetails(visit.getVisitStartTime(), visit.getVisitEndTime(), visit.getSelectedAnswer()));
        //update.set("allSubmissions." + questionId + ".finalAnswer", visit.getSelectedAnswer());
        update.inc("allSubmissions." + questionId + ".totalNoOfSubmissions");
        return reactiveMongoTemplate.upsert(query, update, Submission.class);
    }
}