package com.candyquest.model;

/**
 * Supported Question Types for Candy Quest interactive quizzes.
 */
public enum QuizQuestionType {
    MCQ("Multiple Choice", "💡"),
    CODE_TRACE("Code Output Prediction", "💻"),
    COMPLEXITY("Time & Space Complexity", "⏱️");

    private final String label;
    private final String icon;

    QuizQuestionType(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }
}
