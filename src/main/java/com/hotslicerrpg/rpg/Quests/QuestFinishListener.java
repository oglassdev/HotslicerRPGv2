package com.hotslicerrpg.rpg.Quests;

import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.Utils;
import com.leonardobishop.quests.bukkit.api.event.PlayerFinishQuestEvent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestFinishListener implements Listener {
    public QuestFinishListener(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }
    @EventHandler
    public void onQuestFinish(PlayerFinishQuestEvent event) {
        Player player = event.getPlayer();
        Reward reward = new Reward(200);
        player.sendMessage(Utils.color("&a"));
        player.sendMessage(Utils.color("&7------ &e&lQuest Complete!&7 -------"));
        player.sendMessage(Utils.color("&a"));
        player.sendMessage(Utils.color("&8| &a" + event.getQuestFinishMessage() + "&7 completed!"));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
        String[] rwd = reward.prettyArray();
        if (rwd.length > 0) player.sendMessage(Utils.color("&8| &e&lRewards"));
        for (String str : rwd) {
            player.sendMessage(Utils.color(str));
        }
        player.sendMessage(Utils.color("&a"));
        player.sendMessage(Utils.color("&7------------------------------"));
        player.sendMessage(Utils.color("&a"));

        reward.give(player);
        event.setQuestFinishMessage(null);
    }
}
