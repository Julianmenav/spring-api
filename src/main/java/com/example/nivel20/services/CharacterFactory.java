package com.example.nivel20.services;

import com.example.nivel20.entities.Character;
import com.example.nivel20.entities.Mage;
import com.example.nivel20.entities.Marksman;
import com.example.nivel20.entities.Warrior;
import com.example.nivel20.requests.CreateCharacterRequest;

public class CharacterFactory {

    public static Character createCharacter(CreateCharacterRequest request){

        String name = request.getName();
        String character_type = request.getCharacter_type();

        return switch (character_type.toUpperCase()) {
            case "WARRIOR" -> new Warrior(name);
            case "MAGE" -> new Mage(name);
            case "MARKSMAN" -> new Marksman(name);
            default -> throw new IllegalArgumentException("Not valid character type");
        };
    }
}
