package com.hotslicerrpg.rpg.Quests;

import com.hotslicerrpg.rpg.LocalStorage;
import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.Storage;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class QuestStorage {
    private final UUID uuid;
    private final String location;

    private final Storage<Quest> TEST_QUEST_FILE;
    private final Quest QUEST_FILE;

    public QuestStorage(Player player) throws IOException {
        this.uuid = player.getUniqueId();
        this.location = Main.getPlugin().getDataFolder().getAbsolutePath() + File.separator +
                "Quests" +File.separator + player.getUniqueId() + File.separator;
        this.TEST_QUEST_FILE = new LocalStorage<>(location + "Test_Quest.json");
        this.QUEST_FILE = TEST_QUEST_FILE.load(Quest.class);
        TEST_QUEST_FILE.save(new Quest(QuestType.TEST,player));
    }
}
