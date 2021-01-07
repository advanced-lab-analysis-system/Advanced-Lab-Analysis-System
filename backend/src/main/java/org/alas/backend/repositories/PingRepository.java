package org.alas.backend.repositories;

import org.alas.backend.documents.Ping;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PingRepository extends ReactiveMongoRepository<Ping,String> {
}
