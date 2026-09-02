package com.candyquest.pattern.decorator;

import java.util.List;

/**
 * Concrete Decorator: Adds a pulsating golden halo / aura glow around the badge.
 */
public class GlowBadgeDecorator extends BadgeDecorator {

    public GlowBadgeDecorator(BadgeComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public String getDisplayName() {
        return "🌟 " + super.getDisplayName();
    }

    @Override
    public String getCssClasses() {
        return super.getCssClasses() + " effect-golden-glow";
    }

    @Override
    public List<String> getVisualLayers() {
        List<String> layers = super.getVisualLayers();
        layers.add("layer-golden-glow");
        return layers;
    }

    @Override
    public int getPrestigeLevel() {
        return super.getPrestigeLevel() + 2;
    }
}
