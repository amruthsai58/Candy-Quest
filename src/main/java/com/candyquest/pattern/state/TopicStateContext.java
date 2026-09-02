package com.candyquest.pattern.state;

/**
 * Context object in the State Pattern that maintains the current state reference.
 */
public class TopicStateContext {
    private final String topicId;
    private TopicState currentState;

    public TopicStateContext(String topicId, boolean isUnlocked, boolean isCompleted, int bestScore) {
        this.topicId = topicId;
        if (!isUnlocked) {
            this.currentState = new LockedState();
        } else if (!isCompleted) {
            this.currentState = new InProgressState();
        } else if (bestScore >= 100) {
            this.currentState = new MasteredState();
        } else {
            this.currentState = new CompletedState();
        }
    }

    public String getTopicId() {
        return topicId;
    }

    public TopicState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TopicState newState) {
        this.currentState = newState;
    }

    public int onQuizCompleted(int score) {
        int xp = currentState.calculateXpGain(score);
        this.currentState = currentState.transitionNext(score);
        return xp;
    }

    public void unlock() {
        if (currentState instanceof LockedState) {
            this.currentState = new InProgressState();
        }
    }
}
