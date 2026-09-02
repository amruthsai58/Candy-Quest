package com.candyquest.service;

import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.pattern.builder.QuizBuilder;
import com.candyquest.pattern.command.AnswerSubmittedCommand;
import com.candyquest.pattern.command.CommandHistory;
import com.candyquest.pattern.strategy.GradingStrategyFactory;
import com.candyquest.pattern.strategy.QuizGradingStrategy;

/**
 * Service managing quiz sessions, grading strategies, and answer submissions.
 */
public class QuizService {
    private final CommandHistory commandHistory;

    public QuizService(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    public QuizBuilder.QuizSession createTopicQuiz(Topic topic) {
        return new QuizBuilder()
            .withTitle(topic.getName() + " Challenge")
            .forTrack(topic.getTrack())
            .withDifficulty(topic.getDifficulty())
            .withTimeLimitSeconds(90)
            .withPassingScore(60)
            .addQuestions(topic.getQuizQuestions())
            .shuffle(false)
            .build();
    }

    public QuizGradingStrategy.GradingResult evaluateAnswer(QuizQuestion question, int selectedIndex, int timeSpentSeconds) {
        QuizGradingStrategy strategy = GradingStrategyFactory.getStrategy(question.getType());
        QuizGradingStrategy.GradingResult result = strategy.grade(question, selectedIndex, timeSpentSeconds);

        AnswerSubmittedCommand cmd = new AnswerSubmittedCommand(
            question, selectedIndex, result.isCorrect(), result.xpEarned()
        );
        commandHistory.executeCommand(cmd);

        return result;
    }
}
