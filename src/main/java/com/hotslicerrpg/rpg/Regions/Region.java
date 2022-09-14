package com.hotslicerrpg.rpg.Regions;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class Region {
    private static final HashMap<String,Region> regions = new HashMap<>();

    public static void addRegion(Region region) {
        regions.put(region.getName(),region);
    }
    public static boolean containsRegion(ProtectedRegion region) {
        for (Region reg : regions.values()) {
            if (reg.getRegion().equals(region)) return true;
        }
        return false;
    }
    public static Region getRegion(Player player) {
        for (Region reg : regions.values()) {
            if (reg.inRegion(player)) return reg;
        }
        return null;
    }
    public static Region getRegion(ProtectedRegion region) {
        for (Region reg : regions.values()) {
            if (reg.getRegion().equals(region)) return reg;
        }
        return null;
    }
    public static Region getRegion(Location loc) {
        for (Region reg : regions.values()) {
            if (reg.inRegion(loc)) return reg;
        }
        return null;
    }
    public static Collection<Region> getRegions() {
        return regions.values();
    }
    public static Region getRegion(String name) {
        return regions.getOrDefault(name,null);
    }

    public ProtectedRegion getRegion() {
        return region;
    }
    public String getName() {
        return name;
    }
    private final ProtectedRegion region;
    private final String name;
    private final String permission;
    public Region(ProtectedRegion region, String name, String permission) {
        this.region = region;
        this.name = name;
        this.permission = permission;
    }
    public boolean inRegion(Player player) {
        return WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).getRegions().contains(region);
    }
    public boolean inRegion(Location loc) {
        return WGBukkit.getRegionManager(loc.getWorld()).getApplicableRegions(loc).getRegions().contains(region);
    }
    public String getPermission() {
        return this.permission;
    }
}
