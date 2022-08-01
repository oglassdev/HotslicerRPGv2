package com.hotslicerrpg.rpg.Scripting;

import com.hotslicerrpg.rpg.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public abstract class CustomCommand extends BukkitCommand {
    public CustomCommand(String name, String description, String usage, String permission) {
        super(name);
        this.description = description;
        this.usageMessage = usage;
        if (permission == null) this.setPermission("");
        else this.setPermission(permission);
        this.setAliases(Collections.singletonList(""));
    }
    public CustomCommand(String name, String description, String usage, String permission, String... aliases) {
        super(name);
        this.description = description;
        this.usageMessage = usage;
        if (permission == null) this.setPermission("");
        else this.setPermission(permission);
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(Utils.color("&cYou don't have the required permissions to use this command!"));
            return true;
        }
        run(sender, alias, args);
        return true;
    }

    public abstract void run(CommandSender sender, String alias, String[] args);
}
