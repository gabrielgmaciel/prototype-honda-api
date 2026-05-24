package com.prototype.honda.api.converter;

public record SimpleCarsResponse(
        String id,
        String name,
        String description,
        String cover
) {
}
