package com.candyquest.controller;

import com.candyquest.config.AnimationConfig;
import com.candyquest.model.QuizQuestion;
import com.candyquest.model.Topic;
import com.candyquest.pattern.builder.QuizBuilder;
import com.candyquest.pattern.strategy.QuizGradingStrategy;
import com.candyquest.service.MascotService;
import com.candyquest.service.ProgressService;
import com.candyquest.service.QuizService;
import com.candyquest.service.SoundService;
import com.candyquest.service.TopicService;
import com.candyquest.view.component.CandyRooSprite;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Interactive Quiz Challenge Mode.
 */
public class QuizController {
    private final Topic topic;
    private final TopicService topicService;
    private final QuizService quizService;
    private final ProgressService progressService;
    private final MascotService mascotService;
    private final MainLayoutController mainLayout;

    private final QuizBuilder.QuizSession session;
    private int currentQuestionIndex = 0;
    private int totalEarnedXp = 0;
    private int correctAnswersCount = 0;
    private boolean answeredCurrentQuestion = false;

    private final VBox rootView;
    private final ProgressBar questionProgressBar;
    private final Label progressTextLabel;
    private final Label questionTypeBadge;
    private final Label questionTextLabel;
    private final TextArea codeSnippetArea;
    private final VBox optionsContainer;
    private final Label feedbackLabel;
    private final Button btnNextQuestion;
    private final CandyRooSprite mascotSprite;

    public QuizController(Topic topic, TopicService topicService, QuizService quizService, 
                          ProgressService progressService, MascotService mascotService, 
                          MainLayoutController mainLayout) {
        this.topic = topic;
        this.topicService = topicService;
        this.quizService = quizService;
        this.progressService = progressService;
        this.mascotService = mascotService;
        this.mainLayout = mainLayout;

        this.session = quizService.createTopicQuiz(topic);

        rootView = new VBox(16);
        rootView.setPadding(new Insets(20, 32, 20, 32));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        questionProgressBar = new ProgressBar(0.0);
        questionProgressBar.setPrefWidth(300);
        questionProgressBar.setStyle("-fx-accent: #E63946;");

        progressTextLabel = new Label();
        progressTextLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #FFB703;");

        questionTypeBadge = new Label();
        questionTypeBadge.setStyle("-fx-background-color: #242B45; -fx-text-fill: #2EC4B6; -fx-padding: 4 10; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 11px;");

        questionTextLabel = new Label();
        questionTextLabel.setWrapText(true);
        questionTextLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        codeSnippetArea = new TextArea();
        codeSnippetArea.setWrapText(true);
        codeSnippetArea.setEditable(false);
        codeSnippetArea.setPrefHeight(100);
        codeSnippetArea.setStyle("""
            -fx-control-inner-background: #0D1117;
            -fx-text-fill: #7EE787;
            -fx-font-family: 'Consolas', monospace;
            -fx-font-size: 12px;
        """);

        optionsContainer = new VBox(10);
        optionsContainer.setAlignment(Pos.CENTER_LEFT);

        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);
        feedbackLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8;");

        btnNextQuestion = new Button("Next Question ➡");
        btnNextQuestion.setStyle("""
            -fx-background-color: #E63946;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-cursor: hand;
        """);
        btnNextQuestion.setVisible(false);
        btnNextQuestion.setOnAction(e -> handleNextQuestion());

        mascotSprite = new CandyRooSprite();

        buildView();
        displayCurrentQuestion();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Top Bar
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("⬅ Exit Quiz");
        btnBack.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-padding: 6 14;
            -fx-cursor: hand;
        """);
        btnBack.setOnAction(e -> mainLayout.showTopicDetailView(topic));

        Label quizTitleLabel = new Label("🎯 " + session.title());
        quizTitleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 900; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox progressCol = new HBox(10, questionProgressBar, progressTextLabel);
        progressCol.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(btnBack, quizTitleLabel, spacer, progressCol);

        // Question Card
        VBox questionCard = new VBox(12);
        questionCard.setPadding(new Insets(18));
        questionCard.setStyle("""
            -fx-background-color: #16213E;
            -fx-background-radius: 16;
            -fx-border-color: rgba(255, 255, 255, 0.15);
            -fx-border-radius: 16;
        """);
        questionCard.getChildren().addAll(questionTypeBadge, questionTextLabel, codeSnippetArea, optionsContainer, feedbackLabel);

        // Bottom Action Bar
        HBox bottomBar = new HBox(16);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.getChildren().add(btnNextQuestion);

        rootView.getChildren().addAll(topBar, mascotSprite, questionCard, bottomBar);
    }

    private void displayCurrentQuestion() {
        answeredCurrentQuestion = false;
        btnNextQuestion.setVisible(false);
        feedbackLabel.setText("");
        feedbackLabel.setStyle("");

        List<QuizQuestion> questions = session.questions();
        if (currentQuestionIndex >= questions.size()) {
            showQuizResults();
            return;
        }

        QuizQuestion q = questions.get(currentQuestionIndex);
        double progress = (double) (currentQuestionIndex + 1) / questions.size();
        questionProgressBar.setProgress(progress);
        progressTextLabel.setText(String.format("Question %d of %d", currentQuestionIndex + 1, questions.size()));

        questionTypeBadge.setText(q.getType().getIcon() + " " + q.getType().getLabel());
        questionTextLabel.setText(q.getQuestionText());

        if (q.getCodeSnippet() != null && !q.getCodeSnippet().isBlank()) {
            codeSnippetArea.setVisible(true);
            codeSnippetArea.setManaged(true);
            codeSnippetArea.setText(q.getCodeSnippet().stripIndent());
        } else {
            codeSnippetArea.setVisible(false);
            codeSnippetArea.setManaged(false);
        }

        optionsContainer.getChildren().clear();
        for (int i = 0; i < q.getOptions().size(); i++) {
            final int optionIndex = i;
            String optionText = q.getOptions().get(i);

            Button optionBtn = new Button((char)('A' + i) + ")  " + optionText);
            optionBtn.setMaxWidth(Double.MAX_VALUE);
            optionBtn.setAlignment(Pos.CENTER_LEFT);
            optionBtn.setStyle("""
                -fx-background-color: #242B45;
                -fx-text-fill: #F1FAEE;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10;
                -fx-padding: 12 16;
                -fx-cursor: hand;
                -fx-border-color: #3A3E59;
                -fx-border-radius: 10;
            """);

            optionBtn.setOnAction(e -> handleOptionSelected(q, optionIndex, optionBtn));
            optionsContainer.getChildren().add(optionBtn);
        }

        mascotSprite.say("Read carefully and choose the correct sweet answer!");
    }

    private void handleOptionSelected(QuizQuestion question, int selectedIndex, Button selectedBtn) {
        if (answeredCurrentQuestion) return;
        answeredCurrentQuestion = true;

        QuizGradingStrategy.GradingResult result = quizService.evaluateAnswer(question, selectedIndex, 10);

        // Soft chew squish animation
        ScaleTransition squish = new ScaleTransition(AnimationConfig.BUTTON_PRESS_DURATION, selectedBtn);
        squish.setToX(AnimationConfig.BUTTON_SQUISH_SCALE_X);
        squish.setToY(AnimationConfig.BUTTON_SQUISH_SCALE_Y);
        squish.setAutoReverse(true);
        squish.setCycleCount(2);
        squish.play();

        // Lock all buttons and highlight colors
        for (int i = 0; i < optionsContainer.getChildren().size(); i++) {
            Button btn = (Button) optionsContainer.getChildren().get(i);
            btn.setDisable(true);
            if (i == question.getCorrectOptionIndex()) {
                btn.setStyle("""
                    -fx-background-color: #2EC4B6;
                    -fx-text-fill: #1A1A2E;
                    -fx-font-weight: 900;
                    -fx-font-size: 14px;
                    -fx-background-radius: 10;
                    -fx-padding: 12 16;
                """);
            } else if (i == selectedIndex && !result.isCorrect()) {
                btn.setStyle("""
                    -fx-background-color: #E63946;
                    -fx-text-fill: white;
                    -fx-font-weight: 900;
                    -fx-font-size: 14px;
                    -fx-background-radius: 10;
                    -fx-padding: 12 16;
                """);
            }
        }

        if (result.isCorrect()) {
            correctAnswersCount++;
            totalEarnedXp += result.xpEarned();
            feedbackLabel.setText(result.feedbackTitle() + " " + result.feedbackMessage());
            feedbackLabel.setStyle("-fx-text-fill: #2EC4B6; -fx-font-weight: bold;");

            SoundService.getInstance().playCorrectSweetPop();
            if (mainLayout.getGlobalParticlePane() != null) {
                mainLayout.getGlobalParticlePane().spawnFallingCandyRain(AnimationConfig.QUIZ_SUCCESS_PARTICLE_COUNT);
            }
            mascotSprite.setMood(MascotService.MascotMood.CHEERING_CLAP, mascotService.getCelebrationMessage(result.xpEarned()));
        } else {
            feedbackLabel.setText(result.feedbackTitle() + " " + result.feedbackMessage());
            feedbackLabel.setStyle("-fx-text-fill: #FF5D8F; -fx-font-weight: bold;");

            SoundService.getInstance().playIncorrectThud();
            mascotSprite.setMood(MascotService.MascotMood.CONFUSED_SHRUG, mascotService.getEncouragingMessage());
        }

        btnNextQuestion.setVisible(true);
    }

    private void handleNextQuestion() {
        currentQuestionIndex++;
        displayCurrentQuestion();
    }

    private void showQuizResults() {
        rootView.getChildren().clear();

        int totalQuestions = session.questions().size();
        int scorePercentage = (int) (((double) correctAnswersCount / totalQuestions) * 100);
        boolean passed = scorePercentage >= session.passingScorePercentage();

        if (passed) {
            progressService.completeTopicWithQuiz(topic, scorePercentage, totalEarnedXp);
            SoundService.getInstance().playLevelUpFanfare();
        }

        VBox resultsCard = new VBox(16);
        resultsCard.setAlignment(Pos.CENTER);
        resultsCard.setPadding(new Insets(32));
        resultsCard.setStyle("""
            -fx-background-color: #16213E;
            -fx-background-radius: 20;
            -fx-border-color: %s;
            -fx-border-width: 2.5;
            -fx-border-radius: 20;
        """.formatted(passed ? "#2EC4B6" : "#E63946"));

        Label resultTitle = new Label(passed ? "🎉 TOPIC CLEARED!" : "🍭 KEEP PRACTICING!");
        resultTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: " + (passed ? "#2EC4B6" : "#FF5D8F") + ";");

        Label scoreLabel = new Label(String.format("Score: %d%% (%d / %d Correct)", scorePercentage, correctAnswersCount, totalQuestions));
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label xpEarnedLabel = new Label("🍬 +" + totalEarnedXp + " Total XP Earned!");
        xpEarnedLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFB703;");

        CandyRooSprite finalMascot = new CandyRooSprite();
        if (passed) {
            finalMascot.setMood(MascotService.MascotMood.CHEERING_CLAP, "Spectacular! Another sweet DSA concept added to your candy jar!");
        } else {
            finalMascot.setMood(MascotService.MascotMood.CONFUSED_SHRUG, "Almost there! Review the concept and try the quiz again to unlock mastery!");
        }

        HBox actionRow = new HBox(16);
        actionRow.setAlignment(Pos.CENTER);

        Button btnRetry = new Button("🔄 Retake Quiz");
        btnRetry.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);
        btnRetry.setOnAction(e -> mainLayout.showQuizView(topic));

        Button btnNextTopic = new Button("➡ Next Candy Topic");
        btnNextTopic.setStyle("""
            -fx-background-color: #E63946;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 900;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-cursor: hand;
        """);
        btnNextTopic.setOnAction(e -> {
            Topic next = topicService.getNextTopic(topic.getId());
            if (next != null) {
                mainLayout.showTopicDetailView(next);
            } else {
                mainLayout.showTrackMapView(topic.getTrack());
            }
        });

        actionRow.getChildren().addAll(btnRetry, btnNextTopic);
        resultsCard.getChildren().addAll(resultTitle, scoreLabel, xpEarnedLabel, finalMascot, actionRow);

        rootView.getChildren().add(resultsCard);
    }
}
