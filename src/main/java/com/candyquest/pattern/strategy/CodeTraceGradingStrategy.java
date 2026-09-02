package com.candyquest.pattern.strategy;

import com.candyquest.model.QuizQuestion;

/**
 * Concrete Strategy for Code-Tracing & Output Prediction Questions.
 * Grants a higher base reward (1.5x) for tracing Java algorithms correctly.
 */
public class CodeTraceGradingStrategy implements QuizGradingStrategy {
    @Override
    public GradingResult grade(QuizQuestion question, int selectedOptionIndex, int timeSpentSeconds) {
        boolean correct = question.isCorrect(selectedOptionIndex);
        if (correct) {
            int baseReward = (int) (question.getXpReward() * 1.5);
            return new GradingResult(true, baseReward, "⚡ Master Code Tracer!", 
                "Spot-on execution! " + question.getExplanation(), 1);
        } else {
            return new GradingResult(false, 0, "🐛 Logic Bug Found!", 
                "Trace line-by-line: " + question.getExplanation(), 0);
        }
    }
}
