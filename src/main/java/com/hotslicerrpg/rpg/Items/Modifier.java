package com.hotslicerrpg.rpg.Items;

import com.google.gson.Gson;
import de.tr7zw.nbtapi.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Modifier {
    public abstract class AbilityModifier extends Modifier implements Listener {
        public abstract void run(Player player);
    }
    public static class StatModifier extends Modifier {
        private final StatType stat;
        private final Operation operation;
        private final double amount;
        public StatModifier(StatType stat, Operation operation, double amount) {
            this.stat = stat;
            this.operation = operation;
            this.amount = amount;
        }
        public double modify(double stat) {
            switch (operation) {
                case ADD:
                    stat += amount;
                    break;
                case SUBTRACT:
                    stat -= amount;
                    break;
                case MULTIPLY:
                    stat *= amount;
                    break;
                case DIVIDE:
                    stat /= amount;
                    break;
            }
            return stat;
        }

        public StatType getStat() {
            return stat;
        }

        @Override
        public String toString() {
            return "StatModifier{" +
                    "stat=" + stat +
                    ", operation=" + operation +
                    ", amount=" + amount +
                    '}';
        }
    }
    public enum Operation {
        ADD,SUBTRACT,MULTIPLY,DIVIDE
    }
    public static Modifier[] getModifiers(@Nonnull ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("HotslicerRPG")) return new Modifier[0];
        NBTCompound nbtc = nbtItem.getCompound("HotslicerRPG");
        NBTCompound mods = nbtc.getOrCreateCompound("Modifiers");
        List<Modifier> modifiers = new ArrayList<>();
        for (String key : mods.getKeys()) {
            modifiers.add(mods.getObject(key,Modifier.class));
        }
        return modifiers.toArray(new Modifier[0]);
    }
    public static void setModifiers(ItemStack item, Modifier[] modifiers) {
        NBTItem nbtItem = new NBTItem(item);
        NBTCompound nbtc = nbtItem.getOrCreateCompound("HotslicerRPG");
        nbtc.removeKey("Modifiers");
        NBTCompound mods = nbtc.getOrCreateCompound("Modifiers");
        for (Modifier modifier : modifiers) {
            mods.setObject(modifier.hashCode() + "", modifier);
        }
        item.setItemMeta(nbtItem.getItem().getItemMeta());
    }
}