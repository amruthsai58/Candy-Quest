package com.candyquest.pattern.observer;

import com.candyquest.model.Badge;
import com.candyquest.model.Topic;
import com.candyquest.model.ToyReward;

/**
 * Event object payload dispatched by {@link ProgressSubject} to {@link ProgressObserver}s.
 */
public class ProgressEvent {
    public enum EventType {
        XP_GAINED,
        TOPIC_COMPLETED,
        BADGE_UNLOCKED,
        TOY_UNLOCKED,
        STREAK_INCREMENTED
    }

    private final EventType type;
    private final int xpAdded;
    private final int totalXp;
    private final Topic topic;
    private final Badge badge;
    private final ToyReward toyReward;
    private final String message;

    private ProgressEvent(Builder builder) {
        this.type = builder.type;
        this.xpAdded = builder.xpAdded;
        this.totalXp = builder.totalXp;
        this.topic = builder.topic;
        this.badge = builder.badge;
        this.toyReward = builder.toyReward;
        this.message = builder.message;
    }

    public EventType getType() {
        return type;
    }

    public int getXpAdded() {
        return xpAdded;
    }

    public int getTotalXp() {
        return totalXp;
    }

    public Topic getTopic() {
        return topic;
    }

    public Badge getBadge() {
        return badge;
    }

    public ToyReward getToyReward() {
        return toyReward;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {
        private EventType type;
        private int xpAdded;
        private int totalXp;
        private Topic topic;
        private Badge badge;
        private ToyReward toyReward;
        private String message;

        public Builder(EventType type) {
            this.type = type;
        }

        public Builder xpAdded(int xpAdded) {
            this.xpAdded = xpAdded;
            return this;
        }

        public Builder totalXp(int totalXp) {
            this.totalXp = totalXp;
            return this;
        }

        public Builder topic(Topic topic) {
            this.topic = topic;
            return this;
        }

        public Builder badge(Badge badge) {
            this.badge = badge;
            return this;
        }

        public Builder toyReward(ToyReward toyReward) {
            this.toyReward = toyReward;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ProgressEvent build() {
            return new ProgressEvent(this);
        }
    }
}
