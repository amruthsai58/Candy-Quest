package com.candyquest.pattern.decorator;

import java.util.List;

/**
 * Concrete Decorator: Adds dynamic particle sparkles around the badge.
 */
public class SparkleBadgeDecorator extends BadgeDecorator {

    public SparkleBadgeDecorator(BadgeComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public String getDisplayName() {
        return "✨ " + super.getDisplayName() + " ✨";
    }

    @Override
    public String getCssClasses() {
        return super.getCssClasses() + " effect-sparkle";
    }

    @Override
    public List<String> getVisualLayers() {
        List<String> layers = super.getVisualLayers();
        layers.add("layer-sparkles");
        return layers;
    }

    @Override
    public int getPrestigeLevel() {
        return super.getPrestigeLevel() + 1;
    }
}
