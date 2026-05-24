package com.prototype.honda.api.cars.repository;

import com.prototype.honda.api.cars.model.CarsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends MongoRepository<CarsModel, String> {

}
