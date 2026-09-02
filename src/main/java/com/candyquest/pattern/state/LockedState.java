package com.candyquest.pattern.state;

/**
 * Concrete State: Topic is locked until previous milestones are reached.
 */
public class LockedState implements TopicState {
    @Override
    public String getStateName() {
        return "LOCKED";
    }

    @Override
    public boolean canOpen() {
        return false;
    }

    @Override
    public boolean canStartQuiz() {
        return false;
    }

    @Override
    public int calculateXpGain(int baseQuizScore) {
        return 0;
    }

    @Override
    public String getUiBadgeStyle() {
        return "state-locked";
    }

    @Override
    public String getStatusDescription() {
        return "🔒 Locked: Complete preceding candy nodes to unlock!";
    }

    @Override
    public TopicState transitionNext(int quizScore) {
        // Unlocking moves to InProgress
        return new InProgressState();
    }
}
