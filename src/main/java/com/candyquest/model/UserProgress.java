package com.candyquest.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tracks the comprehensive learning progress across all tracks and topics.
 */
public class UserProgress {
    private String userId;
    private final Map<String, Boolean> completedTopics;
    private final Map<String, Integer> topicQuizScores;
    private final Set<String> bookmarkedTopicIds;
    private final Set<String> unlockedBadgeIds;
    private final Set<String> claimedToyIds;

    public UserProgress() {
        this.userId = "user_default";
        this.completedTopics = new HashMap<>();
        this.topicQuizScores = new HashMap<>();
        this.bookmarkedTopicIds = new HashSet<>();
        this.unlockedBadgeIds = new HashSet<>();
        this.claimedToyIds = new HashSet<>();
    }

    public UserProgress(String userId) {
        this.userId = userId;
        this.completedTopics = new HashMap<>();
        this.topicQuizScores = new HashMap<>();
        this.bookmarkedTopicIds = new HashSet<>();
        this.unlockedBadgeIds = new HashSet<>();
        this.claimedToyIds = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Boolean> getCompletedTopics() {
        return completedTopics;
    }

    public Map<String, Integer> getTopicQuizScores() {
        return topicQuizScores;
    }

    public Set<String> getBookmarkedTopicIds() {
        return bookmarkedTopicIds;
    }

    public Set<String> getUnlockedBadgeIds() {
        return unlockedBadgeIds;
    }

    public Set<String> getClaimedToyIds() {
        return claimedToyIds;
    }

    public boolean isTopicCompleted(String topicId) {
        return Boolean.TRUE.equals(completedTopics.get(topicId));
    }

    public void markTopicCompleted(String topicId, int score) {
        completedTopics.put(topicId, true);
        topicQuizScores.put(topicId, Math.max(topicQuizScores.getOrDefault(topicId, 0), score));
    }

    public int getCompletedCount() {
        return (int) completedTopics.values().stream().filter(Boolean::booleanValue).count();
    }

    public int getCompletedCountForTrack(Track track, Map<String, Topic> allTopics) {
        int count = 0;
        for (Map.Entry<String, Boolean> entry : completedTopics.entrySet()) {
            if (entry.getValue()) {
                Topic topic = allTopics.get(entry.getKey());
                if (topic != null && topic.getTrack() == track) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isBookmarked(String topicId) {
        return bookmarkedTopicIds.contains(topicId);
    }

    public void toggleBookmark(String topicId) {
        if (bookmarkedTopicIds.contains(topicId)) {
            bookmarkedTopicIds.remove(topicId);
        } else {
            bookmarkedTopicIds.add(topicId);
        }
    }
}
