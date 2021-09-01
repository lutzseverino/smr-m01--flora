package me.jasperedits.flora.manager;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonInteraction;

import java.util.ArrayList;
import java.util.List;


/**
 * The idea and some portions of this code was taken from https://github.com/Almighty-Alpaca/JDA-Butler/
 */
public class InteractivePaginator {

    private final List<Message> pages = new ArrayList<>();
    private int index = 0;

    public InteractivePaginator() {
    }

    private boolean isEnd() {
        return index == pages.size() - 1;
    }

    private boolean isStart() {
        return index == 0;
    }

    private Message getNext() {
        return isEnd() ? pages.get(index) : pages.get(++index);
    }

    private Message getPrev() {
        return isStart() ? pages.get(0) : pages.get(--index);
    }

    public Message getCurrent() {
        return pages.get(index);
    }

    public int getCurrentPageCount() {
        return index;
    }

    public void addPage(Message message) {
        pages.add(message);
    }

    public List<Message> getPages() {
        return pages;
    }

    public Message getPage(int pageNumber) {
        return pages.get(pageNumber);
    }

    public int getPageCount() {
        return pages.size();
    }


    public void onButtonClick(ButtonInteraction interaction) {
        String componentId = interaction.getComponentId();

        switch (componentId) {
            case "next" -> {
                interaction.deferEdit().setEmbeds(getNext().getEmbeds())
                        .setActionRows(getButtons())
                        .queue();
            }
            case "previous" -> interaction.deferEdit().setEmbeds(getPrev().getEmbeds())
                    .setActionRows(getButtons())
                    .queue();
            case "delete" -> interaction.getMessage().delete().queue();
        }
    }

    private Button getPrevButton() {
        return Button.secondary("previous", Emoji.fromMarkdown("<:flora_previous:854084823371350086>"));
    }

    private Button getNextButton() {
        return Button.secondary("next", Emoji.fromMarkdown("<:flora_next:854084370058051595>"));
    }

    private Button getDeleteButton() {
        return Button.danger("delete", Emoji.fromMarkdown("<:flora_delete:854093133359874048>"));
    }

    public ActionRow getButtons() {
        return ActionRow.of(
                getPrevButton().withDisabled(isStart()),
                getNextButton().withDisabled(isEnd()),
                getDeleteButton()
        );
    }
}