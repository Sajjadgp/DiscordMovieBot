package dev.sajjadgp.bot.model;

import com.fasterxml.jackson.annotation.*;
import dev.sajjadgp.bot.annotation.CalculatedTime;
import dev.sajjadgp.bot.annotation.CollectionOfJson;
import dev.sajjadgp.bot.annotation.IdFromJson;
import dev.sajjadgp.bot.annotation.SimpleDoubleNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable {

    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private Long tmdbId;

    @IdFromJson
    @JsonProperty("external_ids")
    private String imdbId;

    @JsonAlias({"title", "name"})
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("poster_path")
    private String poster;

    @JsonProperty("backdrop_path")
    private String backdrop;

    @CalculatedTime
    @JsonAlias({"runtime", "episode_run_time"})
    private String runtime;

    @SimpleDoubleNumber
    @JsonProperty("vote_average")
    private Double rating;

    @JsonProperty(value = "number_of_episodes")
    private Integer episodes = 1;

    @JsonProperty(value = "number_of_seasons")
    private Integer seasons = 0;

    @CollectionOfJson
    @JsonProperty("genres")
    private List<String> genres;

    @JsonAlias({"release_date", "first_air_date"})
    private LocalDate released;

    public static String generateTitle(Movie m) {
        return m.getTitle() + " " + m.getReleased().getYear();
    }

    public static String generateUrl(String path) {
        return "https://image.tmdb.org/t/p/original" + path;
    }

}