package com.hotslicerrpg.rpg.Listeners;

import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.Quests.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinListener implements Listener {
    public JoinListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        QuestManager.initPlayer(event.getPlayer());
    }
}
