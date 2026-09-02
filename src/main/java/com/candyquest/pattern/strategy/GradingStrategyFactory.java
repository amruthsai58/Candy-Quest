package com.candyquest.pattern.strategy;

import com.candyquest.model.QuizQuestionType;

/**
 * Helper factory to resolve the appropriate {@link QuizGradingStrategy}.
 */
public class GradingStrategyFactory {
    private static final McqGradingStrategy MCQ_STRATEGY = new McqGradingStrategy();
    private static final CodeTraceGradingStrategy CODE_TRACE_STRATEGY = new CodeTraceGradingStrategy();
    private static final ComplexityGradingStrategy COMPLEXITY_STRATEGY = new ComplexityGradingStrategy();

    public static QuizGradingStrategy getStrategy(QuizQuestionType type) {
        if (type == null) return MCQ_STRATEGY;
        return switch (type) {
            case MCQ -> MCQ_STRATEGY;
            case CODE_TRACE -> CODE_TRACE_STRATEGY;
            case COMPLEXITY -> COMPLEXITY_STRATEGY;
        };
    }
}
