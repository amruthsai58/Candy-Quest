package com.candyquest.controller;

import com.candyquest.model.Topic;
import com.candyquest.pattern.state.TopicStateContext;
import com.candyquest.service.MascotService;
import com.candyquest.service.ProgressService;
import com.candyquest.service.QuizService;
import com.candyquest.service.TopicService;
import com.candyquest.view.component.CandyRooSprite;
import com.candyquest.view.component.visualizer.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Controller for the Topic Detail View: explanation, visualizer, code snippets, and actions.
 */
public class TopicDetailController {
    private final Topic topic;
    private final TopicService topicService;
    private final ProgressService progressService;
    private final QuizService quizService;
    private final MascotService mascotService;
    private final MainLayoutController mainLayout;

    private final VBox rootView;
    private final CandyRooSprite mascotSprite;

    public TopicDetailController(Topic topic, TopicService topicService, ProgressService progressService,
                                 QuizService quizService, MascotService mascotService,
                                 MainLayoutController mainLayout) {
        this.topic = topic;
        this.topicService = topicService;
        this.progressService = progressService;
        this.quizService = quizService;
        this.mascotService = mascotService;
        this.mainLayout = mainLayout;

        topicService.openTopic(topic);

        rootView = new VBox(14);
        rootView.setPadding(new Insets(18, 28, 18, 28));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        mascotSprite = new CandyRooSprite();

        buildView();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Navigation & Topic Header
        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("⬅ Back to Trail");
        btnBack.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 13px;
            -fx-background-radius: 12;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        btnBack.setOnAction(e -> mainLayout.showTrackMapView(topic.getTrack()));

        VBox titleBox = new VBox(2);
        Label seqAndTrack = new Label(topic.getTrack().getEmoji() + " " + topic.getTrack().getFlavorName() + " • Topic #" + topic.getSequenceNumber());
        seqAndTrack.setStyle("-fx-font-size: 12px; -fx-text-fill: " + topic.getTrack().getPrimaryColor() + "; -fx-font-weight: bold;");

        Label titleLabel = new Label(topic.getName());
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: white;");

        titleBox.getChildren().addAll(seqAndTrack, titleLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Meta Badges (Difficulty & Tag)
        HBox metaBadges = new HBox(8);
        metaBadges.setAlignment(Pos.CENTER_RIGHT);

        Label diffBadge = new Label(topic.getDifficultyStars());
        diffBadge.setStyle("-fx-background-color: #242B45; -fx-text-fill: #FFB703; -fx-padding: 6 12; -fx-background-radius: 8; -fx-font-weight: bold;");

        Label tagBadge = new Label("🏷️ " + topic.getTag());
        tagBadge.setStyle("-fx-background-color: #242B45; -fx-text-fill: #2EC4B6; -fx-padding: 6 12; -fx-background-radius: 8; -fx-font-weight: bold;");

        metaBadges.getChildren().addAll(diffBadge, tagBadge);
        topBar.getChildren().addAll(btnBack, titleBox, spacer, metaBadges);

        // Action Toolbar
        HBox actionToolbar = new HBox(12);
        actionToolbar.setAlignment(Pos.CENTER_LEFT);

        Button btnQuiz = new Button("🎯 Launch Quiz Challenge");
        btnQuiz.setStyle("""
            -fx-background-color: #E63946;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: 900;
            -fx-background-radius: 12;
            -fx-padding: 10 20;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(230, 57, 70, 0.4), 8, 0, 0, 2);
        """);
        btnQuiz.setOnAction(e -> mainLayout.showQuizView(topic));

        Button btnPlayground = new Button("💻 Practice in Playground");
        btnPlayground.setStyle("""
            -fx-background-color: #FB8500;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 18;
            -fx-cursor: hand;
        """);
        btnPlayground.setOnAction(e -> mainLayout.showPlaygroundView());

        Button btnBookmark = new Button(topic.isBookmarked() ? "⭐ Bookmarked" : "☆ Bookmark");
        btnBookmark.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 16;
            -fx-cursor: hand;
        """);
        btnBookmark.setOnAction(e -> {
            topicService.toggleBookmark(topic);
            btnBookmark.setText(topic.isBookmarked() ? "⭐ Bookmarked" : "☆ Bookmark");
        });

        Button btnHint = new Button("💡 Ask Roo for Hint");
        btnHint.setStyle("""
            -fx-background-color: #7209B7;
            -fx-text-fill: white;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 10 16;
            -fx-cursor: hand;
        """);
        btnHint.setOnAction(e -> {
            String hint = mascotService.requestTopicHint(topic);
            mascotSprite.setMood(MascotService.MascotMood.POINTING_HINT, hint);
        });

        actionToolbar.getChildren().addAll(btnQuiz, btnPlayground, btnBookmark, btnHint);

        // Mascot Dialogue Bubble
        mascotSprite.say("Welcome to " + topic.getName() + "! Study the concept below or interact with the animated visualizer!");

        // Content Area Split (Left: Explanation & Code, Right: Visualizer)
        HBox contentSplit = new HBox(16);
        contentSplit.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(contentSplit, Priority.ALWAYS);

        // Left Side: Explanation & Code tabs
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #16213E; -fx-background-radius: 12;");
        tabPane.setPrefWidth(520);
        HBox.setHgrow(tabPane, Priority.ALWAYS);

        // Tab 1: Theory & Concept
        Tab theoryTab = new Tab("📖 Concept Deep-Dive");
        theoryTab.setClosable(false);
        TextArea explanationArea = new TextArea(topic.getExplanation());
        explanationArea.setWrapText(true);
        explanationArea.setEditable(false);
        explanationArea.setStyle("""
            -fx-control-inner-background: #16213E;
            -fx-text-fill: #F1FAEE;
            -fx-font-family: 'Segoe UI', 'Outfit', sans-serif;
            -fx-font-size: 13px;
            -fx-padding: 12;
        """);
        theoryTab.setContent(explanationArea);

        // Tab 2: Java Code Example
        Tab codeTab = new Tab("☕ Java Implementation");
        codeTab.setClosable(false);
        TextArea codeArea = new TextArea(topic.getJavaCodeExample());
        codeArea.setWrapText(true);
        codeArea.setEditable(false);
        codeArea.setStyle("""
            -fx-control-inner-background: #0D1117;
            -fx-text-fill: #7EE787;
            -fx-font-family: 'Consolas', 'Courier New', monospace;
            -fx-font-size: 13px;
            -fx-padding: 12;
        """);
        codeTab.setContent(codeArea);

        tabPane.getTabs().addAll(theoryTab, codeTab);

        // Right Side: Interactive Algorithm Visualizer
        VBox visualizerCol = new VBox(10);
        visualizerCol.setPrefWidth(420);
        visualizerCol.setMaxWidth(450);

        AlgorithmVisualizer visualizer = createVisualizerForTopic(topic);
        visualizerCol.getChildren().add(visualizer.getViewNode());

        contentSplit.getChildren().addAll(tabPane, visualizerCol);

        rootView.getChildren().addAll(topBar, actionToolbar, mascotSprite, contentSplit);
    }

    private AlgorithmVisualizer createVisualizerForTopic(Topic topic) {
        String visType = topic.getVisualizerType();
        if (visType == null) visType = "ARRAY";

        return switch (visType.toUpperCase()) {
            case "LINKED_LIST" -> new LinkedListVisualizer();
            case "TREE" -> new TreeVisualizer();
            case "RECURSION" -> new RecursionVisualizer();
            default -> new ArrayVisualizer();
        };
    }
}
