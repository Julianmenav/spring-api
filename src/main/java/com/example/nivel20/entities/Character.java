package com.example.nivel20.entities;

import com.example.nivel20.requests.UpgradeStatsRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "characters")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "character_type")
public abstract class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    @Column(name="character_type", insertable=false, updatable=false)
    private String characterType;
    private int level;
    private int experience;
    private int statPoints;
    private int health;
    private int healthLevelIncrease;
    private int strength;
    private int magic;
    private int stamina;

    public Character(){}
    public Character( Long userId, String name, String characterType, int health, int healthLevelIncrease, int strength, int magic, int stamina) {
        this.userId = userId;
        this.name = name;
        this.characterType = characterType;
        this.level = 1;
        this.experience = 0;
        this.statPoints = 0;
        this.health = health;
        this.healthLevelIncrease = healthLevelIncrease;
        this.strength = strength;
        this.magic = magic;
        this.stamina = stamina;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getStatPoints() {
        return statPoints;
    }

    public void setStatPoints(int statPoints) {
        this.statPoints = statPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacterType() {
        return characterType;
    }

    public void setCharacterType(String characterType) {
        this.characterType = characterType;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getMagic() {
        return magic;
    }

    public int getStamina() {
        return stamina;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void levelUp() {
        this.level += 1;
        this.statPoints += 3;
        this.health += this.healthLevelIncrease;
        this.experience = 0;
    }

    public void upgradeStats(UpgradeStatsRequest request ) {
        int strength = request.getStrengthUpgrade();
        int magic = request.getMagicUpgrade();
        int stamina = request.getStaminaUpgrade();
        int totalPoints = request.totalPoints();

        System.out.println(request);
        this.strength += strength;
        this.magic += magic;
        this.stamina += stamina;

        this.statPoints -= totalPoints;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", characterClass='" + characterType + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", strength=" + strength +
                ", magic=" + magic +
                ", resistance=" + stamina +
                '}';
    }
}
