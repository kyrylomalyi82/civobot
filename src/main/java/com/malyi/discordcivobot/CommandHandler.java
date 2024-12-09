package com.malyi.discordcivobot;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandHandler {

    private final GameSessionService gameSessionService;

    public CommandHandler(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    // Should be refactored in future and every command
    // will be a new method which will be called by controller @kyrylomalyi81 08.12.2024

    public void registerCommands(DiscordClient client) {
        client.withGateway(gateway -> gateway.on(MessageCreateEvent.class, event -> {
            String content = event.getMessage().getContent();
            String userId = event.getMessage().getAuthor().map(user -> user.getId().asString()).orElse("");

            if (content.startsWith("!Тестирование")) {
                return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Алик гениален ебал Настю Н"));
            }

            if (content.startsWith("!Тодоров")) {
                return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Алексей самый гениальный игрок живущий в нашем мире"));
            }

            if (content.startsWith("!startgame")) {
                gameSessionService.startSession(userId);
                return event.getMessage().getChannel()
                        .flatMap(channel -> channel.createMessage("Игра началась! Укажите участников командой: `!players @user1 @user2 ...`"));
            }

            if (content.startsWith("!players")) {
                // Extract and handle player mentions
                List<String> players = event.getMessage().getUserMentions().stream()
                        .map(User::getUsername)
                        .collect(Collectors.toList());

                if (players.isEmpty()) {
                    return event.getMessage().getChannel()
                            .flatMap(channel -> channel.createMessage("Необходимо указать хотя бы одного игрока с помощью упоминания."));
                }

                gameSessionService.setPlayerMentions(userId, players);
                return event.getMessage().getChannel()
                        .flatMap(channel -> channel.createMessage("Каждому игроку будет назначена цивилизация. Введите `!picks <число>` чтобы указать количество пиков."));
            }

            if (content.startsWith("!picks")) {
                try {
                    int picks = Integer.parseInt(content.split(" ")[1]);
                    if (picks < 1 || picks > 5) {
                        return event.getMessage().getChannel()
                                .flatMap(channel -> channel.createMessage("Кол-во пиков должно быть между 1 и 5. Введите `!picks <число>`"));
                    }
                    gameSessionService.setPicks(userId, picks);
                    String result = gameSessionService.generatePicks(userId);
                    return event.getMessage().getChannel().flatMap(channel -> channel.createMessage(result));
                } catch (NumberFormatException e) {
                    return event.getMessage().getChannel().flatMap(channel -> channel.createMessage("Введите корректное число пиков. `!picks <число>`"));
                }
            }

            return Mono.empty();
        })).block();
    }
}