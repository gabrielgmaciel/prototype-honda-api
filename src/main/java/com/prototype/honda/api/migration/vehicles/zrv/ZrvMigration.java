package com.prototype.honda.api.migration.vehicles.zrv;

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

@ChangeUnit(id = "ZrvMigration", order = "002", author = "prototype-honda-api")
public class ZrvMigration {

    private static final String PATH_MIGRATION = "migration/vehicles/zrv/";

    @Execution
    public void migrate(CarsRepository repository, JsonLoader jsonLoader) {
        Optional<CarsModel> data = jsonLoader
                .loadJsonAsObject("classpath:" + PATH_MIGRATION + "zrv.json", CarsModel.class).stream()
                .findFirst();

        repository.saveAll(data.stream()
                .peek(car -> {
                    car.setCover(Utils.saveFile(getFile("capa.webp")));
                    car.setBanner(Utils.saveFile(getFile("banner.webp")));
                    car.setImages(Arrays.asList(
                            Utils.saveFile(getFile("1.webp")),
                            Utils.saveFile(getFile("2.webp")),
                            Utils.saveFile(getFile("3.png")),
                            Utils.saveFile(getFile("4.webp")),
                            Utils.saveFile(getFile("5.png"))
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
