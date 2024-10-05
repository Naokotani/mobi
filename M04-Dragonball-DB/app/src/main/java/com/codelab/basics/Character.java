package com.codelab.basics;

import java.util.List;

public class Character {
    public final int id;
    public final String name;
    public final String race;
    public final String gender;
    public final String bio;
    public final int health;
    public final int attack;
    public final int defense;
    public final int kiRestoreSpeed;

    public Character(int id, String name, String race, String gender, String bio, int health, int attack,
                     int defense, int kiRestoreSpeed) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.gender = gender;
        this.bio = bio;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.kiRestoreSpeed = kiRestoreSpeed;
    }

}