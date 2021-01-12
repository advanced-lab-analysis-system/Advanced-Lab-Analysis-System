package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubmissionRepository extends ReactiveMongoRepository<Submission,String>, CustomSubmissionRepository {

    @Query("{'exam_id':?0,'candidate_id':?1}")
    Mono<Submission> findSubmissionByExam_idAndCandidate_id(String exam_id,String candidate_id);

    @Query("{'exam_id':?0,'candidate_id':?1}")
    boolean existsByExam_idAndCandidate_id(String exam_id,String candidate_id);

}
