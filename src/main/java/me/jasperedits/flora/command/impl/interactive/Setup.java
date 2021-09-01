package me.jasperedits.flora.command.impl.interactive;

import lombok.SneakyThrows;
import me.jasperedits.flora.cache.AnswerClassifierCache;
import me.jasperedits.flora.cache.PaginatorClassifierCache;
import me.jasperedits.flora.command.Command;
import me.jasperedits.flora.command.ExecutionData;
import me.jasperedits.flora.command.annotation.ButtonAction;
import me.jasperedits.flora.command.annotation.CommandType;
import me.jasperedits.flora.command.annotation.CommandType.Format;
import me.jasperedits.flora.command.annotation.LonelyCommand;
import me.jasperedits.flora.embed.EmbedFormat;
import me.jasperedits.flora.embed.EmbedTemplate;
import me.jasperedits.flora.manager.InteractivePaginator;
import me.jasperedits.flora.manager.Language;
import me.jasperedits.flora.util.DiscordUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

@CommandType(
        format = Format.INTERACTIVE,
        permission = Permission.ADMINISTRATOR
)
public class Setup extends Command {

    @LonelyCommand
    public void setup(ExecutionData data) {
        Language language = data.getGuild().getLanguage();
        Member member = data.getInteractionEvent().getMember();

        EmbedBuilder output = new EmbedTemplate(EmbedFormat.DEFAULT, member.getUser()).getEmbedBuilder();

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

        Message message = data.getInteractionEvent().replyEmbeds(output.build())
                .addActionRows(paginator.getButtons())
                .complete()
                .retrieveOriginal()
                .complete();

        AnswerClassifierCache.set(message.getIdLong(), data);
        PaginatorClassifierCache.set(data.getInteractionEvent().getIdLong(), paginator);
    }

    @SneakyThrows
    @ButtonAction
    public void button(ButtonClickEvent event, ExecutionData information) {
        if (information.getInteractionEvent().getMember().getId().equals(event.getMember().getId())) {
            if (PaginatorClassifierCache.get(information.getInteractionEvent().getIdLong()) != null)
                PaginatorClassifierCache.get(information.getInteractionEvent().getIdLong()).onButtonClick(event);
            else DiscordUtil.throwExpiration(event);
        } else event.deferEdit().queue();
    }
}
