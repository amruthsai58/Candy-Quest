package com.candyquest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a single quiz challenge for a topic.
 */
public class QuizQuestion {
    private String id;
    private QuizQuestionType type;
    private String questionText;
    private String codeSnippet; // Optional code snippet to trace
    private List<String> options;
    private int correctOptionIndex;
    private String explanation;
    private int xpReward;

    public QuizQuestion() {
        this.options = new ArrayList<>();
        this.type = QuizQuestionType.MCQ;
        this.xpReward = 15;
    }

    public QuizQuestion(String id, QuizQuestionType type, String questionText, 
                        String codeSnippet, List<String> options, int correctOptionIndex, 
                        String explanation, int xpReward) {
        this.id = id;
        this.type = type;
        this.questionText = questionText;
        this.codeSnippet = codeSnippet;
        this.options = options != null ? options : new ArrayList<>();
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
        this.xpReward = xpReward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuizQuestionType getType() {
        return type;
    }

    public void setType(QuizQuestionType type) {
        this.type = type;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public boolean isCorrect(int selectedIndex) {
        return selectedIndex == correctOptionIndex;
    }
}
