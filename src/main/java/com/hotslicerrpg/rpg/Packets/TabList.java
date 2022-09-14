package com.hotslicerrpg.rpg.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.hotslicerrpg.rpg.Utils;

public class TabList extends AbstractPacket {
    public static final PacketType TYPE =
            PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER;

    public TabList() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    /**
     * @param header Header as a WrappedChatComponent.
     * @param footer Footer as a WrappedChatComponent.
     */
    public TabList(WrappedChatComponent header, WrappedChatComponent footer) {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        setHeader(header);
        setFooter(footer);
    }

    /**
     * @param header Header as a String.
     * @param footer Footer as a String.
     */
    public TabList(String header, String footer) {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
        setHeader(Utils.chatComponent(header));
        setFooter(Utils.chatComponent(footer));
    }

    public TabList(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve Header.
     *
     * @return The current Header
     */
    public WrappedChatComponent getHeader() {
        return handle.getChatComponents().read(0);
    }

    /**
     * Set Header.
     *
     * @param value - new value.
     */
    public void setHeader(WrappedChatComponent value) {
        handle.getChatComponents().write(0, value);
    }

    /**
     * Retrieve Footer.
     *
     * @return The current Footer
     */
    public WrappedChatComponent getFooter() {
        return handle.getChatComponents().read(1);
    }

    /**
     * Set Footer.
     *
     * @param value - new value.
     */
    public void setFooter(WrappedChatComponent value) {
        handle.getChatComponents().write(1, value);
    }

}