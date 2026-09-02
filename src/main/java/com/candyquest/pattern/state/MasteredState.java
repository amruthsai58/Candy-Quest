package com.candyquest.pattern.state;

/**
 * Concrete State: Topic cleared with 100% perfect score.
 */
public class MasteredState implements TopicState {
    @Override
    public String getStateName() {
        return "MASTERED";
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
        return 5; // Token review XP
    }

    @Override
    public String getUiBadgeStyle() {
        return "state-mastered";
    }

    @Override
    public String getStatusDescription() {
        return "👑 Mastered: 100% Perfect Mastery achieved!";
    }

    @Override
    public TopicState transitionNext(int quizScore) {
        return this; // Already at peak state
    }
}
