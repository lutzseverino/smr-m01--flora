package me.jasperedits.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import me.jasperedits.FloraBot;
import me.jasperedits.docs.Dictionary;
import me.jasperedits.managers.document.YAMLManager;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public class Language {
    private final File languageResource = FloraBot.instance.getResourceFolder("languages");
    @Getter
    private String languageCode;

    // Jackson 😢
    public Language() {
    }

    public Language(String languageCode) {
        this.languageCode = languageCode;
    }

    @SuppressWarnings("ConstantConditions")
    public List<String> listLanguages() {
        List<String> list = new ArrayList<>();

        for (File file : languageResource.listFiles())
            list.add(file.getName().replace(".yaml", ""));

        return list;
    }

    @SuppressWarnings("ConstantConditions")
    public boolean checkAvailability(String languageCode) {
        List<String> fileString = Arrays.stream(languageResource.listFiles()).map(File::getName).collect(Collectors.toList());
        return fileString.contains(languageCode + ".yaml");
    }

    @SneakyThrows
    public String getValue(String key) {
        Dictionary dictionary = new YAMLManager("languages" + File.separator + languageCode + ".yaml").reader(Dictionary.class);
        return dictionary.getValues().get(key);
    }

}
