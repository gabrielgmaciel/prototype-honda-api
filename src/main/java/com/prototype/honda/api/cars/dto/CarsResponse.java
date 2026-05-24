package com.prototype.honda.api.cars.dto;

import com.prototype.honda.api.cars.model.Itens;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Collection;

@Builder
public record CarsResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String category,
        Collection<Itens> itens,
        String cover,
        String banner,
        Collection<String> images
) {
}
