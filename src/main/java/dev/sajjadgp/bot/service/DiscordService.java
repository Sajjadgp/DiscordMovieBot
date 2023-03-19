package dev.sajjadgp.bot.service;

import dev.sajjadgp.bot.model.Movie;
import dev.sajjadgp.bot.model.Search;
import dev.sajjadgp.bot.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordService {

    private final MovieService movieService;
    private final StringUtil stringUtil;

    public void search(SlashCommandInteractionEvent event) {
        try {
            String query = Objects.requireNonNull(event.getOption("query")).getAsString();

            List<Search> result = movieService.search(query);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Your search result");
            embed.setDescription(stringUtil.listSearchResult(result));

            reply(event, embed);
        } catch (RuntimeException exception) {
            reply(event, exception.getMessage());
        }
    }

    public void get(SlashCommandInteractionEvent event) {
        try {
            int index = Objects.requireNonNull(event.getOption("index")).getAsInt();

            Movie found = movieService.get(index);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(stringUtil.generateMovieTitle(found));
            embed.setDescription(found.getOverview());
            embed.setImage(stringUtil.generateMovieUrl(found.getPoster()));

            reply(event, embed);
        } catch (RuntimeException exception) {
            reply(event, exception.getMessage());
        }
    }

    private void reply(SlashCommandInteractionEvent event, String message) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("An error has occurred!");
        embed.setDescription(message);
        embed.setColor(Color.RED);

        reply(event, embed);
    }

    private void reply(SlashCommandInteractionEvent event, EmbedBuilder embed) {
        event.getHook().sendMessageEmbeds(embed.build()).queue();
    }
}
