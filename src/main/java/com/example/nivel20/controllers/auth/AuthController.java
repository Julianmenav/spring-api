package com.example.nivel20.controllers.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class AuthController {


    @GetMapping("/logout")
    public RedirectView logout() {
        SecurityContextHolder.clearContext();
        return new RedirectView("/");
    }

}
