package com.candyquest.pattern.command;

import java.time.LocalDateTime;

/**
 * Concrete Command: Encapsulates requesting a hint from Candy Roo mascot.
 */
public class HintRequestedCommand implements UserActionCommand {
    private final String topicId;
    private final String hintGiven;
    private final LocalDateTime timestamp;

    public HintRequestedCommand(String topicId, String hintGiven) {
        this.topicId = topicId;
        this.hintGiven = hintGiven;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public void execute() {}

    @Override
    public void undo() {}

    @Override
    public String getDescription() {
        return "Requested mascot hint for topic " + topicId;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getHintGiven() {
        return hintGiven;
    }
}
