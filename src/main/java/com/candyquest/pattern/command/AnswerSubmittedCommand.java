package com.candyquest.pattern.command;

import com.candyquest.model.QuizQuestion;

import java.time.LocalDateTime;

/**
 * Concrete Command: Encapsulates answering a quiz question.
 */
public class AnswerSubmittedCommand implements UserActionCommand {
    private final QuizQuestion question;
    private final int selectedOption;
    private final boolean wasCorrect;
    private final int xpAwarded;
    private final LocalDateTime timestamp;

    public AnswerSubmittedCommand(QuizQuestion question, int selectedOption, boolean wasCorrect, int xpAwarded) {
        this.question = question;
        this.selectedOption = selectedOption;
        this.wasCorrect = wasCorrect;
        this.xpAwarded = xpAwarded;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public void execute() {
        // Handled in quiz engine
    }

    @Override
    public void undo() {
        // Rollback score if in debug/playground mode
    }

    @Override
    public String getDescription() {
        return "Answered " + (wasCorrect ? "CORRECT (+ " + xpAwarded + " XP)" : "INCORRECT") + 
               " for question: " + (question != null ? question.getQuestionText() : "");
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isWasCorrect() {
        return wasCorrect;
    }

    public int getXpAwarded() {
        return xpAwarded;
    }
}
