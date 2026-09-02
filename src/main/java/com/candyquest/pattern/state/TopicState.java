package com.candyquest.pattern.state;

/**
 * <h1>Design Pattern: State (State Interface)</h1>
 * <p>
 * <b>Why chosen:</b> A topic progresses through strict lifecycle states:
 * <code>LOCKED &rarr; IN_PROGRESS &rarr; COMPLETED &rarr; MASTERED</code>.
 * Different states have different behavioral rules (e.g. locked topics cannot launch quizzes,
 * completed topics award review XP instead of first-time completion bonuses, in-progress topics
 * allow full interaction). The State pattern encapsulates these rules cleanly without messy
 * conditional branching.
 * </p>
 */
public interface TopicState {

    String getStateName();

    boolean canOpen();

    boolean canStartQuiz();

    int calculateXpGain(int baseQuizScore);

    String getUiBadgeStyle();

    String getStatusDescription();

    TopicState transitionNext(int quizScore);
}
