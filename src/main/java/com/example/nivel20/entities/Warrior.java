package com.example.nivel20.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("WARRIOR")
public class Warrior extends Character {

    public Warrior(){}
    public Warrior(Long userId, String name) {
        super(userId, name, "WARRIOR", 8, 3, 2, 0, 1);
    }


}
