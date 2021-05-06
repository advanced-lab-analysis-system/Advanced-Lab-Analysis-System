package org.alas.backend.repositories;

import org.alas.backend.documents.Module;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends MongoRepository<Module, String> {

    @Query("{$or: [ { 'originalAuthor': { $eq: ?0 } }, { 'authorList.?0': { $exists: true } } ]}")
    List<Module> findAllByAuthorId(String authorId);
}
