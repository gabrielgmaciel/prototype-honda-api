package com.prototype.honda.api.migration.dealership;

import com.prototype.honda.api.business.model.DealershipModel;
import com.prototype.honda.api.business.repository.DealershipRepository;
import com.prototype.honda.api.migration.utils.JsonLoader;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "DealershipMigration", order = "001", author = "prototype-honda-api")
public class DealershipMigration {

    private static final String PATH_MIGRATION = "classpath:migration/dealerships/dealerships.json";

    @Execution
    public void migrate(DealershipRepository repository, JsonLoader jsonLoader) {
        if (repository.findAll().isEmpty()) {
            repository.saveAll(jsonLoader.loadJsonAsObject(PATH_MIGRATION, DealershipModel.class));
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate template) {
        template.dropCollection("dealerships");
    }
}
