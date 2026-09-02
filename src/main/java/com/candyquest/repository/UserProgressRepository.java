package com.candyquest.repository;

import com.candyquest.model.UserProfile;
import com.candyquest.model.UserProgress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Repository for persisting and retrieving user profile & topic progress from SQLite.
 */
public class UserProgressRepository {

    public UserProfile loadUserProfile(String userId) {
        Connection conn = DatabaseManager.getConnection();
        UserProfile profile = new UserProfile(userId, "Candy Explorer", "classic_roo", 0, 1);
        try (PreparedStatement stmt = conn.prepareStatement("SELECT username, avatar_skin, total_xp, streak_days, last_active FROM user_profiles WHERE id = ?")) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    profile.setUsername(rs.getString("username"));
                    profile.setAvatarMascotSkin(rs.getString("avatar_skin"));
                    profile.setTotalXp(rs.getInt("total_xp"));
                    profile.setStreakDays(rs.getInt("streak_days"));
                    profile.setLastActiveDate(rs.getString("last_active"));
                    return profile;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading user profile: " + e.getMessage());
        }
        // Save default profile if none exists
        saveUserProfile(profile);
        return profile;
    }

    public void saveUserProfile(UserProfile profile) {
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO user_profiles (id, username, avatar_skin, total_xp, streak_days, last_active)
            VALUES (?, ?, ?, ?, ?, ?)
            ON CONFLICT(id) DO UPDATE SET
                username = excluded.username,
                avatar_skin = excluded.avatar_skin,
                total_xp = excluded.total_xp,
                streak_days = excluded.streak_days,
                last_active = excluded.last_active
        """)) {
            stmt.setString(1, profile.getId());
            stmt.setString(2, profile.getUsername());
            stmt.setString(3, profile.getAvatarMascotSkin());
            stmt.setInt(4, profile.getTotalXp());
            stmt.setInt(5, profile.getStreakDays());
            stmt.setString(6, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving user profile: " + e.getMessage());
        }
    }

    public UserProgress loadUserProgress(String userId) {
        Connection conn = DatabaseManager.getConnection();
        UserProgress progress = new UserProgress(userId);
        try {
            // Load Topic progress
            try (PreparedStatement stmt = conn.prepareStatement("SELECT topic_id, completed, best_score, bookmarked FROM topic_progress WHERE user_id = ?")) {
                stmt.setString(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String topicId = rs.getString("topic_id");
                        boolean completed = rs.getInt("completed") == 1;
                        int score = rs.getInt("best_score");
                        boolean bookmarked = rs.getInt("bookmarked") == 1;
                        if (completed) {
                            progress.getCompletedTopics().put(topicId, true);
                            progress.getTopicQuizScores().put(topicId, score);
                        }
                        if (bookmarked) {
                            progress.getBookmarkedTopicIds().add(topicId);
                        }
                    }
                }
            }

            // Load Unlocked Badges
            try (PreparedStatement stmt = conn.prepareStatement("SELECT badge_id FROM user_badges WHERE user_id = ? AND unlocked = 1")) {
                stmt.setString(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        progress.getUnlockedBadgeIds().add(rs.getString("badge_id"));
                    }
                }
            }

            // Load Claimed Toys
            try (PreparedStatement stmt = conn.prepareStatement("SELECT toy_id FROM claimed_toys WHERE user_id = ?")) {
                stmt.setString(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        progress.getClaimedToyIds().add(rs.getString("toy_id"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading progress: " + e.getMessage());
        }
        return progress;
    }

    public void saveTopicCompletion(String userId, String topicId, int score) {
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO topic_progress (user_id, topic_id, completed, best_score, updated_at)
            VALUES (?, ?, 1, ?, ?)
            ON CONFLICT(user_id, topic_id) DO UPDATE SET
                completed = 1,
                best_score = MAX(topic_progress.best_score, excluded.best_score),
                updated_at = excluded.updated_at
        """)) {
            stmt.setString(1, userId);
            stmt.setString(2, topicId);
            stmt.setInt(3, score);
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving topic completion: " + e.getMessage());
        }
    }

    public void saveBookmark(String userId, String topicId, boolean bookmarked) {
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO topic_progress (user_id, topic_id, bookmarked, updated_at)
            VALUES (?, ?, ?, ?)
            ON CONFLICT(user_id, topic_id) DO UPDATE SET
                bookmarked = excluded.bookmarked,
                updated_at = excluded.updated_at
        """)) {
            stmt.setString(1, userId);
            stmt.setString(2, topicId);
            stmt.setInt(3, bookmarked ? 1 : 0);
            stmt.setString(4, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving bookmark: " + e.getMessage());
        }
    }

    public void saveBadgeUnlocked(String userId, String badgeId) {
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO user_badges (user_id, badge_id, unlocked, unlocked_at)
            VALUES (?, ?, 1, ?)
            ON CONFLICT(user_id, badge_id) DO UPDATE SET
                unlocked = 1,
                unlocked_at = excluded.unlocked_at
        """)) {
            stmt.setString(1, userId);
            stmt.setString(2, badgeId);
            stmt.setString(3, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving unlocked badge: " + e.getMessage());
        }
    }

    public void saveToyClaimed(String userId, String toyId) {
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("""
            INSERT INTO claimed_toys (user_id, toy_id, claimed_at)
            VALUES (?, ?, ?)
            ON CONFLICT(user_id, toy_id) DO NOTHING
        """)) {
            stmt.setString(1, userId);
            stmt.setString(2, toyId);
            stmt.setString(3, LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving claimed toy: " + e.getMessage());
        }
    }
}
