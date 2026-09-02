package com.candyquest.pattern.strategy;

import com.candyquest.model.QuizQuestion;

/**
 * Concrete Strategy for Multiple Choice Questions.
 */
public class McqGradingStrategy implements QuizGradingStrategy {
    @Override
    public GradingResult grade(QuizQuestion question, int selectedOptionIndex, int timeSpentSeconds) {
        boolean correct = question.isCorrect(selectedOptionIndex);
        if (correct) {
            int speedBonus = (timeSpentSeconds <= 10) ? 5 : 0;
            int totalXp = question.getXpReward() + speedBonus;
            String feedbackMsg = "Sweet! " + question.getExplanation();
            if (speedBonus > 0) {
                feedbackMsg += " (+5 Speed Bonus!)";
            }
            return new GradingResult(true, totalXp, "🍬 Sweet Move!", feedbackMsg, 1);
        } else {
            return new GradingResult(false, 0, "🍭 Oops, Chewy Try!", 
                "Not quite! " + question.getExplanation(), 0);
        }
    }
}
