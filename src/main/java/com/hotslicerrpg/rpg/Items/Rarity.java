package com.hotslicerrpg.rpg.Items;

public enum Rarity {
    NONE('f'),
    COMMON('7'),
    UNCOMMON('a'),
    RARE('9'),
    EPIC('5'),
    LEGENDARY('e');

    public final char color;
    Rarity(char color) {
        this.color = color;
    }
    public String toString() {
        return "&" + this.color + name();
    }
}

