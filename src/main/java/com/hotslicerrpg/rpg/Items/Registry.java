package com.hotslicerrpg.rpg.Items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Registry {
    private static final HashMap<String,Item> items = new HashMap<>();
    public static void addItem(Item item) {
        items.put(item.getID(),item);
    }
    public static boolean hasItem(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        if (!nbti.hasKey("HotslicerRPG")) return false;
        return items.containsKey(nbti.getOrCreateCompound("HotslicerRPG").getString("ID"));
    }
    public static boolean hasItem(String id) {
        return items.containsKey(id);
    }
    public static Item getItem(ItemStack stack) {
        NBTItem nbti = new NBTItem(stack);
        if (!nbti.hasKey("HotslicerRPG")) return null;
        return items.getOrDefault(nbti.getOrCreateCompound("HotslicerRPG").getString("ID"),null);
    }
    public static Item getItem(String id) {
        return items.getOrDefault(id,null);
    }
}
