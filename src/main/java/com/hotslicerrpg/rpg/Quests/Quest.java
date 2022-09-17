package com.hotslicerrpg.rpg.Quests;

import org.bukkit.entity.Player;

public enum Quest {
    QUEST_NOT_FOUND("&4Quest Not Found!", new Reward(50));
    private final String name;
    private final Reward reward;
    Quest(String name, Reward reward) {
        this.name = name;
        this.reward = reward;
    }
    public String getName() {
        return name;
    }
    public Reward getReward() {
        return reward;
    }
}
