package com.hotslicerrpg.rpg;

import com.hotslicerrpg.rpg.Entities.Entity;
import com.hotslicerrpg.rpg.Entities.MinesStalker;
import com.hotslicerrpg.rpg.Items.PlayerStats;
import com.hotslicerrpg.rpg.Listeners.EntityDamage;
import com.hotslicerrpg.rpg.Scripting.ScriptFile;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public final class Main extends JavaPlugin {
    private static Main plugin;
    private static CommandManager commandManager;

    @Override
    public void onEnable() {
        plugin = this;
        commandManager = new CommandManager();
        ClassLoader cl = plugin.getClass().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        new MobDrops(this);
        new EntityDamage(this);
        new ScriptFile(new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "Scripts" + File.separator + "Main.js"));

        getCommand("spawnentity").setExecutor((sender, command, s, strings) -> {
            Location loc = ((Player)sender).getLocation();
            Entity.spawnEntity(new MinesStalker(((Player)sender).getWorld()),((Player) sender).getLocation());
            EntityArmorStand stand = new EntityArmorStand(((CraftWorld)loc.getWorld()).getHandle());
            stand.setCustomName("stand0");
            stand.setCustomNameVisible(true);
            stand.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getPitch(),loc.getYaw());
            //stand.setInvisible(true);
            PacketPlayOutSpawnEntityLiving create = new PacketPlayOutSpawnEntityLiving(stand);
                EntityPlayer player = ((CraftPlayer)sender).getHandle();
                PlayerConnection connection = player.playerConnection;
                connection.sendPacket(create);
            return true;
        });
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

    }

    public static Main getPlugin() {
        return plugin;
    }
    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
