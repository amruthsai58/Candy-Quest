package com.candyquest.model;

/**
 * Entity representing user profile information.
 */
public class UserProfile {
    private String id;
    private String username;
    private String avatarMascotSkin; // "classic_roo", "ninja_roo", "wizard_roo", "golden_roo"
    private int totalXp;
    private int streakDays;
    private String lastActiveDate;

    public UserProfile() {
        this.id = "user_default";
        this.username = "Candy Explorer";
        this.avatarMascotSkin = "classic_roo";
        this.totalXp = 0;
        this.streakDays = 1;
    }

    public UserProfile(String id, String username, String avatarMascotSkin, int totalXp, int streakDays) {
        this.id = id;
        this.username = username;
        this.avatarMascotSkin = avatarMascotSkin;
        this.totalXp = totalXp;
        this.streakDays = streakDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarMascotSkin() {
        return avatarMascotSkin;
    }

    public void setAvatarMascotSkin(String avatarMascotSkin) {
        this.avatarMascotSkin = avatarMascotSkin;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public void addXp(int amount) {
        this.totalXp += amount;
    }

    public int getStreakDays() {
        return streakDays;
    }

    public void setStreakDays(int streakDays) {
        this.streakDays = streakDays;
    }

    public String getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(String lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public int getLevel() {
        return (totalXp / 100) + 1;
    }

    public int getXpInCurrentLevel() {
        return totalXp % 100;
    }
}
