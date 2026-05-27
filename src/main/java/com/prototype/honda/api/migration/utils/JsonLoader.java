package com.prototype.honda.api.migration.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonLoader {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public JsonLoader(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public JsonLoader() {
        this(new ObjectMapper(), new DefaultResourceLoader());
    }

    public <T> List<T> loadJsonAsObject(String resourcePath, Class<T> valueType) {
        try {
            var resource = resourceLoader.getResource(resourcePath);
            return objectMapper.readerForListOf(valueType).readValue(resource.getInputStream());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load JSON resource: " + resourcePath, e);
        }
    }
}
