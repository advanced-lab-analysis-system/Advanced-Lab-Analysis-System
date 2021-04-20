package org.alas.backend.repositories;

import org.alas.backend.documents.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BatchRepository extends MongoRepository<Batch, String> {
}
