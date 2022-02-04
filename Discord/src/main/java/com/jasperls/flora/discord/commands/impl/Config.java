package com.jasperls.flora.discord.commands.impl;

import com.jasperls.rimor.jda.type.JDACommand;

import javax.inject.Inject;

public class Config extends JDACommand {

    @Inject
    public Config(Language language) {
        super();
        addOptionSubcommand(language);
    }
}
