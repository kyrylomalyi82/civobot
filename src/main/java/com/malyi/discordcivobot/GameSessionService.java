package com.malyi.discordcivobot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSessionService {

    private final List<String> civilizations = Arrays.asList(
            "America", "Arabia", "Australia", "Aztec", "Brazil",
            "China", "Egypt", "England", "France", "Germany",
            "Greece", "India", "Japan", "Kongo", "Macedon",
            "Norway", "Poland", "Persia", "Rome",
            "Scythia", "Spain", "Sumeria"
    );

    private final Map<String, GameSession> sessions = new HashMap<>();

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

    public String generatePicks(String userId) {
        GameSession session = sessions.get(userId);
        if (session == null) {
            return "Сессия не найдена!";
        }

        StringBuilder result = new StringBuilder("Результаты:\n");
        List<String> availableCivs = new ArrayList<>(civilizations);
        Random random = new Random();

        for (String player : session.getPlayers()) {
            result.append("**").append(player).append(": **");
            for (int j = 0; j < session.getPicks(); j++) {
                if (availableCivs.isEmpty()) {
                    result.append("\nНе хватает цивилизаций для уникальных пиков!");
                    break;
                }
                String civ = availableCivs.remove(random.nextInt(availableCivs.size()));
                result.append(civ).append(j < session.getPicks() - 1 ? ", " : "");
            }
            result.append("\n");
        }

        sessions.remove(userId);
        return result.toString();
    }

    @Setter
    @Getter
    static class GameSession {
        private List<String> players = new ArrayList<>();
        private int picks;
    }
}