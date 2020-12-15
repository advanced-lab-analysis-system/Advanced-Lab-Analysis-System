package org.alas.backend.repositories;

import org.alas.backend.documents.Exam;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcomingTestRepository extends ReactiveMongoRepository<Exam,String> {
}
