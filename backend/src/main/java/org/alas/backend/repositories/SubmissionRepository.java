package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubmissionRepository extends ReactiveMongoRepository<Submission,String>, CustomizedSubmissionRepository {

    Mono<Submission> findByExamIdAndCandidateId(String examId, String candidateId);

}
