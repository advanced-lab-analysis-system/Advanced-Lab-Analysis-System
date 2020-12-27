package org.alas.backend.repositories;

import org.alas.backend.documents.Exam;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ExamRepository extends ReactiveMongoRepository<Exam,String> {

    @Query("{'exam_id':?0}")
    Mono<Exam> findByExam_id(String exam_id);
}
