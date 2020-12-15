package org.alas.backend.repository;

import org.alas.backend.model.Exam;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ExamsRepository extends ReactiveMongoRepository<Exam,String> {

}
