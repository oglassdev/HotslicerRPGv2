package com.hotslicerrpg.rpg;

import com.hotslicerrpg.rpg.Items.*;
import com.hotslicerrpg.rpg.Listeners.EntityDamage;
import com.hotslicerrpg.rpg.Mining.MinesManager;
import com.hotslicerrpg.rpg.Quests.QuestFinishListener;
import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import me.lokka30.treasury.api.common.service.ServiceRegistry;
import me.lokka30.treasury.api.economy.EconomyProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
    private static BukkitQuestsPlugin questsPlugin;
    private static EconomyProvider economy;

    @Override
    public void onEnable() {
        plugin = this;
        commandManager = new CommandManager();
        questsPlugin = (BukkitQuestsPlugin) Bukkit.getPluginManager().getPlugin("Quests");
        if (getServer().getPluginManager().getPlugin("Treasury") != null) {
            economy = ServiceRegistry.INSTANCE.serviceFor(EconomyProvider.class).get().get();
        }

        ClassLoader cl = plugin.getClass().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        new MobDrops(this);
        new EntityDamage(this);
        new QuestFinishListener(this);
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
    public static BukkitQuestsPlugin getQuestPlugin() { return questsPlugin; }
    public static EconomyProvider getEconomy() { return economy; }
}
