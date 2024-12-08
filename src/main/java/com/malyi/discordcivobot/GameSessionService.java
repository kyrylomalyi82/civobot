package com.malyi.discordcivobot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSessionService {

    private final List<String> civilizations = Arrays.asList(
            "America", "Arabia" , "Australia" , "Aztec" , "Brazil" ,
            "China" , "Egypt" , "England" , "France" , "Germany" ,
            "Greece" , "India" , "Japan" , "Kongo" , "Macedon" ,
            "Norway" , "Norway" , "Poland" , "Persia" , "Rome" ,
            "Scythia" , "Spain" , "Sumeria"
    );

    private final Map<String, GameSession> sessions = new HashMap<>();

    public void startSession(String userId) {
        sessions.put(userId, new GameSession());
    }

    public boolean sessionExists(String userId) {
        return sessions.containsKey(userId);
    }

    public void setPlayers(String userId, int players) {
        GameSession session = sessions.get(userId);
        if (session != null) {
            session.setPlayers(players);
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

        for (int i = 1; i <= session.getPlayers(); i++) {
            result.append("Игрок ").append(i).append(": ");
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
        private int players;
        private int picks;

    }
}

