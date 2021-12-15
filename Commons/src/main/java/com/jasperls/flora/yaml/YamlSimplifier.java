package com.jasperls.flora.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class YamlSimplifier {
    String path;

    public YamlSimplifier(String path) {
        this.path = path;
    }

    public <T> T read(Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            // TODO log file not found
        }

        return yaml.load(inputStream);
    }

    public void write(Object object) {
        DumperOptions options = getDumperOptions();
        Yaml yaml = new Yaml(options);

        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            // TODO log unable to create file
        }

        yaml.dump(object, writer);
    }

    private DumperOptions getDumperOptions() {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        return options;
    }
}
