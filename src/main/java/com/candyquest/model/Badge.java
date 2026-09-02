package com.candyquest.model;

/**
 * Entity representing an unlockable achievement badge.
 */
public class Badge {
    private String id;
    private String name;
    private String description;
    private String iconEmoji;
    private String trackId; // null if universal
    private int requiredCompletions;
    private boolean unlocked;
    private String unlockedAt;

    public Badge() {}

    public Badge(String id, String name, String description, String iconEmoji, 
                 String trackId, int requiredCompletions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconEmoji = iconEmoji;
        this.trackId = trackId;
        this.requiredCompletions = requiredCompletions;
        this.unlocked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getRequiredCompletions() {
        return requiredCompletions;
    }

    public void setRequiredCompletions(int requiredCompletions) {
        this.requiredCompletions = requiredCompletions;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(String unlockedAt) {
        this.unlockedAt = unlockedAt;
    }
}
