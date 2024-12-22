package com.malyi.discordcivobot;

import com.malyi.discordcivobot.service.GameSessionService;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class CommandHandler {

    private static final String COMMAND_PREFIX = "!";
    private final GameSessionService gameSessionService;
    private final MessageSource messageSource;

    public CommandHandler(GameSessionService gameSessionService, MessageSource messageSource) {
        this.gameSessionService = gameSessionService;
        this.messageSource = messageSource;
    }

    // Register the commands and their handlers
    public void registerCommands(DiscordClient client) {
        client.withGateway(gateway -> gateway.on(MessageCreateEvent.class, event -> {
            String content = event.getMessage().getContent();
            String userId = event.getMessage().getAuthor().map(user -> user.getId().asString()).orElse("");

            // Check if the message starts with the command prefix
            if (content.startsWith(COMMAND_PREFIX)) {
                String command = content.split(" ")[0].toLowerCase(); // Extract the command part and convert to lowercase

                // Switch case to handle different commands
                return switch (command) {
                    case "!startgame" -> handleStartGameCommand(event, userId);
                    case "!players" -> handlePlayersCommand(event, userId);
                    case "!picks" -> handlePicksCommand(event, userId, content);
                    case "!setlang" -> handleSetLangCommand(event, userId, content);
                    default -> sendMessage(event, "no_command");
                };
            }

            return Mono.empty(); // Return empty if the message doesn't start with a recognized command
        })).block();
    }

    private Mono<Void> handleStartGameCommand(MessageCreateEvent event, String userId) {
        gameSessionService.startSession(userId);
        return sendMessage(event, "game_started");
    }

    private Mono<Void> handlePlayersCommand(MessageCreateEvent event, String userId) {
        // Extract and handle player mentions
        List<String> players = event.getMessage().getUserMentions().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        if (players.isEmpty()) {
            return sendMessage(event, "specify_players");
        }

        gameSessionService.setPlayerMentions(userId, players);
        return sendMessage(event, "set_picks_number");
    }

    private Mono<Void> handlePicksCommand(MessageCreateEvent event, String userId, String content) {
        try {
            int picks = Integer.parseInt(content.split(" ")[1]);
            if (picks < 1 || picks > 5) {
                return sendMessage(event, "set_picks_number");
            }
            gameSessionService.setPicks(userId, picks);
            EmbedCreateSpec result = gameSessionService.generatePicks(userId);
            return sendMessage(event, result);
        } catch (NumberFormatException e) {
            return sendMessage(event, "invalid_picks");
        }
    }

    private Mono<Void> handleSetLangCommand(MessageCreateEvent event, String userId, String content) {
        String[] parts = content.split(" ");
        String languageCode = parts[1];
        Locale locale;

        try {
            locale = new Locale(languageCode);
            LocaleContextHolder.setLocale(locale);
        } catch (Exception e) {
            return sendMessage(event, "invalid_language");
        }

        return sendMessage(event, "language_changed");
    }


    // Utility method to send messages in a localized way
    private Mono<Void> sendMessage(MessageCreateEvent event, String messageKey) {
        String localizedMessage = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(localizedMessage))
                .then();
    }

    private Mono<Void> sendMessage(MessageCreateEvent event, EmbedCreateSpec message) {
        return event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(message))
                .then();
    }
}
