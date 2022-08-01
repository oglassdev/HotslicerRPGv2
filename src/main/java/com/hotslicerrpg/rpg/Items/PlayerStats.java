package com.hotslicerrpg.rpg.Items;

import com.google.common.collect.Lists;
import com.hotslicerrpg.rpg.Utils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

enum StatSource { HelmetStat,ChestplateStat,LeggingsStat,BootsStat,MainHand,Default,Admin }

public class PlayerStats {
    private static final HashMap<Player, PlayerStats> map = new HashMap<>();
    public static PlayerStats getStats(Player player) {
        return map.getOrDefault(player, null);
    }
    public static void initPlayer(Player player) {
        PlayerStats stats = new PlayerStats(player);
        stats.updateStats();
        map.put(player, stats);
    }

    private final HashMap<StatType, HashMap<StatSource, Double>> statMap = new HashMap<>();
    private double health = 100;

    private final Player player;
    private String actionbar = "";
    private long actionbarExpire;

    private long damageCooldown = System.currentTimeMillis();

    private PlayerStats(Player player) {
        this.player = player;
        initDefaultStats();
    }

    private void initDefaultStats() {
        for (StatType type : StatType.values()) {
            if (!statMap.containsKey(type) && type != StatType.Damage) statMap.put(type, new HashMap<>());
        }
        setDefault();
        updateStats();
        health = getStat(StatType.Health);
    }

    private Set<Stat> getItemStats(ItemStack item) {
        if (item == null || item.getType() == Material.AIR || item.getItemMeta() == null) return new HashSet<>();
        if (!Registry.hasItem(item)) return new HashSet<>();
        return new HashSet<>(Arrays.asList(Registry.getItem(item).getStats()));
    }

    public void updateStats() {
        double maxHealth = getStat(StatType.Health)/5;
        if (maxHealth > 40) maxHealth = 40;
        player.setMaxHealth(maxHealth);

        float vanillaHealth = (float) (this.health/5);
        if (vanillaHealth > maxHealth) vanillaHealth = (float) maxHealth;
        if (vanillaHealth < 1) vanillaHealth = 0.5f;
        player.setHealth(vanillaHealth);

        HashMap<StatSource, Set<Stat>> playerEquipment = new HashMap<>();
        if (!player.isFlying()) player.setWalkSpeed((float) (0.2 * getStat(StatType.Speed)/100));

        Set<Stat> s0 = getItemStats(player.getEquipment().getBoots());
        playerEquipment.put(StatSource.BootsStat, s0);
        Set<Stat> s1 = getItemStats(player.getEquipment().getLeggings());
        playerEquipment.put(StatSource.LeggingsStat, s1);
        Set<Stat> s2 = getItemStats(player.getEquipment().getChestplate());
        playerEquipment.put(StatSource.ChestplateStat, s2);
        Set<Stat> s3 = getItemStats(player.getEquipment().getHelmet());
        playerEquipment.put(StatSource.HelmetStat, s3);
        Set<Stat> s4 = getItemStats(player.getEquipment().getItemInHand());
        playerEquipment.put(StatSource.MainHand, s4);

        playerEquipment.forEach((source,stats) -> {
            if (stats == null) {
                for (StatType type : StatType.values()) {
                    if (type != StatType.Damage) statMap.get(type).put(source, 0.0);
                }
            } else {
                HashSet<StatType> types = new HashSet<>();
                for (Stat stat : stats) {
                    types.add(stat.getType());
                    if (stat.getType() != StatType.Damage) statMap.get(stat.getType()).put(source, stat.getAmount());
                }
                for (StatType type : StatType.values()) {
                    if (!types.contains(type) && type != StatType.Damage) statMap.get(type).put(source, 0.0);
                }
            }
        });

        if (System.currentTimeMillis() >= actionbarExpire) actionbar = "&c" + (int) health + "/" + getStat(StatType.Health) + " ♥     " + "&a" + (int) getStat(StatType.Defense) + " ✚     ";
    }

    public double getStat(StatType type) {
        double amount = 0;
        for (double d : statMap.get(type).values()) { amount+=d; }
        if (type == StatType.Speed && amount > 200) return 200;
        return amount;
    }

    public void setDefaultStat(StatType type, double amount) {
        statMap.get(type).put(StatSource.Default, amount);
    }

    public void setAdminStat(StatType type, double amount) {
        statMap.get(type).put(StatSource.Admin, amount);
    }

    public void setDefault() {
        statMap.get(StatType.Defense).put(StatSource.Default, 0.0);
        statMap.get(StatType.ExplosionDefense).put(StatSource.Default, 0.0);
        statMap.get(StatType.FallDefense).put(StatSource.Default, 0.0);
        statMap.get(StatType.ProjectileDefense).put(StatSource.Default, 0.0);
        statMap.get(StatType.FireDefense).put(StatSource.Default, 0.0);
        statMap.get(StatType.Health).put(StatSource.Default, 100.0);
        statMap.get(StatType.CritChance).put(StatSource.Default, 30.0);
        statMap.get(StatType.CritDamage).put(StatSource.Default, 100.0);
        statMap.get(StatType.Speed).put(StatSource.Default, 100.0);
        statMap.get(StatType.Strength).put(StatSource.Default, 0.0);
    }

    public void resetAdmin() {
        for (HashMap<StatSource, Double> map : statMap.values()) {
            map.remove(StatSource.Admin);
        }
    }

    public void setActionBar(double seconds, String text) {
        actionbar = text;
        actionbarExpire = ((long) (seconds * 1000)) + System.currentTimeMillis();
    }

    public String getActionbar() {
        return actionbar;
    }

    public void regenerateStats() {
        double maxHealth = getStat(StatType.Health);
        if (health > maxHealth);
        else if (health >= maxHealth-maxHealth*0.05) health = maxHealth;
        else health+=maxHealth*0.05;
    }

    public void damagePlayer(EntityDamageEvent.DamageCause cause, double damage) {
        if (damageCooldown > System.currentTimeMillis()) return;
        String strCause;
        switch (cause) {
            case ENTITY_EXPLOSION:
            case BLOCK_EXPLOSION:
                damage /= getStat(StatType.ExplosionDefense) / 100 + 1;
                strCause = " was exploded.";
            case PROJECTILE: {
                damage /= getStat(StatType.ProjectileDefense) / 100 + 1;
                strCause = " was shot.";
            }
            case FIRE_TICK:
            case FIRE:
            case LAVA:
                damage /= getStat(StatType.FireDefense) / 100 + 1;
                strCause = " burnt to death.";
            case FALL:
                damage /= getStat(StatType.FallDefense) + getStat(StatType.Defense) / 200 + 1;
                strCause = " fell to death.";
            default:
                damage /= getStat(StatType.Defense) / 100 + 1;
                strCause = " died.";
        }
        damageCooldown = System.currentTimeMillis() + 500;
        if (health - damage <= 0) killPlayer(strCause, true);
        else health-=damage;
    }

    public void damagePlayerByEntity(Entity entity, double damage) {
        if (damageCooldown > System.currentTimeMillis()) return;
        if (health - damage <= 0) killPlayer(entity, true);
        else {
            health-=damage;
        }
        damageCooldown = System.currentTimeMillis() + 500;
    }

    public void killPlayer(String cause, boolean announce) {
        player.teleport(player.getWorld().getSpawnLocation());
        player.setFallDistance(0f);
        player.setFireTicks(0);
        player.setVelocity(new Vector(0,0,0));
        player.setFoodLevel(20);
        player.setSaturation(10);
        health = getStat(StatType.Health);

        if (!announce) return;
        for (Player p : player.getWorld().getPlayers()) {
            if (p != player) p.sendMessage(Utils.color("&c" + player.getName() + cause));
            else p.sendMessage(Utils.color("&c" + player.getName() + cause));
        }
    }

    public void killPlayer(Entity entity, boolean announce) {
        player.teleport(player.getWorld().getSpawnLocation());
        player.setFallDistance(0f);
        player.setFireTicks(0);
        player.setVelocity(new Vector(0,0,0));
        player.setFoodLevel(20);
        player.setSaturation(10);
        health = getStat(StatType.Health);

        if (!announce) return;
        String str = entity.getCustomName();
        if (str == null) str = entity.getName().toLowerCase();
        for (Player p : player.getWorld().getPlayers()) {
            if (p != player) p.sendMessage(Utils.color("&c" + player.getName() + " was killed by " + str));
            else p.sendMessage(Utils.color("&c" + player.getName() + " was killed by " + str));
        }
    }
}