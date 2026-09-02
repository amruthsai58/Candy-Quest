package com.candyquest.pattern.command;

import java.time.LocalDateTime;

/**
 * <h1>Design Pattern: Command (Command Interface)</h1>
 * <p>
 * <b>Why chosen:</b> Encapsulates user interactions (answering questions, bookmarking topics,
 * requesting mascot hints, changing tracks) as standalone command objects. This enables:
 * <ul>
 *   <li>Undo / Redo capabilities for bookmarks and study actions.</li>
 *   <li>Replay logging and analytics tracking for learning efficiency.</li>
 *   <li>Decoupling the UI triggers (buttons, hotkeys) from the underlying progress modification logic.</li>
 * </ul>
 * </p>
 */
public interface UserActionCommand {

    /**
     * Executes the command logic.
     */
    void execute();

    /**
     * Reverses the command action if undoable.
     */
    void undo();

    /**
     * Short human-readable description of the action.
     */
    String getDescription();

    /**
     * Timestamp when the command was generated.
     */
    LocalDateTime getTimestamp();
}
