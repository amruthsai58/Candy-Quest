package com.candyquest.pattern.strategy;

import com.candyquest.model.QuizQuestion;

/**
 * <h1>Design Pattern: Strategy (Grading Strategy Interface)</h1>
 * <p>
 * <b>Why chosen:</b> Different types of DSA questions require different grading logic,
 * XP bonus multipliers, and feedback messages. For example:
 * <ul>
 *   <li>Standard MCQs grant base XP with straightforward correctness checks.</li>
 *   <li>Code-trace questions grant higher bonus XP for multi-line logic analysis.</li>
 *   <li>Complexity questions evaluate asymptotic rigor and provide Big-O hints.</li>
 * </ul>
 * By encapsulating each algorithm in a strategy class, new question formats can be introduced
 * without altering existing quiz controllers.
 * </p>
 */
public interface QuizGradingStrategy {

    /**
     * Grades the user's selected answer.
     *
     * @param question the question being answered
     * @param selectedOptionIndex the index selected by the user
     * @param timeSpentSeconds time taken to answer the question
     * @return a {@link GradingResult} containing correctness, XP earned, and tailored feedback
     */
    GradingResult grade(QuizQuestion question, int selectedOptionIndex, int timeSpentSeconds);

    /**
     * Result payload containing calculated points and contextual coaching message.
     */
    record GradingResult(
        boolean isCorrect,
        int xpEarned,
        String feedbackTitle,
        String feedbackMessage,
        int streakBonus
    ) {}
}
