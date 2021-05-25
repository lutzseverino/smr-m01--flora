package me.jasperedits.commands.impl;

import lombok.SneakyThrows;
import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.docs.GuildDAO;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.embeds.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

@CommandType(names = {"prefix", "setPrefix"}, usage = "[A = text] {A < 3}", permission = Permission.ADMINISTRATOR)
public class Prefix implements Command {
    GuildMessageReceivedEvent event;
    Command command;

    public Prefix() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {
        String ARGUMENTS = "Incorrect arguments";
        String UPLOADED = "New settings saved";

        Message message = command.getEvent().getMessage();
        Member member = command.getEvent().getMember();

        if (command.getArgs().size() != 1 || command.getArgs().get(0).length() > 3) {
            EmbedBuilder errorEmbed = new EmbedTemplate(EmbedType.ERROR, member.getUser()).getEmbedBuilder();
            errorEmbed.setTitle("**__" + ARGUMENTS + "__**");
            errorEmbed.setDescription("`<optional>, [mandatory], {rules}`\n\n" +
                    "Correct usage is: `" + this.getClass().getAnnotation(CommandType.class).usage() + "`");
            message.reply(errorEmbed.build()).queue();
            return;
        }

        String prefix = command.getArgs().get(0);

        EmbedBuilder loadingEmbed = new EmbedTemplate(EmbedType.PROCESS, member.getUser()).getEmbedBuilder();
        loadingEmbed.setDescription("Uploading new data to database...");
        Message embedToUpdate = message.reply(loadingEmbed.build()).complete();

        command.getGuild().setPrefix(prefix);
        GuildDAO.updateGuild(command.getGuild());

        EmbedBuilder successEmbed = new EmbedTemplate(EmbedType.SUCCESS, member.getUser()).getEmbedBuilder();
        successEmbed.setTitle("**__" + UPLOADED + "__**");
        successEmbed.setDescription("Prefix has been updated to `" + prefix + "`.");
        embedToUpdate.editMessage(successEmbed.build()).queue();
    }
}
