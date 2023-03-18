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

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordService {

    private final MovieService movieService;
    private final StringUtil stringUtil;

    public void search(SlashCommandInteractionEvent event) {
        try {
            String query = event.getOption("query").getAsString();

            List<Search> result = movieService.search(query);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Your search result");
            embed.setDescription(stringUtil.list(result));

            reply(event, embed);
        } catch (RuntimeException exception) {
            reply(event, exception.getMessage());
        }
    }

    public void get(SlashCommandInteractionEvent event) {
        try {
            int index = event.getOption("index").getAsInt();

            Movie found = movieService.get(index);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(Movie.generateTitle(found));
            embed.setDescription(found.getOverview());
            embed.setImage(Movie.generateUrl(found.getPoster()));

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
