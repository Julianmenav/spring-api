package com.example.nivel20.controllers.auth;

import com.example.nivel20.entities.User;
import com.example.nivel20.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepository;

    public AuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event){
        //Auth check and save if is a new user
        Authentication authentication = event.getAuthentication();

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Integer oauthId = oauthUser.getAttribute("id");

        Optional<User> optUser = userRepository.findByOauthId(oauthId);
        User user = optUser.orElseGet(() -> new User(oauthUser));
        userRepository.save(user);
    }

}
