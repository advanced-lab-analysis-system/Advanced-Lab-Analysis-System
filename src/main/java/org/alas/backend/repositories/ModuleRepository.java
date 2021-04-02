package org.alas.backend.repositories;


import org.alas.backend.documents.Module;
import org.alas.backend.documents.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ModuleRepository extends ReactiveMongoRepository<Module, String> {
    Mono<Module> findByModuleId(String moduleId);
}
