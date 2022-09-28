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

        Quest quest;
        try {
            quest = Quest.valueOf(event.getQuestProgress().getQuestId());
        } catch (EnumConstantNotPresentException ignored) { return; }
        Reward reward = quest.getReward();

        if (quest.getQuestStage() == Quest.QuestStage.OBJECTIVE) player.sendMessage(Utils.color("&8------< &6&lObjective Complete!&8 >-------"));
        else player.sendMessage(Utils.color("&8--------< &e&lQuest Complete!&8 >---------"));
        player.sendMessage(Utils.color("&a"));
        player.sendMessage(Utils.color("&8| &a" + quest.getName() + "&7 completed!"));
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f,1f);
        if (reward != null) {
            player.sendMessage(Utils.color("&8| &e&lRewards"));
            for (String str : reward.prettyArray()) {
                player.sendMessage(Utils.color(str));
            }
            reward.give(player);
        }
        player.sendMessage(Utils.color("&a"));
        player.sendMessage(Utils.color("&8-----------------------------------"));
        if (quest.getNextStage() != null) {
            com.leonardobishop.quests.common.quest.Quest q = Main.getQuestPlugin().getQuestManager().getQuestById(quest.getNextStage().name());
            if (q == null) return;
            event.getQuestPlayer().startQuest(q);
        }

        event.setQuestFinishMessage(null);
    }
}
