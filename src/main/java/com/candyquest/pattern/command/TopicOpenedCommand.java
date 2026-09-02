package com.candyquest.pattern.command;

import com.candyquest.model.Topic;

import java.time.LocalDateTime;

/**
 * Concrete Command: Encapsulates navigation to a topic view for analytics and history.
 */
public class TopicOpenedCommand implements UserActionCommand {
    private final Topic topic;
    private final LocalDateTime timestamp;

    public TopicOpenedCommand(Topic topic) {
        this.topic = topic;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public void execute() {
        // Logging/Analytics telemetry hook
    }

    @Override
    public void undo() {
        // Navigation history rollback if needed
    }

    @Override
    public String getDescription() {
        return "Opened Topic: " + (topic != null ? topic.getName() : "Unknown");
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Topic getTopic() {
        return topic;
    }
}
