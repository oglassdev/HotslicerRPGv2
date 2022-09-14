package com.hotslicerrpg.rpg.Items;

import org.bukkit.ChatColor;

public enum StatType {
    DEFENSE("Defense", ChatColor.GREEN),
    PROJECTILE_DEFENSE("Projectile Defense", ChatColor.DARK_GRAY),
    EXPLOSION_DEFENSE("Explosion Defense", ChatColor.RED),
    FIRE_DEFENSE("Fire Defense", ChatColor.GOLD),
    FALL_DEFENSE("Fall Defense", ChatColor.GRAY),

    CRIT_CHANCE("Crit Chance", ChatColor.BLUE),
    CRIT_DAMAGE("Crit Damage", ChatColor.RED),
    DAMAGE("Damage", ChatColor.RED),
    STRENGTH("Strength", ChatColor.RED),

    SPEED("Speed", ChatColor.AQUA),
    HEALTH("Health", ChatColor.RED),

    MINING_SPEED("Mining Speed",ChatColor.GOLD),
    MINING_LUCK("Mining Luck",ChatColor.LIGHT_PURPLE),
    BREAKING_POWER("Breaking Power",ChatColor.DARK_GRAY)
    ;

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
