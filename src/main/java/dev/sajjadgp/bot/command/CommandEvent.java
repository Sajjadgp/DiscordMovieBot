package dev.sajjadgp.bot.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface CommandEvent extends Commandable {

    String getValueAsString();
    
    default void handle(SlashCommandInteractionEvent event, Runnable runnable) {
        if (event.getName().equalsIgnoreCase(getValueAsString())) {
            event.deferReply().queue();

            runnable.run();
        }
    }
}
