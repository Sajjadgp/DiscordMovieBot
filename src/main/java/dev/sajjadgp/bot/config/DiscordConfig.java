package dev.sajjadgp.bot.config;

import dev.sajjadgp.bot.command.GetCommand;
import dev.sajjadgp.bot.command.SearchCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscordConfig {

    private final SearchCommand searchCommand;
    private final GetCommand getCommand;

    @Value("${discord.api.key}")
    private String token;

    @Bean
    public JDA jda() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(token)
                .addEventListeners(getCommand)
                .addEventListeners(searchCommand)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                //.setActivity(Activity.playing("with your mom"))
                .build().awaitReady();

        jda.updateCommands().addCommands(
                searchCommand.getSlashCommand(),
                getCommand.getSlashCommand()
        ).queue();

        return jda;
    }
}
