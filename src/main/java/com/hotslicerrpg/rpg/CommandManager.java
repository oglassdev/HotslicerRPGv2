package com.hotslicerrpg.rpg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;

public class CommandManager {
    public CommandManager() {}

    public CommandMap getCommandMap() {
        Field bukkitCommandMap = null;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = null;
        try {
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return commandMap;
    }

    public void registerCommand(String command, BukkitCommand bukkitCommand) {
        getCommandMap().register(command, bukkitCommand);
    }
}