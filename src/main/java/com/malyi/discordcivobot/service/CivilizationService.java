package com.malyi.discordcivobot.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
@Setter
public class CivilizationService {

    private final List<String> civilizations = List.of(
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

    private final List<String> worldGenerations = List.of(
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
}
