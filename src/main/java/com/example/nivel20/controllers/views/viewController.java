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
        return "index";
    }

    @GetMapping("/dashboard")
    public String auth(Model model){

        //Auth check and save if new user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");

        Optional<User> optUser = userRepository.findByOauthId(oauthId);

        List<Character> characters = characterRepository.findByUserId(optUser.get().getId());

        model.addAttribute("info", authentication);
        model.addAttribute("characters", characters);
        return "dashboard";
    }



}
