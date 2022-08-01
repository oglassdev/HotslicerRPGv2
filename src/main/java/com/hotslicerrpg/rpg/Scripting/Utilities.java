package com.hotslicerrpg.rpg.Scripting;

import com.hotslicerrpg.rpg.Listeners.ClickListener;
import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.MobDrops;
import com.hotslicerrpg.rpg.Utils;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

public class Utilities {
    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }
    public static String color(String string) {
        return Utils.color(string);
    }
    public static void sendMessage(Player player, String message) {
        player.sendMessage(Utils.color(message));
    }
    public static void teleport(Entity entity, World world, double x, double y, double z, float yaw, float pitch) {
        entity.teleport(new Location(world,x,y,z,yaw,pitch));
    }
    public static void teleportForward(Player player, boolean playSound, int range) {
        for (; range >= 0; range--) {
            Block block = player.getTargetBlock((HashSet<Byte>) null, range);
            Material blockType = block.getType();
            Material aboveBlock = block.getLocation().add(0, 1, 0).getBlock().getType();
            if ((blockType.equals(Material.AIR) || blockType.equals(Material.WATER) || blockType.equals(Material.LAVA)) &&
                    (aboveBlock.equals(Material.AIR) || aboveBlock.equals(Material.WATER) || aboveBlock.equals(Material.LAVA))) {
                Location blockLoc = block.getLocation();
                float pitch = player.getEyeLocation().getPitch();
                float yaw = player.getEyeLocation().getYaw();
                blockLoc.add(0.5, 0, 0.5);
                blockLoc.setYaw(yaw);
                blockLoc.setPitch(pitch);
                player.teleport(blockLoc);
                break;
            }
        }
        if (playSound) player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 0.4F, 1F);
    }
    public static void registerCommand(CustomCommand cmd) {
        Utils.sendLog(Level.INFO,"Registered command" + cmd.getName());
        Main.getCommandManager().registerCommand(cmd.getName(),cmd);
    }
    public static void registerRightClickEvent(Consumer<PlayerInteractEvent> consumer) {
        ClickListener.addRightClickAction(consumer);
    }
    public static void registerLeftClickEvent(Consumer<PlayerInteractEvent> consumer) {
        ClickListener.addRightClickAction(consumer);
    }
    public static void runLater(long ticks, BukkitRunnable runnable) {
        runnable.runTaskLater(Main.getPlugin(),ticks);
    }
    public static Entity spawnEntity(String name, Location location) {
        return location.getWorld().spawnEntity(location, EntityType.valueOf(name));
    }
    public static Vector newVector(double x, double y, double z) {
        return new Vector(x,y,z);
    }
    public static void softMove(long time, Entity entity, Location location) {
        Location entLoc = entity.getLocation();
        new BukkitRunnable() {
            long i = 0;
            @Override
            public void run() {
                if (i >= time) {
                    entity.teleport(location);
                    cancel();
                    return;
                }
                double x = entLoc.getX() + (location.getX()-entLoc.getX())*((i+0.0)/time);
                double y = entLoc.getY() + (location.getY()-entLoc.getY())*((i+0.0)/time);
                double z = entLoc.getZ() + (location.getZ()-entLoc.getZ())*((i+0.0)/time);
                entity.teleport(new Location(entity.getWorld(),x,y,z));
                i++;
            }
        }.runTaskTimer(Main.getPlugin(),0,1);
    }
    public static void setAI(Entity bukkitEntity, boolean ai) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", ai ? 0 : 1);
        nmsEntity.f(tag);
    }
    public static void setMobDrops(LivingEntity entity, Map<ItemStack,Double> itemMap) {
        MobDrops.setCustomDrops(entity,itemMap);
    }
    public static void setKnockbackResistance(LivingEntity entity, double resistance) {
        if (resistance > 1) resistance = 1;
        if (resistance < 0) resistance = 0;
        ((EntityLiving) ((CraftEntity) entity).getHandle()).getAttributeInstance(GenericAttributes.c).setValue(resistance);
    }
    public static void setMovementSpeed(LivingEntity entity, double speed) {
        ((EntityLiving) ((CraftEntity) entity).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
    }
}