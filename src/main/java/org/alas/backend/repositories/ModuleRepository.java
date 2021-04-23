package org.alas.backend.repositories;

import org.alas.backend.documents.Module;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends MongoRepository<Module, String> {

    @Query("{$or: [ { 'originalAuthor': { $eq: ?0 } }, { 'authorList.?1': { $exists: true } } ]}")
    List<Module> findAllByAuthorId(String authorId);
}

//interface CustomizedModuleRepository {
//    List<Module> findAllModulesByAuthorId(String authorId);
//}
//
//class CustomizedModuleRepositoryImpl implements CustomizedModuleRepository {
//    private final MongoTemplate mongoTemplate;
//
//    public CustomizedModuleRepositoryImpl(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//
//    @Override
//    public List<Module> findAllModulesByAuthorId(String authorId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("originalAuthor").is(authorId).and("authorList").);
//    }
//}
