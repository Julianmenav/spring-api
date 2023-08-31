package com.example.nivel20.controllers.views;

import com.example.nivel20.entities.Character;
import com.example.nivel20.entities.User;
import com.example.nivel20.repositories.CharacterRepository;
import com.example.nivel20.repositories.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class viewController {

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;

    public viewController(CharacterRepository characterRepository, UserRepository userRepository) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model){
        //Auth Check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication.getPrincipal() instanceof OAuth2User)){
            model.addAttribute("user", null);
            model.addAttribute("characters", null);
            return "index";
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");

        Optional<User> optUser = userRepository.findByOauthId(oauthId);
        List<Character> characters = characterRepository.findByUserId(optUser.get().getId());

        model.addAttribute("user", oauthUser);
        model.addAttribute("characters", characters);
        return "index";
    }

    @GetMapping("/character/{id}")
    public String character(@PathVariable Long id, Model model){
        //Auth check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");
        Optional<User> authOptUser = userRepository.findByOauthId(oauthId);
        Long authId = authOptUser.get().getId();

        //Get character
        Optional<Character> optCharacter = characterRepository.findById(id);

        model.addAttribute("user", oauthUser);
        
        if(optCharacter.isEmpty() || !optCharacter.get().getUserId().equals(authId)){
            model.addAttribute("valid", false);
            return "character";
        }
        model.addAttribute("valid", true);
        model.addAttribute("character", optCharacter.get());
        return "character";
    }
}
