package com.example.nivel20.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MAGE")
public class Mage extends Character {
    public Mage(){}
    public Mage(String name) {
        super(name, "MAGE",6,1, 0, 3, 0);
    }


}
