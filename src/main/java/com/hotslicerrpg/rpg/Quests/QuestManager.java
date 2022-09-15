package com.hotslicerrpg.rpg.Quests;

import com.hotslicerrpg.rpg.LocalStorage;
import com.hotslicerrpg.rpg.Main;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class QuestManager {
    private static final HashMap<Player, QuestStorage> quests = new HashMap<>();
    public static QuestStorage getQuestData(Player player) {
        return quests.get(player);
    }
    public static void initPlayer(Player player) {
        try {
            quests.put(player, new QuestStorage(player));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
