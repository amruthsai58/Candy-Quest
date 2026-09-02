package com.candyquest.service;

import com.candyquest.model.*;
import com.candyquest.pattern.observer.ProgressEvent;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.pattern.state.TopicState;
import com.candyquest.pattern.state.TopicStateContext;
import com.candyquest.repository.BadgeRepository;
import com.candyquest.repository.TopicRepository;
import com.candyquest.repository.ToyRewardRepository;
import com.candyquest.repository.UserProgressRepository;

import java.util.List;

/**
 * Service responsible for computing and persisting user learning progress,
 * checking badge milestones, unlocking "Free Toy Inside" easter eggs, and
 * notifying observers.
 */
public class ProgressService {
    private final TopicRepository topicRepository;
    private final UserProgressRepository progressRepository;
    private final BadgeRepository badgeRepository;
    private final ToyRewardRepository toyRewardRepository;

    public ProgressService(TopicRepository topicRepository, 
                           UserProgressRepository progressRepository, 
                           BadgeRepository badgeRepository, 
                           ToyRewardRepository toyRewardRepository) {
        this.topicRepository = topicRepository;
        this.progressRepository = progressRepository;
        this.badgeRepository = badgeRepository;
        this.toyRewardRepository = toyRewardRepository;
    }

    public TopicStateContext getTopicStateContext(Topic topic) {
        AppSessionManager session = AppSessionManager.getInstance();
        UserProgress progress = session.getCurrentProgress();
        
        boolean isCompleted = progress.isTopicCompleted(topic.getId());
        int bestScore = progress.getTopicQuizScores().getOrDefault(topic.getId(), 0);
        
        // Check if unlocked: first topic in track is always unlocked, or previous topic is completed
        boolean isUnlocked = isTopicUnlocked(topic);
        return new TopicStateContext(topic.getId(), isUnlocked, isCompleted, bestScore);
    }

    public boolean isTopicUnlocked(Topic topic) {
        if (topic == null) return false;
        List<Topic> trackTopics = topicRepository.getTopicsForTrack(topic.getTrack());
        int index = trackTopics.indexOf(topic);
        if (index <= 0) {
            return true; // First topic in every track is unlocked
        }
        // Topic is unlocked if preceding topic was completed
        Topic prevTopic = trackTopics.get(index - 1);
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        return progress.isTopicCompleted(prevTopic.getId());
    }

    public void completeTopicWithQuiz(Topic topic, int quizScore, int earnedXp) {
        AppSessionManager session = AppSessionManager.getInstance();
        UserProfile user = session.getCurrentUser();
        UserProgress progress = session.getCurrentProgress();

        TopicStateContext stateContext = getTopicStateContext(topic);
        int finalXp = stateContext.onQuizCompleted(quizScore) + earnedXp;

        progress.markTopicCompleted(topic.getId(), quizScore);
        user.addXp(finalXp);

        // Persist
        progressRepository.saveTopicCompletion(user.getId(), topic.getId(), quizScore);
        progressRepository.saveUserProfile(user);

        // Notify XP & Topic Completion
        session.getProgressSubject().notifyObservers(
            new ProgressEvent.Builder(ProgressEvent.EventType.TOPIC_COMPLETED)
                .topic(topic)
                .xpAdded(finalXp)
                .totalXp(user.getTotalXp())
                .message("🎉 Cleared " + topic.getName() + "! +" + finalXp + " XP")
                .build()
        );

        // Check for new badge unlocks
        checkBadgeUnlocks(topic);

        // Check for "Free Toy Inside" milestone (every 10 topics)
        checkToyUnlocks();
    }

    private void checkBadgeUnlocks(Topic lastCompletedTopic) {
        AppSessionManager session = AppSessionManager.getInstance();
        UserProfile user = session.getCurrentUser();
        UserProgress progress = session.getCurrentProgress();
        int totalCompleted = progress.getCompletedCount();

        for (Badge badge : badgeRepository.getAllBadges()) {
            if (!progress.getUnlockedBadgeIds().contains(badge.getId())) {
                boolean shouldUnlock = false;
                if (badge.getTrackId() == null) {
                    shouldUnlock = totalCompleted >= badge.getRequiredCompletions();
                } else if (lastCompletedTopic != null && lastCompletedTopic.getTrack().name().equals(badge.getTrackId())) {
                    int trackCompleted = progress.getCompletedCountForTrack(
                        lastCompletedTopic.getTrack(), 
                        topicRepository.getAllTopics().stream().collect(java.util.stream.Collectors.toMap(Topic::getId, t -> t))
                    );
                    shouldUnlock = trackCompleted >= badge.getRequiredCompletions();
                }

                if (shouldUnlock) {
                    badge.setUnlocked(true);
                    progress.getUnlockedBadgeIds().add(badge.getId());
                    progressRepository.saveBadgeUnlocked(user.getId(), badge.getId());

                    session.getProgressSubject().notifyObservers(
                        new ProgressEvent.Builder(ProgressEvent.EventType.BADGE_UNLOCKED)
                            .badge(badge)
                            .message("🏆 Achievement Unlocked: " + badge.getName() + "!")
                            .build()
                    );
                }
            }
        }
    }

    private void checkToyUnlocks() {
        AppSessionManager session = AppSessionManager.getInstance();
        UserProfile user = session.getCurrentUser();
        UserProgress progress = session.getCurrentProgress();
        int totalCompleted = progress.getCompletedCount();

        ToyReward toy = toyRewardRepository.getToyForMilestone(totalCompleted);
        if (toy != null && !progress.getClaimedToyIds().contains(toy.getId())) {
            toy.setClaimed(true);
            progress.getClaimedToyIds().add(toy.getId());
            progressRepository.saveToyClaimed(user.getId(), toy.getId());

            session.getProgressSubject().notifyObservers(
                new ProgressEvent.Builder(ProgressEvent.EventType.TOY_UNLOCKED)
                    .toyReward(toy)
                    .message("🎁 FREE TOY INSIDE UNLOCKED: " + toy.getTitle() + "!")
                    .build()
            );
        }
    }

    public double getTrackProgressPercentage(Track track) {
        List<Topic> trackTopics = topicRepository.getTopicsForTrack(track);
        if (trackTopics.isEmpty()) return 0.0;
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        long completed = trackTopics.stream().filter(t -> progress.isTopicCompleted(t.getId())).count();
        return (double) completed / trackTopics.size();
    }
}
