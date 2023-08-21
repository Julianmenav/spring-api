package com.example.nivel20.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MARKSMAN")
public class Marksman extends Character {
    public Marksman(){}
    public Marksman(String name) {
        super(name, "MARKSMAN", 6, 2, 1, 0, 2);
    }

}
