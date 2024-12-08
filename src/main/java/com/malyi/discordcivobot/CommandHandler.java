package com.malyi.discordcivobot;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CommandHandler {

    private final GameSessionService gameSessionService;

    public CommandHandler(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    public void registerCommands(DiscordClient client) {
        client.withGateway(gateway -> gateway.on(MessageCreateEvent.class, event -> {
            String content = event.getMessage().getContent();
            String userId = event.getMessage().getAuthor().map(user -> user.getId().asString()).orElse("");

            if (content.startsWith("!startgame")) {
                gameSessionService.startSession(userId);
                return event.getMessage().getChannel()
                        .flatMap(channel -> channel.createMessage("Игра началась! Сколько игроков участвует? Введите команду: `!players <число>`"));
            }

            if (content.startsWith("!players")) {
                try {
                    int players = Integer.parseInt(content.split(" ")[1]);
                    gameSessionService.setPlayers(userId, players);
                    return event.getMessage().getChannel()
                            .flatMap(channel -> channel.createMessage("Укажите количество пиков для каждого игрока командой: `!picks <число>`"));
                } catch (NumberFormatException e) {
                    return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Введите корректное число игроков."));
                }
            }

            if (content.startsWith("!picks")) {
                try {
                    int picks = Integer.parseInt(content.split(" ")[1]);
                    gameSessionService.setPicks(userId, picks);
                    String result = gameSessionService.generatePicks(userId);
                    return event.getMessage().getChannel().flatMap(channel -> channel.createMessage(result));
                } catch (NumberFormatException e) {
                    return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Введите корректное число пиков."));
                }
            }

            return Mono.empty();
        })).block();
    }
}