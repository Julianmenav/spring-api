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


		Character char1 = new Warrior(1L,"Zink");
		Character char2 = new Mage(1L, "judas");
		Character char3 = new Marksman(1L, "kixy");

//		CRUD
//		Create
		characterRepo.save(char1);
		characterRepo.save(char2);
		characterRepo.save(char3);
	}

}
