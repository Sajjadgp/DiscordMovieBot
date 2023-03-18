package dev.sajjadgp.bot.util;

import dev.sajjadgp.bot.model.Search;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StringUtil {

    public String list(List<Search> values) {
        StringBuilder builder = new StringBuilder();

        values.forEach(s -> {
            int index = values.indexOf(s) + 1;
            String title = Search.generateTitle(s);

            builder.append(index).append(" - ").append(title).append("\n");
        });

        return builder.toString();
    }
}
