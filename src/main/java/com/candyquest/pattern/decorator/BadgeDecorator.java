package com.candyquest.pattern.decorator;

import com.candyquest.model.Badge;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Design Pattern: Decorator (Abstract Decorator)</h1>
 * <p>
 * Implements the component interface and maintains a reference to the wrapped {@link BadgeComponent}.
 * </p>
 */
public abstract class BadgeDecorator implements BadgeComponent {
    protected final BadgeComponent wrappedComponent;

    public BadgeDecorator(BadgeComponent wrappedComponent) {
        this.wrappedComponent = wrappedComponent;
    }

    @Override
    public Badge getBadge() {
        return wrappedComponent.getBadge();
    }

    @Override
    public String getDisplayName() {
        return wrappedComponent.getDisplayName();
    }

    @Override
    public String getCssClasses() {
        return wrappedComponent.getCssClasses();
    }

    @Override
    public List<String> getVisualLayers() {
        return new ArrayList<>(wrappedComponent.getVisualLayers());
    }

    @Override
    public int getPrestigeLevel() {
        return wrappedComponent.getPrestigeLevel();
    }
}
