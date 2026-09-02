package com.candyquest.pattern.builder;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.QuizQuestionType;
import com.candyquest.model.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>Design Pattern: Builder</h1>
 * <p>
 * <b>Why chosen:</b> Assembling customized challenge quizzes (e.g. "5-question Strawberry Speed Run",
 * "Hard DP Boss Fight", or "Randomized Track Mastery Exam") involves numerous optional parameters:
 * target track, difficulty filters, question count, time limits, question type distribution,
 * and passing thresholds. The Builder pattern provides a fluent, expressive API to construct
 * these quizzes step-by-step while ensuring validation.
 * </p>
 */
public class QuizBuilder {
    private String quizTitle;
    private Track track;
    private int targetDifficulty = 1;
    private int timeLimitSeconds = 120;
    private int passingScorePercentage = 70;
    private boolean shuffleQuestions = true;
    private final List<QuizQuestion> questions = new ArrayList<>();

    public QuizBuilder() {
        this.quizTitle = "Candy Challenge";
    }

    public QuizBuilder withTitle(String title) {
        this.quizTitle = title;
        return this;
    }

    public QuizBuilder forTrack(Track track) {
        this.track = track;
        return this;
    }

    public QuizBuilder withDifficulty(int difficulty) {
        this.targetDifficulty = Math.max(1, Math.min(5, difficulty));
        return this;
    }

    public QuizBuilder withTimeLimitSeconds(int seconds) {
        this.timeLimitSeconds = seconds;
        return this;
    }

    public QuizBuilder withPassingScore(int percentage) {
        this.passingScorePercentage = percentage;
        return this;
    }

    public QuizBuilder shuffle(boolean shuffle) {
        this.shuffleQuestions = shuffle;
        return this;
    }

    public QuizBuilder addQuestion(QuizQuestion question) {
        if (question != null) {
            this.questions.add(question);
        }
        return this;
    }

    public QuizBuilder addQuestions(List<QuizQuestion> questionList) {
        if (questionList != null) {
            this.questions.addAll(questionList);
        }
        return this;
    }

    public QuizBuilder addQuickMcq(String id, String questionText, List<String> options, 
                                  int correctIndex, String explanation, int xp) {
        QuizQuestion q = new QuizQuestion(id, QuizQuestionType.MCQ, questionText, null, 
                                          options, correctIndex, explanation, xp);
        this.questions.add(q);
        return this;
    }

    public QuizSession build() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("Cannot build a quiz with no questions!");
        }
        List<QuizQuestion> finalQuestions = new ArrayList<>(questions);
        if (shuffleQuestions) {
            Collections.shuffle(finalQuestions);
        }
        return new QuizSession(quizTitle, track, targetDifficulty, timeLimitSeconds, 
                               passingScorePercentage, finalQuestions);
    }

    /**
     * Immutable Product of QuizBuilder.
     */
    public record QuizSession(
        String title,
        Track track,
        int difficulty,
        int timeLimitSeconds,
        int passingScorePercentage,
        List<QuizQuestion> questions
    ) {
        public int getTotalPossibleXp() {
            return questions.stream().mapToInt(QuizQuestion::getXpReward).sum();
        }
    }
}
