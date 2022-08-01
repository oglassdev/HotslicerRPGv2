package com.hotslicerrpg.rpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MobDrops implements Listener {
    private static final Map<Entity, Map<ItemStack,Double>> customDrops = new HashMap<>();
    public MobDrops(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!customDrops.containsKey(event.getEntity())) return;
        event.getDrops().clear();
        if (customDrops.get(event.getEntity()).size() == 0) {
            customDrops.remove(event.getEntity());
            return;
        }
        customDrops.get(event.getEntity()).forEach((item,chance) -> {
            if (Math.random() <= chance) event.getDrops().add(item);
        });
        customDrops.remove(event.getEntity());
    }
    public static void setCustomDrops(LivingEntity entity, Map<ItemStack,Double> drops) {
        if (entity.isDead()) return;
        customDrops.put(entity,drops);
    }
}
