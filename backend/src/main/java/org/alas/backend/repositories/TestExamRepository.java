package org.alas.backend.repositories;

import org.alas.backend.documents.Exam;
import org.alas.backend.documents.TestExam;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TestExamRepository extends ReactiveMongoRepository<TestExam,String> {

    @Query("{'testExam_id':?0}")
    Mono<TestExam> findByTestExam_id(String exam_id);
}
