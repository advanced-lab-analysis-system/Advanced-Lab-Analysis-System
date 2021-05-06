package org.alas.backend.repositories;

import org.alas.backend.documents.Submission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
}
