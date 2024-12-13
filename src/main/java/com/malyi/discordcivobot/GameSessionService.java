package com.malyi.discordcivobot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameSessionService {

    private final List<String> civilizations = Arrays.asList(
            "Teddy Roosevelt" , "Teddy Roosevelt (Bull Moose)" , "Teddy Roosevelt (Rough Rider)" , "Abraham Lincoln" ,
            "Saladin (Vizier)" , "Saladin (Sultan)" ,
            "John Curtin" ,
            "Montezuma" ,
            "Hammurabi" ,
            "Pedro II" ,
            "Basil II" , "Theodora" ,
            "Qin Shi Huang (Mandate of Heaven)", "Qin Shi Huang (Unifier)" , "Kublai Khan (Chinese)" , "Yongle" , "Wu Zetian" ,
            "Poundmaker" ,
            "Wilhelmina" ,
            "Cleopatra (Egyptian)" , "Cleopatra (Ptolemaic)" , "Ramses II" ,
            "Victoria (Age of Empire)" , "Victoria (Age of Steam)" , "Eleanor of Aquitaine (English)" ,"Elizabeth I" ,
            "Menelik II" , "Catherine de Medici (Black Queen)" , "Catherine de Medici (Magnificence)" , "Eleanor of Aquitaine (French)" ,
            "Ambiorix" ,
            "Tamar" ,
            "Frederick Barbarossa" , "Ludwig II" ,
            "Simón Bolívar" ,
            "Pericles" , "Gorgo" ,
            "Matthias Corvinus" ,
            "Pachacut" ,
            "Gandhi" , "Chandragupta" ,
            "Gitarja" ,
            "Hojo Tokimune" , "Tokugawa" ,
            "Jayavarman VII" ,
            "Mvemba a Nzinga" , "Nzinga Mbande" ,
            "Seondeok" , "Sejong" ,
            "Alexander" ,
            "Mansa Musa" , "Sundiata Keita" ,
            "Kupe" ,
            "Lautaro" ,
            "Lady Six Sky" ,
            "Genghis Khan" , "Kublai Khan (Mongolian)" ,
            "Harald Hardrada (Konge)" , "Harald Hardrada (Varangian)" ,
            "Nubian Amanitore" ,
            "Suleiman (Kanuni)" , "Suleiman (Muhteşem)" ,
            "Cyrus" , "Nader Shah" ,
            "Dido" ,
            "Jadwiga" ,
            "Joan III" ,
            "Trajan" , "Julius Caesar" ,
            "Peter" ,
            "Robert the Bruce" ,
            "Tomyris" ,
            "Philip II" ,
            "Gilgamesh" ,
            "Kristina" ,
            "Ba Trieu" ,
            "Shaka"
    );

    private final List<String> worldGeneration = Arrays.asList(
            "Continents" ,
            "Fractal" ,
            "Island Plates" ,
            "Pangaea" ,
            "Archipelago" ,
            "Seven Seas " ,
            "Small Continents" ,
            "Continents and islands" ,
            "Primordial" ,
            "Splintered Fractal" ,
            "Terra"
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


    // suggestion for refactoring create a method to print results
    public String generatePicks(String userId) {
        GameSession session = sessions.get(userId);
        if (session == null) {
            return "Сессия не найдена!";
        }

        StringBuilder result = new StringBuilder("**Результаты:**\n" + "\n");
        List<String> availableCivs = new ArrayList<>(civilizations);
        List<String> availableWorldGeneration = new ArrayList<>(worldGeneration);
        Random random = new Random();

        for (String player : session.getPlayers()) {
            result.append("**").append(player).append(": **");
            for (int j = 0; j < session.getPicks(); j++) {
                String civ = availableCivs.remove(random.nextInt(availableCivs.size()));
                result.append(civ).append(j < session.getPicks() - 1 ? ", " : "");
            }
            result.append("\n");
        }

        result.append("\n");
        result.append("**World Generation:**");
        for (int j = 0; j < 3 ; j++  ){
            String world = availableWorldGeneration.remove(random.nextInt(availableWorldGeneration.size()));
            result.append(world).append(j < 2 ? ", " : "");
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