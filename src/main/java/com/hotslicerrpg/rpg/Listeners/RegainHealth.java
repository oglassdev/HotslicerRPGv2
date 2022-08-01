package com.hotslicerrpg.rpg.Listeners;

import com.hotslicerrpg.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegainHealth implements Listener {
    public RegainHealth(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player) || !Bukkit.getOnlinePlayers().contains((Player)event.getEntity())) return;
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.CUSTOM) return;
        event.setCancelled(true);
    }
}
