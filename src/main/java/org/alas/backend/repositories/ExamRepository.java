package org.alas.backend.repositories;

import org.alas.backend.documents.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamRepository extends MongoRepository<Exam, String> {
}
