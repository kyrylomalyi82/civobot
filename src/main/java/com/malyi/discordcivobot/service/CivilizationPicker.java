package com.malyi.discordcivobot.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class CivilizationPicker {

    private final CivilizationService civilizationService;

    public CivilizationPicker(CivilizationService civilizationService) {
        this.civilizationService = civilizationService;
    }

    public List<String> assignCivilizations(List<String> players, int picks) {
        Random random = new Random();
        List<String> results = new ArrayList<>();

        for (String player : players) {
            List<String> playerCivs = new ArrayList<>();
            List<String> availableCivs = new ArrayList<>(civilizationService.getCivilizations());

            for (int i = 0; i < picks; i++) {
                if (availableCivs.isEmpty()) break; // Ensure no duplicates
                String civ = availableCivs.remove(random.nextInt(availableCivs.size()));
                playerCivs.add(civ);
            }

            results.add(player + ": " + String.join(", ", playerCivs));
        }

        return results;
    }

    public List<String> pickWorldGenerations(int count) {
        Random random = new Random();
        List<String> results = new ArrayList<>(civilizationService.getWorldGenerations());
        Collections.shuffle(results, random);
        return results.subList(0, Math.min(count, results.size()));
    }
}
