package com.example.nivel20.entities;

import com.example.nivel20.enums.RegistrationProvider;
import jakarta.persistence.*;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Integer oauthId;
    @Enumerated(EnumType.STRING)
    @Column(name = "registration_provider")
    private RegistrationProvider registrationProvider;

    public User(){}

    public User( String username, Integer oauthId, RegistrationProvider registrationProvider) {
        this.username = username;
        this.oauthId = oauthId;
        this.registrationProvider = registrationProvider;
    }

    public User(OAuth2User oauthUser){
        this.username = oauthUser.getAttribute("login");
        this.oauthId = oauthUser.getAttribute("id");
        this.registrationProvider = RegistrationProvider.GITHUB;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getOauthId() {
        return oauthId;
    }

    public RegistrationProvider getRegistrationProvider() {
        return registrationProvider;
    }
}
