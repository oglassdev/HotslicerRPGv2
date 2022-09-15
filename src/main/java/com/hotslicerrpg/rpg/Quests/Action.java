package com.hotslicerrpg.rpg.Quests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

public abstract class Action<T extends PlayerEvent> implements Listener {
    private final int stage;
    private final Quest quest;
    public Action(int stage, Quest quest) {
        this.stage = stage;
        this.quest = quest;
    }
    public abstract boolean isValid(T event);
    public void accept(T event) {
        if (quest.getStage() != stage) return;
        if (isValid(event)) run(event);
    }
    protected abstract void run(T event);
    @EventHandler
    public void event(T event) {
        accept(event);
    }
}