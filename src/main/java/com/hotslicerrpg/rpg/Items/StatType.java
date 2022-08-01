package com.hotslicerrpg.rpg.Items;

import org.bukkit.ChatColor;

public enum StatType {
    Defense("Defense", ChatColor.GREEN),
    ProjectileDefense("Projectile Defense", ChatColor.DARK_GRAY),
    ExplosionDefense("Explosion Defense", ChatColor.RED),
    FireDefense("Fire Defense", ChatColor.GOLD),
    FallDefense("Fall Defense", ChatColor.GRAY),

    CritChance("Crit Chance", ChatColor.BLUE),
    CritDamage("Crit Damage", ChatColor.RED),
    Damage("Damage", ChatColor.RED),
    Strength("Strength", ChatColor.RED),

    Speed("Speed", ChatColor.AQUA),
    Health("Health", ChatColor.RED),;

    private final String name;
    private final ChatColor color;
    StatType(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
    public String getName() {
        return name;
    }
}
