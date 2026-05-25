package com.prototype.honda.api.converter;

import com.prototype.honda.api.cars.dto.CarsResponse;
import com.prototype.honda.api.cars.model.CarsModel;
import com.prototype.honda.api.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static com.prototype.honda.api.utils.Utils.getFile;


@Component
public class CarsConverter {

    public CarsResponse convert(CarsModel carsModel) {
        return CarsResponse.builder()
                .id(carsModel.getId())
                .name(carsModel.getName())
                .description(carsModel.getDescription())
                .price(carsModel.getPrice())
                .category(carsModel.getCategory())
                .itens(carsModel.getItens())
                .cover(getFile(carsModel.getCover()))
                .banner(getFile(carsModel.getBanner()))
                .images(getImages(carsModel.getImages()))
                .build();
    }

    private Collection<String> getImages(Collection<String> images) {
        if (null == images || images.isEmpty()) {
            return Collections.emptyList();
        }
        return images.stream()
                .filter(Objects::nonNull)
                .map(Utils::getFile).toList();
    }

    public SimpleCarsResponse convertSimple(CarsModel carsModel) {
        return new SimpleCarsResponse(
                carsModel.getId(),
                carsModel.getName(),
                carsModel.getDescription(),
                getFile(carsModel.getCover())
        );
    }
}
