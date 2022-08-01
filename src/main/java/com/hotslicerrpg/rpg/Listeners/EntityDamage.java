package com.hotslicerrpg.rpg.Listeners;

import com.hotslicerrpg.rpg.Items.PlayerStats;
import com.hotslicerrpg.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    public EntityDamage(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) || !Bukkit.getOnlinePlayers().contains((Player)event.getEntity())) return;
        Player p = (Player) event.getEntity();
        PlayerStats stats = PlayerStats.getStats(p);
        stats.updateStats();
        if (event instanceof EntityDamageByEntityEvent) stats.damagePlayerByEntity(((EntityDamageByEntityEvent) event).getDamager(), event.getDamage() * 5);
        else stats.damagePlayer(event.getCause(), event.getDamage() * 5);
        event.setDamage(0.0000001);
    }
}
