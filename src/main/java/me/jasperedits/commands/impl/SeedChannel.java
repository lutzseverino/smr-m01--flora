package me.jasperedits.commands.impl;

import lombok.SneakyThrows;
import me.jasperedits.commands.Command;
import me.jasperedits.commands.CommandInformation;
import me.jasperedits.commands.CommandType;
import me.jasperedits.docs.GuildDAO;
import me.jasperedits.embeds.EmbedTemplate;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.utils.MathUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

@CommandType(names = {"channel", "seedChannel"}, usage = "[A = channel]", permission = Permission.ADMINISTRATOR)
public class SeedChannel implements Command {
    GuildMessageReceivedEvent event;
    Command command;

    public SeedChannel() {
    }

    @SneakyThrows
    @Override
    public void execute(CommandInformation command) {
        String ARGUMENTS = "Incorrect arguments";
        String UPLOADED = "New settings saved";

        Message message = command.getEvent().getMessage();
        Member member = command.getEvent().getMember();

        // To-do: This won't work with a channel mention.
        if (command.getArgs().size() != 1 || command.getEvent().getGuild().getTextChannelById(command.getArgs().get(0).replaceAll("\\D+","")) == null) {
            EmbedBuilder errorEmbed = new EmbedTemplate(EmbedType.ERROR, member.getUser()).getEmbedBuilder();
            errorEmbed.setTitle("**__" + ARGUMENTS + "__**");
            errorEmbed.setDescription("`<optional>, [mandatory], {rules}`\n\n" +
                    "Correct usage is: `" + this.getClass().getAnnotation(CommandType.class).usage() + "`");
            message.reply(errorEmbed.build()).queue();
            return;
        }

        String seed = command.getArgs().get(0);

        EmbedBuilder loadingEmbed = new EmbedTemplate(EmbedType.PROCESS, member.getUser()).getEmbedBuilder();
        loadingEmbed.setDescription("Uploading new data to database...");
        Message embedToUpdate = message.reply(loadingEmbed.build()).complete();

        command.getGuild().setSeedChannel(seed.replaceAll("\\D+",""));
        GuildDAO.updateGuild(command.getGuild());

        EmbedBuilder successEmbed = new EmbedTemplate(EmbedType.SUCCESS, member.getUser()).getEmbedBuilder();
        successEmbed.setTitle("**__" + UPLOADED + "__**");
        successEmbed.setDescription("Seed channel has been updated to " + seed + ".");
        embedToUpdate.editMessage(successEmbed.build()).queue();

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(EmbedType.SUCCESS.hexColor);
        embedBuilder.setDescription("**Next objective**: " + MathUtils.getGuildObjective(command.getEvent().getGuild()) + " messages");
        command.getEvent().getGuild().getTextChannelById(seed.replaceAll("\\D+","")).sendMessage(embedBuilder.build()).queue();
    }
}
