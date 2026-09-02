package com.candyquest.pattern.decorator;

import java.util.List;

/**
 * Concrete Decorator: Adds the "Free Toy Inside" golden ribbon banner and mascot sticker overlay.
 */
public class ToyUnlockedBadgeDecorator extends BadgeDecorator {
    private final String toyName;

    public ToyUnlockedBadgeDecorator(BadgeComponent wrappedComponent, String toyName) {
        super(wrappedComponent);
        this.toyName = toyName;
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " [🎁 " + (toyName != null ? toyName : "Toy Inside") + "]";
    }

    @Override
    public String getCssClasses() {
        return super.getCssClasses() + " effect-toy-ribbon";
    }

    @Override
    public List<String> getVisualLayers() {
        List<String> layers = super.getVisualLayers();
        layers.add("layer-toy-ribbon");
        layers.add("layer-toy-sticker");
        return layers;
    }

    @Override
    public int getPrestigeLevel() {
        return super.getPrestigeLevel() + 3;
    }
}
