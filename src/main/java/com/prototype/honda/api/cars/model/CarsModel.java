package com.prototype.honda.api.cars.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@Document(collection = "cars")
public class CarsModel {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String banner;
    private String category;
    private Collection<String> images;

}
