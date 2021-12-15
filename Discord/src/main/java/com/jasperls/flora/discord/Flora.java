package com.jasperls.flora.discord;

import com.jasperls.flora.config.Values;
import com.jasperls.flora.yaml.YamlSimplifier;

public class Flora {

    public Flora() {
    }

    public void init(String config) {
        YamlSimplifier configYaml = new YamlSimplifier(config);
        Values values = configYaml.read(Values.class);
    }
}
