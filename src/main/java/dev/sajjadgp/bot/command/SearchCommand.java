package dev.sajjadgp.bot.command;

import dev.sajjadgp.bot.service.DiscordService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchCommand extends ListenerAdapter {

    private final DiscordService discordService;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("search")) {
            event.deferReply().queue();

            discordService.search(event);
        }
    }

    public SlashCommandData getSlashCommand() {
        return Commands.slash("search", "search movies")
                .addOption(OptionType.STRING, "query", "type any movie name you want.", true);
    }
}