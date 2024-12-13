package com.malyi.discordcivobot.service;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSessionService {

    private final CivilizationPicker civilizationPicker;
    private final Map<String, GameSession> sessions = new HashMap<>();

    public GameSessionService(CivilizationPicker civilizationPicker) {
        this.civilizationPicker = civilizationPicker;
    }

    public void startSession(String userId) {
        sessions.put(userId, new GameSession());
    }

    public boolean sessionExists(String userId) {
        return sessions.containsKey(userId);
    }

    public void setPlayerMentions(String userId, List<String> playerMentions) {
        GameSession session = sessions.get(userId);
        if (session != null) {
            session.setPlayers(playerMentions);
        }
    }

    public void setPicks(String userId, int picks) {
        GameSession session = sessions.get(userId);
        if (session != null) {
            session.setPicks(picks);
        }
    }

    public EmbedCreateSpec generatePicks(String userId) {
        GameSession session = sessions.get(userId);
        if (session == null) {
            return EmbedCreateSpec.builder()
                    .title("Error")
                    .description("Session not found!")
                    .color(Color.RED)
                    .build();
        }

        List<String> civilizationResults = civilizationPicker.assignCivilizations(session.getPlayers(), session.getPicks());
        List<String> worldGenerationResults = civilizationPicker.pickWorldGenerations(3);

        sessions.remove(userId);

        return EmbedCreateSpec.builder()
                .title("Game Results")
                .addField("Civilization Picks", String.join("\n", civilizationResults), false)
                .addField("World Generation", String.join(", ", worldGenerationResults), false)
                .color(Color.GREEN)
                .build();
    }

    @Setter
    @Getter
    static class GameSession {
        private List<String> players = new ArrayList<>();
        private int picks;
    }
}
