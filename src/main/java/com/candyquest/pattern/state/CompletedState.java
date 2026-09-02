package com.candyquest.pattern.state;

/**
 * Concrete State: Topic is passed and cleared. Retakes award standard review XP.
 */
public class CompletedState implements TopicState {
    @Override
    public String getStateName() {
        return "COMPLETED";
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
        // Repeat review awards half base XP
        return Math.max(5, baseQuizScore / 2);
    }

    @Override
    public String getUiBadgeStyle() {
        return "state-completed";
    }

    @Override
    public String getStatusDescription() {
        return "✅ Completed: Topic cleared! Retake for 100% mastery.";
    }

    @Override
    public TopicState transitionNext(int quizScore) {
        if (quizScore >= 100) {
            return new MasteredState();
        }
        return this;
    }
}
