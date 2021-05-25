package me.jasperedits.commands;

import lombok.SneakyThrows;
import me.jasperedits.docs.GuildDAO;
import me.jasperedits.docs.impl.Guild;
import me.jasperedits.embeds.EmbedType;
import me.jasperedits.embeds.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandService extends ListenerAdapter {

    @SneakyThrows
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        Member member = event.getMember();
        Guild guild = GuildDAO.getGuild(event.getGuild().getId());
        String prefix = guild.getPrefix();

        if (event.isWebhookMessage() || member.getUser().isBot()) {
            return;
        }

        for (Command command : CommandRegistry.getAllCommands()) {
            if (member.hasPermission(command.getClass().getAnnotation(CommandType.class).permission())) {
                for (String alias : command.getClass().getAnnotation(CommandType.class).names()) {
                    if (message.getContentRaw().equals(prefix + alias)) {
                        List<String> args = new ArrayList<>(Arrays.asList(message.getContentRaw().split(" ")));
                        args.remove(0);

                        CommandRegistry.byName(alias).execute(new CommandInformation(args, event, guild));
                    }
                }

            } else {
                String PERMISSIONS_TITLE = "Unauthorized action";
                String PERMISSIONS_DESC = "You don't have enough permissions to call that command.";

                EmbedBuilder embedBuilder = new EmbedTemplate(EmbedType.ERROR, member.getUser()).getEmbedBuilder();
                embedBuilder.setTitle("**__" + PERMISSIONS_TITLE + "__**");
                embedBuilder.setDescription(PERMISSIONS_DESC);
                message.reply(embedBuilder.build()).queue();
            }
        }
    }
}