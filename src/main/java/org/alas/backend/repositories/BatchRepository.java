package org.alas.backend.repositories;

import org.alas.backend.documents.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {

// TODO: write query to only fetch id and moduleName for author to select while module creation
//    @Query("({}, {_id:1}).map(function(item){ return item._id; })")
//    List<String> findAllBatchesId();

    List<Batch> findAllByCandidateList(String CandidateId);
}
