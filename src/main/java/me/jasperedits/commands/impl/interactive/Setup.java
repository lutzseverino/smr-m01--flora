package me.jasperedits.commands.impl.interactive;

import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandFormat;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.buttons.ExecutorCache;
import me.jasperedits.embeds.EmbedFormat;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.managers.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "setup",
        permission = Permission.ADMINISTRATOR
)
public class Setup implements Command {
    @Override
    public void execute(CommandInformation information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        output.setTitle(language.getValue("setup.first.title"));
        output.setDescription(language.getValue("setup.first.message"));
        output.setFooter("ID: 0000");
        Message message = information.getInteractionEvent().replyEmbeds(output.build())
                .addActionRow(
                        Button.primary("previous", Emoji.fromMarkdown("<:flora_previous:854084823371350086>"))
                                .asDisabled(),
                        Button.primary("next", Emoji.fromMarkdown("<:flora_next:854084370058051595>")),
                        Button.danger("delete", Emoji.fromMarkdown("<:flora_delete:854093133359874048>"))).complete().retrieveOriginal().complete();

        ExecutorCache.set(message.getId(), information);
    }

    @Override
    public void error(CommandInformation information, EmbedBuilder output, String errorMessage) {
    }

    @Override
    public void button(ButtonClickEvent event, CommandInformation information) {
        if (information.getInteractionEvent().getMember().getId().equals(event.getMember().getId())) {
            switch (event.getComponentId()) {
                case "previous" -> {

                }
                case "delete" -> event.getMessage().delete().queue();
            }
        } else {
            event.deferEdit().queue();
        }
    }
}
