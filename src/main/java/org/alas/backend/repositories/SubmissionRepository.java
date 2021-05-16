package org.alas.backend.repositories;

import com.mongodb.client.result.UpdateResult;
import org.alas.backend.dataobjects.exam.submission.mcq.MCQVisit;
import org.alas.backend.dataobjects.exam.submission.mcq.MCQVisitDetails;
import org.alas.backend.documents.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> , CustomizedSubmissionRepository {

    Submission findByExamIdAndCandidateId(String examId, String candidateId);

    Submission findAllByCandidateId(String candidateId);

    Submission findAllByExamId(String examId);

    void deleteAllByExamId(String examId);
}

interface CustomizedSubmissionRepository {
    UpdateResult updateByExamIdAndCandidateId(String examId, String candidateId, String status);

    UpdateResult updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, MCQVisit visit);

//    UpdateResult updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, CodeSubmission codeSubmission, GetSubmissionResponse getSubmissionResponse);
}

class CustomizedSubmissionRepositoryImpl implements CustomizedSubmissionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public UpdateResult updateByExamIdAndCandidateId(String examId, String candidateId, String status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.set("sessionStatus", status);
        return mongoTemplate.upsert(query, update, Submission.class);
    }

    public UpdateResult updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, MCQVisit visit) {
        String key = visit.getQuestionId();
        Query query = new Query();
        query.addCriteria(Criteria.where("examId").is(examId))
                .addCriteria(Criteria.where("candidateId").is(candidateId));
        Update update = new Update();
        update.push("allSubmissions." + key + ".visits", new MCQVisitDetails(visit.getVisitStartTime(), visit.getVisitEndTime(), visit.getSelectedAnswer()));
        update.set("allSubmissions." + key + ".finalAnswer", visit.getSelectedAnswer());
        update.inc("allSubmissions." + key + ".totalVisits");
        return mongoTemplate.upsert(query, update, Submission.class);
    }

//    public UpdateResult updateByExamIdAndCandidateIdAndQuestionId(String examId, String candidateId, String questionId, CodeSubmission codeSubmission, GetSubmissionResponse getSubmissionResponse) {
//        System.out.println("In update method : qid " + questionId);
//        System.out.println(getSubmissionResponse);
//        Query query = new Query();
//        query.addCriteria(Criteria.where("examId").is(examId))
//                .addCriteria(Criteria.where("candidateId").is(candidateId));
//        Update update = new Update();
//        update.push("allSubmissions." + questionId + ".allCodeSubmissions", getSubmissionResponse);
//        if (codeSubmission.getBestSubmissionScore() <= getSubmissionResponse.getScore()) {
//            update.set("allSubmissions." + questionId + ".bestSubmission", codeSubmission.getTotalNoOfSubmissions());
//            update.set("allSubmissions." + questionId + ".bestSubmissionScore", getSubmissionResponse.getScore());
//            System.out.println("Updated bestSubmissionScore");
//        }
//        update.inc("allSubmissions." + questionId + ".totalNoOfSubmissions");
//        return mongoTemplate.upsert(query, update, Submission.class);
//    }
}
