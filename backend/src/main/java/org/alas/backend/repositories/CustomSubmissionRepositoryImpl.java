package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.alas.backend.dto.QuestionMCQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;


public class CustomSubmissionRepositoryImpl implements CustomSubmissionRepository {

    ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public CustomSubmissionRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate){
        this.reactiveMongoTemplate=reactiveMongoTemplate;
    }


    @Override
    public Mono<?> findAndUpdateSubmissionByExam_idAndCandidate_id(String exam_id, String candidate_id, QuestionMCQ questionMCQ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("exam_id").is(exam_id))
                .addCriteria(Criteria.where("candidate_id").is(candidate_id))
                .addCriteria(Criteria.where("questionMCQList.question_id").is(questionMCQ.getQuestion_id()));
        Update update = new Update();
        update.push("questionMCQList.$.all_visits",questionMCQ.getAll_visits().get(0));
        update.set("questionMCQList.$.final_answer",questionMCQ.getFinal_answer());
        update.inc("questionMCQList.$.total_no_of_visits");

        return reactiveMongoTemplate.upsert(query,update, Submission.class);
    }
}
