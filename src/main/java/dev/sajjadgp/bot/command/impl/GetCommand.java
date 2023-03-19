package dev.sajjadgp.bot.command.impl;

import dev.sajjadgp.bot.command.CommandEvent;
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
public class GetCommand extends ListenerAdapter implements CommandEvent {

    private final DiscordService discordService;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        handle(event, () -> discordService.get(event));
    }

    @Override
    public String getValueAsString() {
        return "get";
    }

    @Override
    public SlashCommandData getValue() {
        return Commands.slash(getValueAsString(), "get a movies")
                .addOption(
                        OptionType.INTEGER, "index", "type an index from you search list.", true
                );
    }
}
