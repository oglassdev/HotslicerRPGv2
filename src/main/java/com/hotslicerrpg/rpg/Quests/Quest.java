package com.hotslicerrpg.rpg.Quests;

public enum Quest {
    QUEST_NOT_FOUND(QuestStage.COMPLETION,"&4Quest Not Found!", null),

    FISHERMAN2(QuestStage.COMPLETION,"&eTalk to the Fisherman", new Reward(400)),
    FISHERMAN1(QuestStage.OBJECTIVE,"&eGive the Fisherman 5 Fish", null, FISHERMAN2),
    FISHERMAN0(QuestStage.OBJECTIVE,"&eTalk to the Fisherman", null, FISHERMAN1),
    ;
    private final QuestStage questStage;
    private final String name;
    private final Reward reward;
    private final Quest nextStage;
    Quest(QuestStage stage, String name, Reward reward) {
        this.questStage = stage;
        this.name = name;
        this.reward = reward;
        this.nextStage = null;
    }
    Quest(QuestStage stage, String name, Reward reward, Quest nextStage) {
        this.questStage = stage;
        this.name = name;
        this.reward = reward;
        this.nextStage = nextStage;
    }
    public String getName() {
        return name;
    }
    public Reward getReward() {
        return reward;
    }
    public QuestStage getQuestStage() {
        return questStage;
    }
    public Quest getNextStage() {
        return nextStage;
    }

    public enum QuestStage {
        COMPLETION,OBJECTIVE
    }
}
