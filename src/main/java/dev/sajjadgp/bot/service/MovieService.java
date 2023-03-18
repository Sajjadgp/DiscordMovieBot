package dev.sajjadgp.bot.service;

import dev.sajjadgp.bot.client.MovieClient;
import dev.sajjadgp.bot.model.Movie;
import dev.sajjadgp.bot.model.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final Map<Integer, Search> searches = new HashMap<>();

    private final MovieClient movieClient;

    public Movie get(Integer index) {
        Search search = searches.get(index);
        if (search == null) {
            throw new RuntimeException("Index: %s in not valid!".formatted(index));
        }

        Long tmdbId = search.getTmdbId();
        String type = search.getMediaType();

        Movie found = movieClient.get(tmdbId, type);

        log.info("Got a movie: {}", found);
        return found;
    }

    public List<Search> search(String query) {
        List<Search> result = movieClient.search(query).getResults().stream()
                .filter(s -> s.getPoster() != null &&
                        s.getBackdrop() != null &&
                        s.getOverview() != null &&
                        s.getReleased() != null)
                .toList();

        if (result.isEmpty()) {
            throw new RuntimeException("Movie: %s does not exist!".formatted(query));
        }

        cache(result);

        log.info("Searched a query: query={}, result={}", query, result);
        return result;
    }

    private void cache(List<Search> result) {
        searches.clear();
        result.forEach(s -> searches.put((result.indexOf(s) + 1), s));
    }
}