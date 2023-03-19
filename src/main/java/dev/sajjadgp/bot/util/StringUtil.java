package dev.sajjadgp.bot.util;

import dev.sajjadgp.bot.model.Movie;
import dev.sajjadgp.bot.model.Search;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringUtil {

    @Value("${tmdb.api.image.base_url}")
    private String imageBaseUrl;

    public String listSearchResult(List<Search> values) {
        StringBuilder builder = new StringBuilder();

        values.forEach(s -> {
            int index = values.indexOf(s) + 1;

            builder.append(index)
                    .append(" - ").
                    append(generateSearchTitle(s))
                    .append("\n");
        });

        return builder.toString();
    }

    public String generateMovieTitle(Movie m) {
        StringBuilder builder = new StringBuilder();

        builder.append(m.getTitle())
                .append(" ")
                .append(m.getReleased().getYear());

        return builder.toString();
    }

    public String generateMovieUrl(String path) {
        StringBuilder builder = new StringBuilder();

        builder.append(imageBaseUrl)
                .append(path);

        return builder.toString();
    }

    public String generateSearchTitle(Search s) {
        StringBuilder builder = new StringBuilder();

        builder.append(s.getTitle())
                .append(" (")
                .append(s.getReleased().getYear())
                .append(")");

        return builder.toString();
    }
}
