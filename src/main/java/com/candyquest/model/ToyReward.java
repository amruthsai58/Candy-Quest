package com.candyquest.model;

/**
 * Entity representing the "Free Toy Inside" rewards unlocked every 10 completed topics.
 */
public class ToyReward {
    private String id;
    private int unlockThreshold; // e.g. 10, 20, 30... 150
    private String title;
    private String toyType; // e.g. "Mascot Skin", "Secret Mini-Game", "Golden Chew Trophy", "Easter Egg Animation"
    private String description;
    private String iconEmoji;
    private String animationType;
    private boolean claimed;

    public ToyReward() {}

    public ToyReward(String id, int unlockThreshold, String title, String toyType, 
                     String description, String iconEmoji, String animationType) {
        this.id = id;
        this.unlockThreshold = unlockThreshold;
        this.title = title;
        this.toyType = toyType;
        this.description = description;
        this.iconEmoji = iconEmoji;
        this.animationType = animationType;
        this.claimed = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnlockThreshold() {
        return unlockThreshold;
    }

    public void setUnlockThreshold(int unlockThreshold) {
        this.unlockThreshold = unlockThreshold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToyType() {
        return toyType;
    }

    public void setToyType(String toyType) {
        this.toyType = toyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconEmoji() {
        return iconEmoji;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public String getAnimationType() {
        return animationType;
    }

    public void setAnimationType(String animationType) {
        this.animationType = animationType;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
}
