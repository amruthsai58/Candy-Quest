package com.candyquest.pattern.decorator;

import com.candyquest.model.Badge;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Component: Base badge representation without extra visual flair.
 */
public class BaseBadgeComponent implements BadgeComponent {
    private final Badge badge;

    public BaseBadgeComponent(Badge badge) {
        this.badge = badge;
    }

    @Override
    public Badge getBadge() {
        return badge;
    }

    @Override
    public String getDisplayName() {
        return badge != null ? badge.getName() : "Badge";
    }

    @Override
    public String getCssClasses() {
        return "badge-base " + (badge != null && badge.isUnlocked() ? "badge-unlocked" : "badge-locked");
    }

    @Override
    public List<String> getVisualLayers() {
        List<String> layers = new ArrayList<>();
        layers.add("base-icon");
        return layers;
    }

    @Override
    public int getPrestigeLevel() {
        return 1;
    }
}
