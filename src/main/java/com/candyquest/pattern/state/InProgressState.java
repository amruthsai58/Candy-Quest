package com.candyquest.pattern.state;

/**
 * Concrete State: Topic is unlocked and active for studying and taking quizzes.
 */
public class InProgressState implements TopicState {
    @Override
    public String getStateName() {
        return "IN_PROGRESS";
    }

    @Override
    public boolean canOpen() {
        return true;
    }

    @Override
    public boolean canStartQuiz() {
        return true;
    }

    @Override
    public int calculateXpGain(int baseQuizScore) {
        // Full XP awarded on first completion
        return baseQuizScore + 25; // 25 XP first-time completion bonus!
    }

    @Override
    public String getUiBadgeStyle() {
        return "state-in-progress";
    }

    @Override
    public String getStatusDescription() {
        return "🍬 In Progress: Study concept and pass quiz to clear!";
    }

    @Override
    public TopicState transitionNext(int quizScore) {
        if (quizScore >= 100) {
            return new MasteredState();
        } else if (quizScore >= 60) {
            return new CompletedState();
        }
        return this; // stay in progress if not passed
    }
}
