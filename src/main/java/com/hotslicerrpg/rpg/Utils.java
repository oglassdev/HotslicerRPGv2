package com.hotslicerrpg.rpg;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.fusesource.jansi.Ansi;

import java.util.logging.Level;

public class Utils {
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&',input);
    }
    public static void sendLog(Level level, String input) {
        Ansi.Color color = Ansi.Color.CYAN;
        if (level == Level.SEVERE || level == Level.WARNING) color = Ansi.Color.RED;

        Bukkit.getLogger().log(level, Ansi.ansi().fg(Ansi.Color.RED).toString() +
                "[" + Ansi.ansi().fg(Ansi.Color.YELLOW).toString()
                + Main.getPlugin().getName() +  Ansi.ansi().fg(Ansi.Color.RED).toString() +
                "] " + Ansi.ansi().fg(color).toString() + input + Ansi.ansi().fg(Ansi.Color.DEFAULT).toString());
    }
    public static double getDamageReduced(Player player) {
        ItemStack boots = player.getEquipment().getBoots();
        ItemStack helmet = player.getEquipment().getHelmet();
        ItemStack chest = player.getEquipment().getChestplate();
        ItemStack pants = player.getEquipment().getLeggings();

        double reduction = 0.0;
        if (helmet != null) {
            if (helmet.getType() == Material.LEATHER_HELMET) reduction += 0.04;
            else if (helmet.getType() == Material.GOLD_HELMET) reduction += 0.08;
            else if (helmet.getType() == Material.CHAINMAIL_HELMET) reduction += 0.08;
            else if (helmet.getType() == Material.IRON_HELMET) reduction += 0.08;
            else if (helmet.getType() == Material.DIAMOND_HELMET) reduction += 0.12;

            if (helmet.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) reduction += 0.015 * helmet.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        if (boots != null) {
            if (boots.getType() == Material.LEATHER_BOOTS) reduction += 0.04;
            else if (boots.getType() == Material.GOLD_BOOTS) reduction += 0.04;
            else if (boots.getType() == Material.CHAINMAIL_BOOTS) reduction += 0.04;
            else if (boots.getType() == Material.IRON_BOOTS) reduction += 0.08;
            else if (boots.getType() == Material.DIAMOND_BOOTS) reduction += 0.12;

            if (boots.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) reduction += 0.015 * boots.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        if (pants != null) {
            if (pants.getType() == Material.LEATHER_LEGGINGS) reduction += 0.08;
            else if (pants.getType() == Material.GOLD_LEGGINGS) reduction += 0.12;
            else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) reduction += 0.16;
            else if (pants.getType() == Material.IRON_LEGGINGS) reduction += 0.20;
            else if (pants.getType() == Material.DIAMOND_LEGGINGS) reduction += 0.24;

            if (pants.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) reduction += 0.015 * pants.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        if (chest != null) {
            if (chest.getType() == Material.LEATHER_CHESTPLATE) reduction += 0.12;
            else if (chest.getType() == Material.GOLD_CHESTPLATE) reduction += 0.20;
            else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) reduction += 0.20;
            else if (chest.getType() == Material.IRON_CHESTPLATE) reduction += 0.24;
            else if (chest.getType() == Material.DIAMOND_CHESTPLATE) reduction += 0.32;

            if (chest.getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) reduction += 0.015 * chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        return reduction;
    }
    public static void sendActionbar(Player player, String text) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
