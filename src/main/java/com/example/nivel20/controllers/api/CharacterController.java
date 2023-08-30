package com.example.nivel20.controllers.api;

import com.example.nivel20.entities.Character;
import com.example.nivel20.entities.User;
import com.example.nivel20.repositories.CharacterRepository;

import com.example.nivel20.repositories.UserRepository;
import com.example.nivel20.requests.CreateCharacterRequest;
import com.example.nivel20.requests.UpgradeStatsRequest;
import com.example.nivel20.services.CharacterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CharacterController {

    private final Logger log = LoggerFactory.getLogger(CharacterController.class);
    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;

    public CharacterController(CharacterRepository characterRepository, UserRepository userRepository) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/characters")
    public List<Character> characters() {
        // Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);


        return characterRepository.findByUserId(authOptUser.get().getId());
    }

    @GetMapping("/api/characters/{id}")
    public ResponseEntity<Character> character(@PathVariable Long id){
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        //Get character
        Optional<Character> optCharacter = characterRepository.findById(id);

        if(optCharacter.isEmpty()) return ResponseEntity.notFound().build();
        if(!optCharacter.get().getUserId().equals(authId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(optCharacter.get());

    }
    @PostMapping("/api/characters")
    public ResponseEntity<Character> create(@RequestBody CreateCharacterRequest request){
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        Character newCharacter = CharacterFactory.createCharacter(request, authId);
        return ResponseEntity.ok(characterRepository.save(newCharacter));
    }

    @PutMapping("/api/characters/{id}/levelup")
    public ResponseEntity<Character> levelUp(@PathVariable Long id ){
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        Optional<Character> optCharacter = characterRepository.findById(id);

        if(optCharacter.isEmpty()) return ResponseEntity.badRequest().build();
        if(!optCharacter.get().getUserId().equals(authId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Character updatedCharacter = optCharacter.get();
        updatedCharacter.levelUp();
        return ResponseEntity.ok(characterRepository.save(updatedCharacter));
    }

    @PutMapping("/api/characters/{id}/stats")
    public ResponseEntity<Character> upgradeStats(@PathVariable Long id, @RequestBody UpgradeStatsRequest request) {
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        Optional<Character> optCharacter = characterRepository.findById(id);

        if(optCharacter.isEmpty()) return ResponseEntity.badRequest().build();
        if(!optCharacter.get().getUserId().equals(authId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

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
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        Optional<Character> optCharacter = characterRepository.findById(id);

        if(optCharacter.isEmpty()) return ResponseEntity.badRequest().build();
        if(!optCharacter.get().getUserId().equals(authId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        characterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}



