package com.hotslicerrpg.rpg.Entities;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;

import java.util.Map;

public enum Entity {
    MINES_STALKER("MinesStalker", EntityType.PIG_ZOMBIE, MinesStalker.class),
    ;

    private final Class<? extends net.minecraft.server.v1_8_R3.Entity> clazz;
    Entity(String name, EntityType type, Class<? extends net.minecraft.server.v1_8_R3.Entity> clazz) {
        register(clazz, name, type.getTypeId());
        this.clazz = clazz;
    }

    public Class<? extends net.minecraft.server.v1_8_R3.Entity> getEntityClass() {
        return clazz;
    }

    public static void spawnEntity(net.minecraft.server.v1_8_R3.Entity entity, Location loc) {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
        EntityManager.registerEntity(entity);
    }

    private static void register(Class clazz, String name, int id) {
        ((Map)NMSUtils.getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
        ((Map)NMSUtils.getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
        ((Map)NMSUtils.getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, id);
    }
}
