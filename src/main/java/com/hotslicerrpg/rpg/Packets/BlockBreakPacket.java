package com.hotslicerrpg.rpg.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.World;

public class BlockBreakPacket extends AbstractPacket {
    public static final PacketType TYPE =
            PacketType.Play.Server.BLOCK_BREAK_ANIMATION;

    public BlockBreakPacket() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    /**
     * @param location Block location.
     * @param state Animation state.
     */
    public BlockBreakPacket(Location location, int state) {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        handle.getIntegers().write(0,location.hashCode());
        setLocation(location);
        setState(state);
    }

    /**
     * @param location Block location.
     * @param state Animation state.
     */
    public BlockBreakPacket(BlockPosition location, int state) {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        setLocation(location);
        setState(state);
    }

    public BlockBreakPacket(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve location.
     *
     * @return The current location
     */
    public Location getLocation(World world) {
        return handle.getBlockPositionModifier().read(0).toLocation(world);
    }

    /**
     * Set Location.
     *
     * @param location - new value as Bukkit location.
     */
    public void setLocation(Location location) {
        handle.getBlockPositionModifier().write(0, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    /**
     * Set State.
     *
     * @param location - new value.
     */
    public void setLocation(BlockPosition location) {
        handle.getBlockPositionModifier().write(0, location);
    }

    /**
     * Retrieve state.
     *
     * @return The current State
     */
    public int getState() {
        return handle.getIntegers().read(1);
    }

    /**
     * Set State.
     *
     * @param state - new value.
     */
    public void setState(int state) {
        handle.getIntegers().write(1, state);
    }
}