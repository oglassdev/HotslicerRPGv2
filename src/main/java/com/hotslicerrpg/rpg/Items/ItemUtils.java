package com.hotslicerrpg.rpg.Items;

import com.hotslicerrpg.rpg.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.logging.Level;

public class ItemUtils {
    public static List<String> generateLore(final ModifiedItem item) {
        List<String> lore = new ArrayList<>();
        Item i = item.getBaseItem();
        HashMap<StatType,Double> stats = new HashMap<>();
        for (Stat stat : i.getStats()) {
            stats.put(stat.getType(),stats.getOrDefault(stat.getType(),0.0)+stat.getAmount());
        }
        for (Modifier mod : item.getModifiers()) {
            if (!(mod instanceof Modifier.StatModifier)) continue;
            Modifier.StatModifier statMod = (Modifier.StatModifier) mod;
            stats.put(statMod.getStat(),
                    statMod.modify(stats.getOrDefault(statMod.getStat(),0.0)));
        }
        stats.forEach((type,amount) ->
                lore.add(Utils.color(type.getColor() + type.getName() + ": &7" + amount)));

        lore.addAll(Arrays.asList(i.getLore()));
        if (i.getLore().length > 0 && i.getRarity() != Rarity.NONE) lore.add("");
        if (i.getRarity() != Rarity.NONE) {
            lore.add(Utils.color("&" + i.getRarity().color + "&l" + i.getRarity().name()));
        }
        return lore;
    }
    public static List<String> generateLore(final Item item) {
        return generateLore(new ModifiedItem(item));
    }
    public static List<String> generateLore(final ItemStack item) {
        return generateLore(new ModifiedItem(item));
    }
    public static void genAndApplyLore(final ModifiedItem item) {
        ItemMeta meta = item.getBukkitItem().getItemMeta();
        meta.setLore(generateLore(item));
        item.getBukkitItem().setItemMeta(meta);
    }
    public static Set<Stat> getModifiedStats(ModifiedItem item) {
        Item i = item.getBaseItem();
        HashMap<StatType,Double> stats = new HashMap<>();
        for (Stat stat : i.getStats()) {
            stats.put(stat.getType(),stats.getOrDefault(stat.getType(),0.0)+stat.getAmount());
        }
        for (Modifier mod : item.getModifiers()) {
            if (!(mod instanceof Modifier.StatModifier)) continue;
            Modifier.StatModifier statMod = (Modifier.StatModifier) mod;
            Utils.sendLog(Level.INFO,statMod.toString());
            stats.put(statMod.getStat(),
                    statMod.modify(stats.getOrDefault(statMod.getStat(),0.0)));
        }
        HashSet<Stat> set = new HashSet<>();
        stats.forEach((type,amount) -> set.add(new Stat(type,amount)));
        return set;
    }
}
