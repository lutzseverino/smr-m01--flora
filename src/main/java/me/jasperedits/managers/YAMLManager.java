package me.jasperedits.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;


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
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    public <T> T buildObject(Class<T> clazz) throws IOException {
        return this.objectMapper.readValue(this.getFile(), clazz);
    }
}
