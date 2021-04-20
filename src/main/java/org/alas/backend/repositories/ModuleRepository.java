package org.alas.backend.repositories;

import org.alas.backend.documents.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModuleRepository extends MongoRepository<Module, String> {
}
