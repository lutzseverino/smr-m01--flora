package com.jasperls.flora.language;

import lombok.Getter;

import java.io.File;
import java.util.*;

public class Dictionary {

    @Getter private final ResourceBundle bundle;

    public Dictionary(Locale language) {
        bundle = ResourceBundle.getBundle("language.bundle", language);
    }

    public Dictionary() {
        bundle = ResourceBundle.getBundle("language.bundle");
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

    public ArrayList<String> getAvailableLanguages() {
        File languageFolder = new File(Objects.requireNonNull(this.getClass().getResource("/language/")).getPath());
        ArrayList<String> languages = new ArrayList<>();

        Arrays.stream(Objects.requireNonNull(languageFolder.listFiles())).forEach(file -> {
            if (!file.getName().matches(".*[a-z]{2}_[A-Z]{2}.*")) {
                languages.add("en_US");
                return;
            }
            languages.add(file.getName().replaceAll("(bundle_)|(.properties)", ""));
        });

        return languages;
    }

    public boolean isLanguageAvailable(String languageCode) {
        return getAvailableLanguages().contains(languageCode);
    }
}
