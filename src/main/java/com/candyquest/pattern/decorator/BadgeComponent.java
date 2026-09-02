package com.candyquest.pattern.decorator;

import com.candyquest.model.Badge;

import java.util.List;

/**
 * <h1>Design Pattern: Decorator (Component Interface)</h1>
 * <p>
 * <b>Why chosen:</b> Badges start as base achievements, but as the user earns streaks,
 * completes tracks, or unlocks rare "Free Toy Inside" easter eggs, the badge UI needs
 * dynamic visual layers added (e.g. golden glow, animated sparkles, toy ribbons, neon borders)
 * without creating dozens of rigid subclasses like <code>GlowSparkleToyBadge</code>.
 * </p>
 */
public interface BadgeComponent {
    Badge getBadge();
    String getDisplayName();
    String getCssClasses();
    List<String> getVisualLayers();
    int getPrestigeLevel();
}
