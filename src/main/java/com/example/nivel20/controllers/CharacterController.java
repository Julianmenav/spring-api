package com.example.nivel20.controllers;

import com.example.nivel20.entities.Character;
import com.example.nivel20.repositories.CharacterRepository;

import com.example.nivel20.requests.CreateCharacterRequest;
import com.example.nivel20.requests.UpgradeStatsRequest;
import com.example.nivel20.services.CharacterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CharacterController {

    private final Logger log = LoggerFactory.getLogger(CharacterController.class);
    private final CharacterRepository characterRepository;

    public CharacterController(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @GetMapping("/api/characters")
    public List<Character> characters() {
        return characterRepository.findAll();
    }

    @GetMapping("/api/characters/{id}")
    public ResponseEntity<Character> character(@PathVariable Long id){
        Optional<Character> optCharacter = characterRepository.findById(id);
        if(optCharacter.isPresent()){
            return ResponseEntity.ok(optCharacter.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/api/characters")
    public ResponseEntity<Character> create(@RequestBody CreateCharacterRequest request){

        Character newCharacter = CharacterFactory.createCharacter(request);
        return ResponseEntity.ok(characterRepository.save(newCharacter));
    }

    @PutMapping("/api/characters/{id}/levelup")
    public ResponseEntity<Character> levelUp(@PathVariable Long id ){
        Optional<Character> optCharacter = characterRepository.findById(id);
        if(optCharacter.isPresent()){
            Character updatedCharacter = optCharacter.get();
            updatedCharacter.levelUp();
            return ResponseEntity.ok(characterRepository.save(updatedCharacter));
        } else {
            log.warn("Trying ot update non existent character");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/characters/{id}/stats")
    public ResponseEntity<Character> upgradeStats(@PathVariable Long id, @RequestBody UpgradeStatsRequest request) {
        Optional<Character> optCharacter = characterRepository.findById(id);
        if(optCharacter.isEmpty()){
            log.warn("Trying to update non existent character");
            return ResponseEntity.badRequest().build();
        }

        Character updatedCharacter = optCharacter.get();
        if(request.totalPoints() > updatedCharacter.getStatPoints()){
            log.warn("Trying to upgrade more points than availables");
            return ResponseEntity.badRequest().build();
        }

        updatedCharacter.upgradeStats(request);
        return ResponseEntity.ok(characterRepository.save(updatedCharacter));
    }

    @DeleteMapping("/api/characters/{id}")
    public ResponseEntity<Character> delete(@PathVariable Long id){
        if(!characterRepository.existsById(id)){
            log.warn("Trying to delete non existent character");
            return ResponseEntity.badRequest().build();
        }
        characterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}



