package dev.sajjadgp.bot.client;

import dev.sajjadgp.bot.model.Movie;
import dev.sajjadgp.bot.model.Search;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "movieclient", url = "${tmdb.api-url}")
public interface MovieClient {

    @GetMapping("${tmdb.api.get-search}")
    Search.SearchResponse search(@PathVariable("query") String query);

    @GetMapping("${tmdb.api.get-movie}")
    Movie get(@PathVariable("tmdbId") Long tmdbId, @PathVariable("type") String type);
}