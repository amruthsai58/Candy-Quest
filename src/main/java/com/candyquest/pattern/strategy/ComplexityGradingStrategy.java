package com.candyquest.pattern.strategy;

import com.candyquest.model.QuizQuestion;

/**
 * Concrete Strategy for Asymptotic Time and Space Complexity Questions.
 */
public class ComplexityGradingStrategy implements QuizGradingStrategy {
    @Override
    public GradingResult grade(QuizQuestion question, int selectedOptionIndex, int timeSpentSeconds) {
        boolean correct = question.isCorrect(selectedOptionIndex);
        if (correct) {
            return new GradingResult(true, question.getXpReward() + 5, "⏱️ Big-O Guru!", 
                "Precise asymptotic analysis! " + question.getExplanation(), 1);
        } else {
            return new GradingResult(false, 0, "📉 Asymptotic Twist!", 
                "Check tight bounds: " + question.getExplanation(), 0);
        }
    }
}
