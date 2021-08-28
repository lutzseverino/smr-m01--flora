package me.jasperedits.command.impl.interactive;

import lombok.SneakyThrows;
import me.jasperedits.cache.AnswerClassifierCache;
import me.jasperedits.cache.PaginatorClassifierCache;
import me.jasperedits.command.Command;
import me.jasperedits.command.settings.CommandFormat;
import me.jasperedits.command.CommandData;
import me.jasperedits.command.annotation.CommandType;
import me.jasperedits.embed.EmbedFormat;
import me.jasperedits.embed.EmbedTemplate;
import me.jasperedits.manager.InteractivePaginator;
import me.jasperedits.manager.Language;
import me.jasperedits.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = CommandFormat.INTERACTIVE,
        names = "setup",
        permission = Permission.ADMINISTRATOR
)
public class Setup implements Command {

    @Override
    public void execute(CommandData information) {
        Language language = information.getGuild().getLanguage();
        Member member = information.getInteractionEvent().getMember();

        // Create the embed used for output.
        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

        // Create a paginator and insert the pages.
        InteractivePaginator paginator = new InteractivePaginator();
        for (int page = 0; language.getValue("setup.page." + page + ".title") != null; page++) {
            output.setTitle(language.getValue("setup.page." + page + ".title"));
            output.setDescription(language.getValue("setup.page." + page + ".description"));
            output.setFooter(language.getValue("setup.pages")
                    .replace("%s", String.valueOf(paginator.getPageCount())));
            paginator.addPage(new MessageBuilder().setEmbed(output.build()).build());
        }

        output.setTitle(language.getValue("setup.page.0.title"));
        output.setDescription(language.getValue("setup.page.0.description"));
        output.setFooter(language.getValue("setup.pages")
                .replace("%s", "0"));

        Message message = information.getInteractionEvent().replyEmbeds(output.build())
                .addActionRows(paginator.getButtons())
                .complete()
                .retrieveOriginal()
                .complete();

        AnswerClassifierCache.set(message.getIdLong(), information);
        PaginatorClassifierCache.set(information.getInteractionEvent().getIdLong(), paginator);
    }

    @SneakyThrows
    @Override
    public void button(ButtonClickEvent event, CommandData information) {
        if (information.getInteractionEvent().getMember().getId().equals(event.getMember().getId())) {
            if (PaginatorClassifierCache.get(information.getInteractionEvent().getIdLong()) != null)
                PaginatorClassifierCache.get(information.getInteractionEvent().getIdLong()).onButtonClick(event);
            else DiscordUtil.expire(event);
        } else event.deferEdit().queue();
    }
}
