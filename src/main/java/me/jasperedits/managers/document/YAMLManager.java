package me.jasperedits.managers.document;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.Getter;
import lombok.SneakyThrows;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Getter
public class YAMLManager {
    File file;
    ObjectMapper objectMapper;
    
    public YAMLManager(String filename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        this.file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
    }

    @SneakyThrows
    public <T> T reader(Class<T> clazz) {
        return this.objectMapper.readValue(this.getFile(), clazz);
    }

    @SneakyThrows
    public <T> void write(T object)  {
        this.objectMapper.writeValue(this.getFile(), object);
    }
}
