package com.hotslicerrpg.rpg.Items;

import com.hotslicerrpg.rpg.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ModifiedItem {
    private final Item baseItem;
    private final ItemStack bukkitItem;
    private final List<Modifier> modifiers = new ArrayList<>();
    public ModifiedItem(ItemStack item) {
        this.baseItem = Registry.getItem(item);
        this.bukkitItem = item;
        this.modifiers.addAll(Arrays.asList(Modifier.getModifiers(item)));
    }
    public ModifiedItem(Item item) {
        this.baseItem = item;
        this.bukkitItem = null;
    }
    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }
    public void applyModifiers() {
        Modifier.setModifiers(bukkitItem,modifiers.toArray(new Modifier[0]));
        updateItem();
    }
    public void updateItem() {
        ItemMeta meta = bukkitItem.getItemMeta();
        meta.setDisplayName(Utils.color(baseItem.getName()));
        meta.setLore(ItemUtils.generateLore(this));
        bukkitItem.setItemMeta(meta);
        NBTItem nbti = new NBTItem(bukkitItem);
        nbti.getOrCreateCompound("HotslicerRPG").setString("ID",baseItem.getID());
        if (baseItem.isUniqueItem()) nbti.getOrCreateCompound("HotslicerRPG").setString("UUID", UUID.randomUUID().toString());
    }

    public Item getBaseItem() {
        return baseItem;
    }
    public List<Modifier> getModifiers() {
        return modifiers;
    }
    public ItemStack getBukkitItem() {
        return bukkitItem;
    }
}
