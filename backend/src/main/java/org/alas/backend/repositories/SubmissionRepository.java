package org.alas.backend.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    Mono<Submission> findByExamIdAndCandidateId(String examId, String candidateId);
    Flux<Submission> findAllByCandidateId(String candidateId);
    Flux<Submission> findAllByExamId(String examId);
    Mono<?> deleteAllByExamId(String examId);
}

interface CustomizedSubmissionRepository {
    Mono<?> updateByExamIdAndCandidateId(String examId, String candidateId, String status);
    Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, VisitDTO visit);
    Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, CodeSubmission codeSubmission, GetSubmissionResponse getSubmissionResponse);
}

class CustomizedSubmissionRepositoryImpl implements CustomizedSubmissionRepository {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;


    @Override
    public Mono<?> updateByExamIdAndCandidateId(String examId, String candidateId, String status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.set("sessionStatus", status);
        return reactiveMongoTemplate.upsert(query, update, Submission.class);
    }

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

    public Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, CodeSubmission codeSubmission, GetSubmissionResponse getSubmissionResponse){
        System.out.println("In update method : qid " + questionId);
        System.out.println(getSubmissionResponse);
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.push("allSubmissions." + questionId + ".allCodeSubmissions", getSubmissionResponse);
        if(codeSubmission.getBestSubmissionScore()<=getSubmissionResponse.getScore()){
            update.set("allSubmissions." + questionId + ".bestSubmission",codeSubmission.getTotalNoOfSubmissions());
            update.set("allSubmissions." + questionId + ".bestSubmissionScore",getSubmissionResponse.getScore());
            System.out.println("Updated bestSubmissionScore");
        }
        update.inc("allSubmissions." + questionId + ".totalNoOfSubmissions");
        return reactiveMongoTemplate.upsert(query, update, Submission.class);
    }
}