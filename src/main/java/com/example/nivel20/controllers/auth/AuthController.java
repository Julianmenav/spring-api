package com.example.nivel20.controllers.auth;

import com.example.nivel20.entities.User;
import com.example.nivel20.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class AuthController {


    @GetMapping("/logout")
    public RedirectView logout() {
        SecurityContextHolder.clearContext();
        return new RedirectView("/");
    }

}
