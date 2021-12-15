package com.jasperls.flora.discord;

public class Main {
    public static void main(String[] args) {
        String configPath = args[0];

        Flora flora = new Flora();

        try {
            flora.init(configPath);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO log init failure
        }
    }
}
