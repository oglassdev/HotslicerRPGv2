package com.hotslicerrpg.rpg.Listeners;

import com.hotslicerrpg.rpg.Items.Registry;
import com.hotslicerrpg.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerFish implements Listener {
    public PlayerFish(Main main) {
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        Player p = event.getPlayer();
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Entity drop = event.getCaught();
            Location playerLoc = p.getEyeLocation();
            playerLoc.add(0,0.5,0);
            //if (drop instanceof Item) ((Item) drop).setPickupDelay(0);
            //drop.setVelocity(playerLoc.toVector().subtract(drop.getLocation().toVector()).normalize());
            ((Item) drop).setItemStack(getDrop(p));
            //event.getCaught().remove();
        }
    }

    private ItemStack getDrop(Player p) {
        float chance = (float) Math.random();
        //if (chance > 0.8) Registry.CELESTINE.getItem();
        return Registry.getItem("RAW_FISH").getItem();
    }
}