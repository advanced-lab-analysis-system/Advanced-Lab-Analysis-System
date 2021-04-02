package org.alas.backend.repositories;

import org.alas.backend.documents.Batch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BatchRepository extends ReactiveMongoRepository<Batch, String> {
    Mono<Batch> findBatchByBatchId(String batchId);

}
