package com.hotslicerrpg.rpg.Items;

public class Stat {
    private final StatType type;
    private double amount;

    public Stat(StatType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public StatType getType() {
        return type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    public String getName(boolean color) {
        if (color) return type.getColor() + type.getName();
        else return type.getName();
    }
}