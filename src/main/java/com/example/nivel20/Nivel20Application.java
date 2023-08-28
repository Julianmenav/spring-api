package com.example.nivel20;

import com.example.nivel20.entities.Character;
import com.example.nivel20.entities.Mage;
import com.example.nivel20.entities.Marksman;
import com.example.nivel20.entities.Warrior;
import com.example.nivel20.repositories.CharacterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Nivel20Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Nivel20Application.class, args);
		CharacterRepository characterRepo = context.getBean(CharacterRepository.class);


	}

}
