package com.hotslicerrpg.rpg;

import com.hotslicerrpg.rpg.Entities.Entity;
import com.hotslicerrpg.rpg.Entities.MinesStalker;
import com.hotslicerrpg.rpg.Items.*;
import com.hotslicerrpg.rpg.Listeners.EntityDamage;
import com.hotslicerrpg.rpg.Mining.MinesManager;
import com.hotslicerrpg.rpg.Regions.PlayerMove;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    private static Main plugin;
    private static MinesManager minesManager;
    private static CommandManager commandManager;

    @Override
    public void onEnable() {
        plugin = this;
        commandManager = new CommandManager();
        ClassLoader cl = plugin.getClass().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        new MobDrops(this);
        new EntityDamage(this);
        new PlayerMove(this);
        //new ScriptFile(new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "Scripts" + File.separator + "Main.js"));
        List<Stat> stats = new ArrayList<>();
        stats.add(new Stat(StatType.MINING_SPEED,50));
        stats.add(new Stat(StatType.MINING_LUCK,10));
        stats.add(new Stat(StatType.BREAKING_POWER,1));
        Registry.addItem(new Item("DRILL_0", Material.PRISMARINE_SHARD,(short) 0,
                "&abad Drill", new String[0], Rarity.COMMON,stats.toArray(new Stat[0])));

        stats = new ArrayList<>();
        stats.add(new Stat(StatType.MINING_SPEED,200));
        stats.add(new Stat(StatType.MINING_LUCK,30));
        stats.add(new Stat(StatType.BREAKING_POWER,3));
        Registry.addItem(new Item("DRILL_1", Material.PRISMARINE_SHARD,(short) 0,
                "&bdecent Drill", new String[0], Rarity.UNCOMMON,stats.toArray(new Stat[0])));

        stats = new ArrayList<>();
        stats.add(new Stat(StatType.MINING_SPEED,500));
        stats.add(new Stat(StatType.MINING_LUCK,80));
        stats.add(new Stat(StatType.BREAKING_POWER,4));
        Registry.addItem(new Item("DRILL_2", Material.PRISMARINE_SHARD,(short) 0,
                "&bpoggers Drill", new String[0], Rarity.EPIC,stats.toArray(new Stat[0])));

        stats = new ArrayList<>();
        stats.add(new Stat(StatType.MINING_SPEED,1000));
        stats.add(new Stat(StatType.MINING_LUCK,100));
        stats.add(new Stat(StatType.BREAKING_POWER,5));
        Registry.addItem(new Item("DRILL_3", Material.PRISMARINE_SHARD,(short) 0,
                "&depik Drill", new String[0], Rarity.LEGENDARY,stats.toArray(new Stat[0])));

        getCommand("spawnentity").setExecutor((sender, command, s, strings) -> {
            if (!(sender instanceof Player)) return true;
            ((Player) sender).getInventory().addItem(Registry.getItem("DRILL_0").getItem(),
                    Registry.getItem("DRILL_1").getItem(),
                    Registry.getItem("DRILL_2").getItem(),
                    Registry.getItem("DRILL_3").getItem());
            return true;
        });
        minesManager = new MinesManager(this);
        new BukkitRunnable(){
            boolean b = false;
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerStats stats = PlayerStats.getStats(player);
                    if (stats == null) PlayerStats.initPlayer(player);
                    stats = PlayerStats.getStats(player);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,1200,10,false,false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,1200,0,false,false));
                    stats.updateStats();
                    if (b) stats.regenerateStats();
                    Utils.sendActionbar(player, Utils.color(stats.getActionbar()));
                    b = !b;
                }
            }
        }.runTaskTimer(plugin,10,10);
        /*Region.addRegion(
                new Region(
                        WGBukkit.getRegionManager(Bukkit.getWorld("spawn")).getRegion("mines"),
                        "Mines",
                        "hot.region.mines"));*/
    }

    @Override
    public void onDisable() {
        minesManager.replaceBlocks();
    }

    public static Main getPlugin() {
        return plugin;
    }
    public static CommandManager getCommandManager() {
        return commandManager;
    }
    public static MinesManager getMinesManager() { return minesManager; }
}
