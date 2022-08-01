package com.hotslicerrpg.rpg.Items;

import com.hotslicerrpg.rpg.Utils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {
    private final String ID;
    private final Material material;
    private final short dataValue;

    private final String name;
    private final String[] lore;

    private final Rarity rarity;
    private final Stat[] stats;
    public Item(String ID, Material material, short dataValue,
                String name, String[] lore, Rarity rarity, Stat[] stats) {
        this.ID = ID;
        this.material = material;
        this.dataValue = dataValue;
        this.name = name;
        this.lore = lore;
        this.rarity = rarity;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }
    public String getID() {
        return ID;
    }
    public Material getMaterial() {
        return material;
    }
    public Rarity getRarity() {
        return rarity;
    }
    public short getDataValue() {
        return dataValue;
    }
    public Stat[] getStats() {
        return stats;
    }
    public String[] getLore() {
        return lore;
    }

    public ItemStack getItem() {
        ItemStack stack = new ItemStack(material,1,dataValue);
        ItemMeta meta = stack.getItemMeta();
        String displayName = name;
        List<String> list = new ArrayList<>();
        if (stats.length > 0) {
            for (Stat stat : stats) {
                list.add(Utils.color(stat.getName(true) + ": &7" + stat.getAmount()));
            }
        }
        list.addAll(Arrays.asList(lore));
        if (rarity != Rarity.NONE) {
            list.add("");
            list.add(Utils.color(rarity.toString()));
            displayName = Utils.color('&' + rarity.color + name);
        }
        meta.setDisplayName(Utils.color(displayName));
        meta.setLore(list);
        stack.setItemMeta(meta);
        NBTItem nbti = new NBTItem(stack);
        nbti.getOrCreateCompound("HotslicerRPG").setString("ID",ID);
        return nbti.getItem();
    }

    public static Builder builder(String ID, Material material) {
        return new Builder(ID, material);
    }
    public static class Builder {
        private final String ID;
        private final Material material;
        private final List<String> lore = new ArrayList<>();
        private short dataValue = 0;
        private String name = null;
        private Rarity rarity = Rarity.NONE;
        private List<Stat> stats = new ArrayList<>();
        public Builder(String ID, Material material) {
            this.ID = ID;
            this.material = material;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder addLoreLine(String line) {
            lore.add(line);
            return this;
        }
        public Builder setRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }
        public Builder addStat(StatType type, double amount) {
            stats.add(new Stat(type,amount));
            return this;
        }
        public Builder setDataValue(short value) {
            this.dataValue = value;
            return this;
        }
        public Item build() {
            return new Item(ID, material,dataValue,name,lore.toArray(new String[0]),rarity,stats.toArray(new Stat[0]));
        }
    }
}
