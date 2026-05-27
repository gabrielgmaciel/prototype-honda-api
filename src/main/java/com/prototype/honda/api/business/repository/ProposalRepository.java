package com.prototype.honda.api.business.repository;

import com.prototype.honda.api.business.model.ProposalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends MongoRepository<ProposalModel, String> {
}
