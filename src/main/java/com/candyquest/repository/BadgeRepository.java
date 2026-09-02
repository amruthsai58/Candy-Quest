package com.candyquest.repository;

import com.candyquest.model.Badge;
import com.candyquest.model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository containing available achievement badges in Candy Quest.
 */
public class BadgeRepository {
    private final Map<String, Badge> badges = new ConcurrentHashMap<>();

    public BadgeRepository() {
        initDefaultBadges();
    }

    private void initDefaultBadges() {
        // Universal Badges
        add(new Badge("badge_first_drop", "First Candy Drop", "Complete your first DSA topic!", "🍬", null, 1));
        add(new Badge("badge_sweet_streak_3", "Sugar Rush 3-Day", "Maintain a 3-day learning streak", "🔥", null, 3));
        add(new Badge("badge_ten_chews", "Fruity Decade", "Master 10 topics across any tracks", "📦", null, 10));
        add(new Badge("badge_quarter_century", "Candy Confectioner", "Master 25 DSA topics", "🏆", null, 25));
        add(new Badge("badge_half_century", "Gummy Grandmaster", "Master 50 DSA topics", "👑", null, 50));
        add(new Badge("badge_century", "Candy Centurion", "Master 100 DSA topics", "💎", null, 100));
        add(new Badge("badge_dsa_god", "Master of All Chews", "Complete all 150 DSA topics!", "🌌", null, 150));

        // Track Specific Badges
        add(new Badge("badge_straw_starter", "Strawberry Nibbler", "Complete 5 Foundations topics", "🍓", Track.FOUNDATIONS.name(), 5));
        add(new Badge("badge_straw_master", "Strawberry Champion", "Complete all Foundations topics", "🍰", Track.FOUNDATIONS.name(), 36));

        add(new Badge("badge_orange_starter", "Citrus Chainer", "Complete 5 Linear Structures topics", "🍊", Track.LINEAR.name(), 5));
        add(new Badge("badge_orange_master", "Orange Dynamo", "Complete all Linear Structures topics", "🍹", Track.LINEAR.name(), 36));

        add(new Badge("badge_grape_starter", "Vine Climber", "Complete 5 Trees & Graphs topics", "🍇", Track.TREES_GRAPHS.name(), 5));
        add(new Badge("badge_grape_master", "Grape Arborist", "Complete all Trees & Graphs topics", "🍷", Track.TREES_GRAPHS.name(), 36));

        add(new Badge("badge_melon_starter", "Melon Magician", "Complete 5 Advanced Algorithms topics", "🍉", Track.ADVANCED.name(), 5));
        add(new Badge("badge_melon_master", "Watermelon Wizard", "Complete all Advanced topics", "🧙‍♂️", Track.ADVANCED.name(), 42));
    }

    private void add(Badge badge) {
        badges.put(badge.getId(), badge);
    }

    public List<Badge> getAllBadges() {
        return new ArrayList<>(badges.values());
    }

    public Badge getBadgeById(String id) {
        return badges.get(id);
    }
}
