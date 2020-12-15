package org.alas.backend.repository;

import org.alas.backend.model.Exam;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcomingTestRepository extends ReactiveMongoRepository<Exam,String> {
}
