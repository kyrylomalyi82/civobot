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

    private static final String COMMAND_PREFIX = "!";
    private final GameSessionService gameSessionService;

    public CommandHandler(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    // Register the commands and their handlers
    public void registerCommands(DiscordClient client) {
        client.withGateway(gateway -> gateway.on(MessageCreateEvent.class, event -> {
            String content = event.getMessage().getContent();
            String userId = event.getMessage().getAuthor().map(user -> user.getId().asString()).orElse("");

            if (content.startsWith(COMMAND_PREFIX)) {
                // Handle different commands
                if (content.startsWith("!Тестирование")) {
                    return handleTestCommand(event);
                }

                if (content.startsWith("!Тодоров")) {
                    return handleTodorovCommand(event);
                }

                if (content.startsWith("!startgame")) {
                    return handleStartGameCommand(event, userId);
                }

                if (content.startsWith("!players")) {
                    return handlePlayersCommand(event, userId);
                }

                if (content.startsWith("!picks")) {
                    return handlePicksCommand(event, userId, content);
                }
            }

            return Mono.empty();
        })).block();
    }

    // Command handling methods

    private Mono<Void> handleTestCommand(MessageCreateEvent event) {
        return sendMessage(event, "Алик гениален ебал Настю Н");
    }

    private Mono<Void> handleTodorovCommand(MessageCreateEvent event) {
        return sendMessage(event, "Алексей самый гениальный игрок живущий в нашем мире");
    }

    private Mono<Void> handleStartGameCommand(MessageCreateEvent event, String userId) {
        gameSessionService.startSession(userId);
        return sendMessage(event, "Игра началась! Укажите участников командой: `!players @user1 @user2 ...`");
    }

    private Mono<Void> handlePlayersCommand(MessageCreateEvent event, String userId) {
        // Extract and handle player mentions
        List<String> players = event.getMessage().getUserMentions().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        if (players.isEmpty()) {
            return sendMessage(event, "Необходимо указать хотя бы одного игрока с помощью упоминания.");
        }

        gameSessionService.setPlayerMentions(userId, players);
        return sendMessage(event, "Каждому игроку будет назначена цивилизация. Введите `!picks <число>` чтобы указать количество пиков.");
    }

    private Mono<Void> handlePicksCommand(MessageCreateEvent event, String userId, String content) {
        try {
            int picks = Integer.parseInt(content.split(" ")[1]);
            if (picks < 1 || picks > 5) {
                return sendMessage(event, "Кол-во пиков должно быть между 1 и 5. Введите `!picks <число>`");
            }
            gameSessionService.setPicks(userId, picks);
            String result = gameSessionService.generatePicks(userId);
            return sendMessage(event, result);
        } catch (NumberFormatException e) {
            return sendMessage(event, "Введите корректное число пиков. `!picks <число>`");
        }
    }

    // Utility method to send messages
    private Mono<Void> sendMessage(MessageCreateEvent event, String message) {
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(message))
                .then();
    }
}
