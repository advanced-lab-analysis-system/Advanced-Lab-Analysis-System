package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.VisitDTO;
import org.alas.backend.dto.VisitDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

public class CustomizedSubmissionRepositoryImpl implements CustomizedSubmissionRepository {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<?> updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, VisitDTO visit) {
        String key = visit.getQuestionId();
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.push("questions." + key + ".visits", new VisitDetails(visit.getVisitStartTime(), visit.getVisitEndTime(), visit.getSelectedAnswer()));
        update.set("questions." + key + ".finalAnswer", visit.getSelectedAnswer());
        update.inc("questions." + key + ".totalVisits");

        return reactiveMongoTemplate.upsert(query, update, Submission.class);
    }
}
