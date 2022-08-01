package com.hotslicerrpg.rpg.Entities;

import net.minecraft.server.v1_8_R3.Entity;

import java.util.HashSet;
import java.util.Set;

public class EntityManager {
    public static final Set<Entity> entities = new HashSet<>();
    public static void registerEntity(Entity entity) {
        boolean valid = false;
        for (com.hotslicerrpg.rpg.Entities.Entity e : com.hotslicerrpg.rpg.Entities.Entity.values()) {
            if (e.getEntityClass().equals(entity.getClass())) {
                valid = true;
                break;
            }
        }
        if (!valid) throw new IllegalArgumentException("Entity must be a custom entity!");
        entities.add(entity);
    }
    public static boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }
    public static boolean containsEntity(int id) {
        for (Entity e : entities) {
            if (e.getId() == id) return true;
        }
        return false;
    }
    public static Entity getEntity(int id) {
        for (Entity e : entities) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}
