package com.hotslicerrpg.rpg.Items;

import com.hotslicerrpg.rpg.Utils;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModItem {
    private static final String STAT_DISPLAY = "%type%: %coloredsign%%amount%";

    private final ItemStack item;
    private final NBTItem nbti;
    private final NBTCompound comp;
    private final NBTCompound modifiers;
    private final NBTCompound enchants;
    public ModItem(ItemStack item) {
        this.item = item;
        this.nbti = new NBTItem(item);
        this.comp = this.nbti.getOrCreateCompound("HotslicerRPG");
        this.modifiers = this.comp.getOrCreateCompound("Modifiers");
        this.enchants = this.modifiers.getOrCreateCompound("Enchants");
    }

    public String getID() {
        return comp.getString("ID");
    }

    public int getCarrotBookUses() {
        if (!modifiers.hasKey("CarrotBook")) return 0;
        return modifiers.getInteger("Carrot");
    }
    public void addCarrotBookUse() {
        if (!modifiers.hasKey("Carrot")) modifiers.setInteger("Carrot",1);
        else modifiers.setInteger("Carrot",modifiers.getInteger("Carrot") + 1);
    }

    public void addEnchant(String name, int level) {
        if (!enchants.hasKey(name)) enchants.setInteger(name,level);
        else {
            enchants.setInteger(name,addEnchantLevel(enchants.getInteger(name),level));
        }
    }
    public int getEnchantLevel(String name) {
        if (!enchants.hasKey(name)) return 0;
        return enchants.getInteger(name);
    }

    public void applyChanges() {
        item.setItemMeta(nbti.getItem().getItemMeta());
    }

    public void updateLore() {  
        List<String> lore = new ArrayList<>();
        for (Stat stat : Registry.getItem(getID()).getStats()) {
            String string = STAT_DISPLAY;
            string = string.replace("%type%",stat.getName(true));
            String str = "&7";
            if (stat.getAmount() > 0) str = "&a+";
            else if (stat.getAmount() < 0) str = "&c-";
            string = string.replace("%coloredsign%",str);
            string = string.replace("%amount%",str + stat.getAmount());
            lore.add(Utils.color(string));
        }
    }

    private static int addEnchantLevel(int level1, int level2) {
        if (level1 == level2) return level1 + 1;
        return Math.max(level1, level2);
    }
}
