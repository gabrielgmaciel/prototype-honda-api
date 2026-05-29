package com.prototype.honda.api.migration.admin;

import com.prototype.honda.api.migration.utils.JsonLoader;
import com.prototype.honda.api.user.model.User;
import com.prototype.honda.api.user.repository.UserRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "AdminMasterMigration", order = "001", author = "prototype-honda-api")
public class AdminMasterMigration {

    private static final String PATH_MIGRATION = "classpath:migration/users/admin.json";

    @Execution
    public void migrate(UserRepository repository, JsonLoader jsonLoader) {
        if (repository.findAll().isEmpty()) {
            repository.saveAll(jsonLoader.loadJsonAsObject(PATH_MIGRATION, User.class));
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate template) {
        template.dropCollection("users");
    }
}
