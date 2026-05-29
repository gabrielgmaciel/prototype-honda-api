package com.prototype.honda.api.migration.vehicles.civic.typer;

import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.cars.repository.CarsRepository;
import com.prototype.honda.api.migration.utils.JsonLoader;
import com.prototype.honda.api.utils.FileMultipart;
import com.prototype.honda.api.utils.Utils;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Optional;

@ChangeUnit(id = "CivicTypeRMigration", order = "002", author = "prototype-honda-api")
public class CivicTypeRMigration {

    private static final String PATH_MIGRATION = "migration/vehicles/civic-type-r/";

    @Execution
    public void migrate(CarsRepository repository, JsonLoader jsonLoader) {
        Optional<CarsModel> data = jsonLoader
                .loadJsonAsObject("classpath:" + PATH_MIGRATION + "civic-type-r.json", CarsModel.class).stream()
                .findFirst();

        repository.saveAll(data.stream()
                .peek(car -> {
                    car.setCover(Utils.saveFile(getFile("capa.png")));
                    car.setBanner(Utils.saveFile(getFile("banner.webp")));
                    car.setImages(Arrays.asList(
                            Utils.saveFile(getFile("1.jpg")),
                            Utils.saveFile(getFile("2.jpg")),
                            Utils.saveFile(getFile("3.webp")),
                            Utils.saveFile(getFile("4.jpg")),
                            Utils.saveFile(getFile("5.webp")),
                            Utils.saveFile(getFile("6.jpg")),
                            Utils.saveFile(getFile("7.jpg")),
                            Utils.saveFile(getFile("8.jpg")),
                            Utils.saveFile(getFile("9.jpg"))
                    ));
                }).toList());
    }

    private MultipartFile getFile(String fileName) {
        try {
            return new FileMultipart(new ClassPathResource(PATH_MIGRATION + fileName)
                    .getFile()
                    .toPath());
        } catch (Exception exception) {
            throw new RuntimeException("Erro ao carregar arquivo: " + fileName, exception);
        }
    }

    @RollbackExecution
    public void rollback(MongoTemplate template) {
        template.dropCollection("users");
    }
}
