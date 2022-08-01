package com.hotslicerrpg.rpg.Listeners;

import com.hotslicerrpg.rpg.Items.PlayerStats;
import com.hotslicerrpg.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ClickListener implements Listener {
    private static final Set<Consumer<PlayerInteractEvent>> rightClickActions = new HashSet<>();
    private static final Set<Consumer<PlayerInteractEvent>> leftClickActions = new HashSet<>();
    public ClickListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            rightClickActions.forEach(consumer -> consumer.accept(event));
        } else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            leftClickActions.forEach(consumer -> consumer.accept(event));
        }
        PlayerStats stats = PlayerStats.getStats(event.getPlayer());
        if (stats == null) return;
        stats.updateStats();
    }
    public static void addLeftClickAction(Consumer<PlayerInteractEvent> consumer) {
        rightClickActions.add(consumer);
    }
    public static void addRightClickAction(Consumer<PlayerInteractEvent> consumer) {
        rightClickActions.add(consumer);
    }
}
