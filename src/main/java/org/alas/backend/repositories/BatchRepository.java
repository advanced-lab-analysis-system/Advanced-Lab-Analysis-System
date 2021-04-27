package org.alas.backend.repositories;

import org.alas.backend.documents.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface BatchRepository extends MongoRepository<Batch, String> {

    //    TODO: Review the query to fetch all Batches which have the given candidateId in their candidateList
    @Query("{ 'candidateList': ?0 }")
    List<Batch> findAllByCandidateId(String CandidateId);
}
