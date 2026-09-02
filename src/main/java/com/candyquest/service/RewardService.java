package com.candyquest.service;

import com.candyquest.model.Badge;
import com.candyquest.model.ToyReward;
import com.candyquest.model.UserProgress;
import com.candyquest.pattern.decorator.*;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.repository.BadgeRepository;
import com.candyquest.repository.ToyRewardRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service managing badges, the Decorator pattern layer decorations, and Free Toy rewards.
 */
public class RewardService {
    private final BadgeRepository badgeRepository;
    private final ToyRewardRepository toyRewardRepository;

    public RewardService(BadgeRepository badgeRepository, ToyRewardRepository toyRewardRepository) {
        this.badgeRepository = badgeRepository;
        this.toyRewardRepository = toyRewardRepository;
    }

    public List<BadgeComponent> getDecoratedBadges() {
        List<BadgeComponent> decoratedList = new ArrayList<>();
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        int totalCompleted = progress.getCompletedCount();

        for (Badge badge : badgeRepository.getAllBadges()) {
            boolean isUnlocked = progress.getUnlockedBadgeIds().contains(badge.getId());
            badge.setUnlocked(isUnlocked);

            // Base component
            BadgeComponent component = new BaseBadgeComponent(badge);

            if (isUnlocked) {
                // If user has high streak or high score, decorate with Sparkles
                if (AppSessionManager.getInstance().getCurrentUser().getStreakDays() >= 3) {
                    component = new SparkleBadgeDecorator(component);
                }

                // If user completed a major milestone (>=25 topics), decorate with Glow
                if (totalCompleted >= 25) {
                    component = new GlowBadgeDecorator(component);
                }

                // If user has unlocked toys, attach Toy Ribbon decorator
                if (!progress.getClaimedToyIds().isEmpty()) {
                    ToyReward latestToy = getLatestClaimedToy();
                    String toyName = latestToy != null ? latestToy.getTitle() : "Surprise Toy";
                    component = new ToyUnlockedBadgeDecorator(component, toyName);
                }
            }

            decoratedList.add(component);
        }
        return decoratedList;
    }

    public List<ToyReward> getAllToys() {
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        List<ToyReward> toys = toyRewardRepository.getAllToys();
        for (ToyReward toy : toys) {
            toy.setClaimed(progress.getClaimedToyIds().contains(toy.getId()));
        }
        return toys;
    }

    public ToyReward getLatestClaimedToy() {
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        for (ToyReward toy : toyRewardRepository.getAllToys()) {
            if (progress.getClaimedToyIds().contains(toy.getId())) {
                return toy;
            }
        }
        return null;
    }
}
