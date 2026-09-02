package com.candyquest.pattern.singleton;

import com.candyquest.model.Track;
import com.candyquest.model.UserProfile;
import com.candyquest.model.UserProgress;
import com.candyquest.pattern.observer.ProgressSubject;

/**
 * <h1>Design Pattern: Singleton</h1>
 * <p>
 * <b>Why chosen:</b> In a desktop learning application, user session state (current user,
 * active track, global audio settings, and runtime progression events) must be universally
 * accessible across all controllers without tight coupling or cumbersome parameter passing.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implemented via Lazy Initialization with Double-Checked Locking.
 * </p>
 */
public class AppSessionManager {
    private static volatile AppSessionManager instance;

    private UserProfile currentUser;
    private UserProgress currentProgress;
    private Track activeTrack;
    private boolean soundEnabled;
    private final ProgressSubject progressSubject;

    private AppSessionManager() {
        // Private constructor prevents direct instantiation
        this.currentUser = new UserProfile("user_default", "Candy Explorer", "classic_roo", 0, 1);
        this.currentProgress = new UserProgress("user_default");
        this.activeTrack = Track.FOUNDATIONS;
        this.soundEnabled = true;
        this.progressSubject = new ProgressSubject();
    }

    public static AppSessionManager getInstance() {
        if (instance == null) {
            synchronized (AppSessionManager.class) {
                if (instance == null) {
                    instance = new AppSessionManager();
                }
            }
        }
        return instance;
    }

    public UserProfile getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    public UserProgress getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(UserProgress currentProgress) {
        this.currentProgress = currentProgress;
    }

    public Track getActiveTrack() {
        return activeTrack;
    }

    public void setActiveTrack(Track activeTrack) {
        this.activeTrack = activeTrack;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public ProgressSubject getProgressSubject() {
        return progressSubject;
    }

    public void reset() {
        this.currentUser = new UserProfile("user_default", "Candy Explorer", "classic_roo", 0, 1);
        this.currentProgress = new UserProgress("user_default");
        this.activeTrack = Track.FOUNDATIONS;
    }
}
