package com.hotslicerrpg.rpg.Mining;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.hotslicerrpg.rpg.Items.PlayerStats;
import com.hotslicerrpg.rpg.Items.StatType;
import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.Packets.AnimationPacket;
import com.hotslicerrpg.rpg.Packets.BlockBreakPacket;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MinesManager {
    private final Main plugin;
    private final HashMap<Player,Location> blocks = new HashMap<>();
    private final HashMap<Location,CustomBlock> brokenBlocks  = new HashMap<>();
    public MinesManager(Main plugin) {
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(
            new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    Player player = event.getPlayer();
                    PacketContainer pck = event.getPacket();
                    EnumWrappers.PlayerDigType status = event.getPacket().getPlayerDigTypes().read(0);

                    if (player.getGameMode().equals(GameMode.CREATIVE)) return;
                    Location loc = pck.getBlockPositionModifier().readSafely(0).toLocation(player.getWorld());
                    if (status == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                        if (blocks.containsValue(loc)) return;
                        blocks.put(player,loc);
                        animate(player,loc,0);
                    }
                    else if (status == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK || status == EnumWrappers.PlayerDigType.SWAP_HELD_ITEMS) {
                        blocks.remove(player,loc);
                        sendMiningPacket(player, loc, 10);
                    }
                }
            }
        );
    }
    public void animate(Player player, Location loc, int state) {
        if (!blocks.containsKey(player) || !blocks.get(player).equals(loc)) return;
        if (state > 9) {
            breakBlock(player,loc);
        }
        CustomBlock block = CustomBlock.getBlock(loc);
        //Utils.sendLog(Level.INFO, String.valueOf(block));
        if (block == null) return;

        PlayerStats stats = PlayerStats.getStats(player);
        if (stats.getStat(StatType.BREAKING_POWER) < block.getRequiredPower()) return;
        sendMiningPacket(player,loc,state);

        double time = block.getHardness()/stats.getStat(StatType.MINING_SPEED);
        if (time < 1) time = 1;
        Bukkit.getScheduler().runTaskLater(plugin, () -> animate(player, loc, state + 1),(int)time);
    }
    public void sendMiningPacket(Player miner, Location location, int state) {
        //Utils.sendLog(Level.INFO, miner.getDisplayName() + " : " + location.toString() + " : " + state);
        if (state < 0 || state > 10) {
            return;
        }
        BlockBreakPacket packet = new BlockBreakPacket(location, state);
        AnimationPacket animation = new AnimationPacket((byte) 0,miner);
        for (Player p : location.getWorld().getPlayers()) {
            packet.sendPacket(p);
            if (p != miner) animation.sendPacket(p);
        }
    }
    public void breakBlock(Player player, Location location) {
        sendMiningPacket(player,location,10);
        blocks.remove(player);
        CustomBlock block = CustomBlock.getBlock(location);
        brokenBlocks.put(location,block);
        location.getBlock().setType(block.getReplaceMaterial());
        location.getBlock().setData(block.getReplaceData());
        Bukkit.getScheduler().runTaskLater(plugin,() -> {
            replaceBlock(location,block);
            brokenBlocks.remove(location);
        },block.getHardness()/2);
    }
    public void replaceBlocks() {
        brokenBlocks.forEach(((location, block) -> {
            replaceBlocks();
        }));
        brokenBlocks.clear();
    }
    public void replaceBlock(Location location, CustomBlock block) {
        location.getBlock().setType(block.getMaterial());
        location.getBlock().setData(block.getData());
    }
}
