package com.hotslicerrpg.rpg.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;

public class AnimationPacket extends AbstractPacket {
    public static final PacketType TYPE =
            PacketType.Play.Server.ANIMATION;

    public AnimationPacket() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        setState((byte) 0);
    }
    /**
     * @param entity Block location.
     */
    public AnimationPacket(int state, Entity entity) {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        setState(state);
        setEntity(entity);
    }

    public AnimationPacket(PacketContainer packet) {
        super(packet, TYPE);
    }
    public void setState(int state) {
        handle.getIntegers().write(1,state);
    }
    public void setEntity(Entity entity) {
        handle.getIntegers().write(0,entity.getEntityId());
    }
}