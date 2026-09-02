package com.candyquest.controller;

import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.model.UserProgress;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.pattern.state.TopicStateContext;
import com.candyquest.service.MascotService;
import com.candyquest.service.ProgressService;
import com.candyquest.service.TopicService;
import com.candyquest.view.component.CandyRooSprite;
import com.candyquest.view.component.TrackNodeButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Controller for the Track Map View displaying the board-game candy trail of topics.
 */
public class TrackMapController {
    private final Track track;
    private final TopicService topicService;
    private final ProgressService progressService;
    private final MascotService mascotService;
    private final MainLayoutController mainLayout;

    private final VBox rootView;

    public TrackMapController(Track track, TopicService topicService, ProgressService progressService,
                              MascotService mascotService, MainLayoutController mainLayout) {
        this.track = track;
        this.topicService = topicService;
        this.progressService = progressService;
        this.mascotService = mascotService;
        this.mainLayout = mainLayout;

        rootView = new VBox(16);
        rootView.setPadding(new Insets(20, 28, 20, 28));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        buildView();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Track Header Banner
        HBox headerRow = new HBox(16);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("⬅ Back to Worlds");
        btnBack.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 13px;
            -fx-background-radius: 12;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        btnBack.setOnAction(e -> mainLayout.showHomeView());

        VBox titleCol = new VBox(4);
        Label trackTitle = new Label(track.getEmoji() + " " + track.getFlavorName() + " — " + track.getDomainTitle());
        trackTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: " + track.getPrimaryColor() + ";");

        Label trackDesc = new Label(track.getDescription());
        trackDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #94A3B8;");
        titleCol.getChildren().addAll(trackTitle, trackDesc);

        headerRow.getChildren().addAll(btnBack, titleCol);

        // Progress Bar
        List<Topic> topics = topicService.getTopicsForTrack(track);
        UserProgress userProgress = AppSessionManager.getInstance().getCurrentProgress();
        long completedCount = topics.stream().filter(t -> userProgress.isTopicCompleted(t.getId())).count();
        double progressRatio = topics.isEmpty() ? 0.0 : (double) completedCount / topics.size();

        HBox progressRow = new HBox(12);
        progressRow.setAlignment(Pos.CENTER_LEFT);
        ProgressBar trackProgressBar = new ProgressBar(progressRatio);
        trackProgressBar.setPrefWidth(350);
        trackProgressBar.setStyle("-fx-accent: " + track.getPrimaryColor() + ";");

        Label progressLabel = new Label(String.format("%d of %d Topics Completed (%d%%)", 
                                        completedCount, topics.size(), (int) (progressRatio * 100)));
        progressLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #E2E8F0;");

        progressRow.getChildren().addAll(trackProgressBar, progressLabel);

        // Mascot Guide Bar
        CandyRooSprite mascot = new CandyRooSprite();
        mascot.say("Hop onto any unlocked candy drop node to study the concept and pass the quiz!");

        // Board Game Candy Trail Node Grid
        FlowPane trailGrid = new FlowPane();
        trailGrid.setHgap(16);
        trailGrid.setVgap(20);
        trailGrid.setPadding(new Insets(16));
        trailGrid.setAlignment(Pos.TOP_LEFT);
        trailGrid.setStyle("-fx-background-color: rgba(22, 33, 62, 0.7); -fx-background-radius: 16; -fx-border-color: rgba(255,255,255,0.1); -fx-border-radius: 16;");

        for (Topic topic : topics) {
            TopicStateContext stateContext = progressService.getTopicStateContext(topic);
            TrackNodeButton nodeBtn = new TrackNodeButton(
                topic, 
                stateContext.getCurrentState(), 
                selectedTopic -> mainLayout.showTopicDetailView(selectedTopic)
            );
            trailGrid.getChildren().add(nodeBtn);
        }

        ScrollPane scrollPane = new ScrollPane(trailGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        rootView.getChildren().addAll(headerRow, progressRow, mascot, scrollPane);
    }
}
