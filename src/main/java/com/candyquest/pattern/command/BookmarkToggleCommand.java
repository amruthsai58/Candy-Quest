package com.candyquest.pattern.command;

import com.candyquest.model.Topic;
import com.candyquest.pattern.singleton.AppSessionManager;

import java.time.LocalDateTime;

/**
 * Concrete Command: Handles toggling bookmark state on a topic with full undo support.
 */
public class BookmarkToggleCommand implements UserActionCommand {
    private final Topic topic;
    private final LocalDateTime timestamp;
    private boolean previousState;

    public BookmarkToggleCommand(Topic topic) {
        this.topic = topic;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public void execute() {
        if (topic != null) {
            this.previousState = topic.isBookmarked();
            topic.setBookmarked(!previousState);
            AppSessionManager.getInstance().getCurrentProgress().toggleBookmark(topic.getId());
        }
    }

    @Override
    public void undo() {
        if (topic != null) {
            topic.setBookmarked(previousState);
            if (previousState) {
                AppSessionManager.getInstance().getCurrentProgress().getBookmarkedTopicIds().add(topic.getId());
            } else {
                AppSessionManager.getInstance().getCurrentProgress().getBookmarkedTopicIds().remove(topic.getId());
            }
        }
    }

    @Override
    public String getDescription() {
        return "Toggled bookmark for: " + (topic != null ? topic.getName() : "Unknown");
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
