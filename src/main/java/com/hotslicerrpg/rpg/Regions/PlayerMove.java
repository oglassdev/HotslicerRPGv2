package com.hotslicerrpg.rpg.Regions;

import com.hotslicerrpg.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class PlayerMove {
    private final Map<UUID, Location> lastLocations = new HashMap<>();
    private static final Map<UUID, HashSet<String>> disallowedRegions = new HashMap<>();
    public PlayerMove(Main plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin,() -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID uuid = p.getUniqueId();
                Location lastLoc = lastLocations.get(uuid);
                for (String name : disallowedRegions.get(uuid)) {
                    Region region = Region.getRegion(name);
                    if (region == null) continue;
                    if (region.inRegion(p)) p.teleport(lastLoc);
                }
                lastLocations.put(uuid,p.getLocation());
            }
        },0,10);
    }
    public static void getR
}
