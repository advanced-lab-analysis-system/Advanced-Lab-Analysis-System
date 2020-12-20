package org.alas.backend.repositories;

import org.alas.backend.documents.ExamData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExamRepository extends ReactiveCrudRepository<ExamData,String> {

    @Query("{'exam_id':?0}")
    Mono<ExamData> findByExam_id(String exam_id);
}
