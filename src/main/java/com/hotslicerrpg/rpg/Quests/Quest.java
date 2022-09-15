package com.hotslicerrpg.rpg.Quests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Quest {
    private final QuestType type;
    private int stage = 0;
    private final UUID uuid;
    public Quest(QuestType type, Player player) {
        this.type = type;
        this.uuid = player.getUniqueId();
    }
    public int getStage() {
        return stage;
    }
    public void incrementStage() {
        setStage(stage+1);
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}