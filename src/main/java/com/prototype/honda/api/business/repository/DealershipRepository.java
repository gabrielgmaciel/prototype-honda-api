package com.prototype.honda.api.business.repository;

import com.prototype.honda.api.business.model.DealershipModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DealershipRepository extends MongoRepository<DealershipModel, String> {

    @Query("{ 'status': 'ativo' }")
    Collection<DealershipModel> findAllByStatusActive();
}
