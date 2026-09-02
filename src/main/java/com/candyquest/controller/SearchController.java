package com.candyquest.controller;

import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.service.ProgressService;
import com.candyquest.service.TopicService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

/**
 * Controller for searching, filtering, and bookmarking across all 150 DSA topics.
 */
public class SearchController {
    private final TopicService topicService;
    private final ProgressService progressService;
    private final MainLayoutController mainLayout;

    private final VBox rootView;
    private final TextField searchField;
    private final VBox resultsListContainer;
    private Track selectedTrackFilter = null;
    private Integer selectedDifficultyFilter = null;
    private boolean showOnlyBookmarked = false;

    public SearchController(TopicService topicService, ProgressService progressService,
                            MainLayoutController mainLayout) {
        this.topicService = topicService;
        this.progressService = progressService;
        this.mainLayout = mainLayout;

        rootView = new VBox(16);
        rootView.setPadding(new Insets(20, 28, 20, 28));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        searchField = new TextField();
        searchField.setPromptText("🔍 Search across all 150 topics by name, tag, or algorithm concept...");
        searchField.setStyle("""
            -fx-background-color: #16213E;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 12 16;
            -fx-background-radius: 12;
            -fx-border-color: #E63946;
            -fx-border-radius: 12;
            -fx-border-width: 1.5;
        """);

        resultsListContainer = new VBox(10);

        buildView();
        performSearch();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Top Header
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("⬅ Back to Home");
        btnBack.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        btnBack.setOnAction(e -> mainLayout.showHomeView());

        Label title = new Label("🔎 150 Topics Explorer & Bookmarks");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: white;");

        topBar.getChildren().addAll(btnBack, title);

        // Search Input
        searchField.textProperty().addListener((obs, oldVal, newVal) -> performSearch());

        // Track Filter Chips Row
        HBox filterChips = new HBox(10);
        filterChips.setAlignment(Pos.CENTER_LEFT);

        Button btnAll = createChipButton("All Tracks", null);
        filterChips.getChildren().add(btnAll);

        for (Track track : Track.values()) {
            Button trackChip = createChipButton(track.getEmoji() + " " + track.getFlavorName(), track);
            filterChips.getChildren().add(trackChip);
        }

        Button btnBookmarks = new Button("⭐ Bookmarked Only");
        btnBookmarks.setStyle("""
            -fx-background-color: #242B45;
            -fx-text-fill: #FFB703;
            -fx-font-weight: bold;
            -fx-font-size: 12px;
            -fx-padding: 6 14;
            -fx-background-radius: 20;
            -fx-cursor: hand;
        """);
        btnBookmarks.setOnAction(e -> {
            showOnlyBookmarked = !showOnlyBookmarked;
            btnBookmarks.setStyle(showOnlyBookmarked ?
                "-fx-background-color: #FFB703; -fx-text-fill: #1A1A2E; -fx-font-weight: 900; -fx-padding: 6 14; -fx-background-radius: 20;" :
                "-fx-background-color: #242B45; -fx-text-fill: #FFB703; -fx-font-weight: bold; -fx-padding: 6 14; -fx-background-radius: 20;");
            performSearch();
        });
        filterChips.getChildren().add(btnBookmarks);

        // Results Scroll View
        ScrollPane scrollPane = new ScrollPane(resultsListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        rootView.getChildren().addAll(topBar, searchField, filterChips, scrollPane);
    }

    private Button createChipButton(String label, Track track) {
        Button btn = new Button(label);
        btn.setStyle("""
            -fx-background-color: #242B45;
            -fx-text-fill: white;
            -fx-font-size: 12px;
            -fx-font-weight: bold;
            -fx-padding: 6 14;
            -fx-background-radius: 20;
            -fx-cursor: hand;
        """);
        btn.setOnAction(e -> {
            selectedTrackFilter = track;
            performSearch();
        });
        return btn;
    }

    private void performSearch() {
        resultsListContainer.getChildren().clear();
        String query = searchField.getText();
        List<Topic> matches = topicService.search(query, selectedTrackFilter, selectedDifficultyFilter);

        if (showOnlyBookmarked) {
            matches = matches.stream()
                .filter(t -> AppSessionManager.getInstance().getCurrentProgress().isBookmarked(t.getId()))
                .toList();
        }

        if (matches.isEmpty()) {
            Label empty = new Label("No candy topics match your query. Try broadening your keywords!");
            empty.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 14px; -fx-padding: 24;");
            resultsListContainer.getChildren().add(empty);
            return;
        }

        for (Topic topic : matches) {
            HBox itemRow = new HBox(12);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemRow.setPadding(new Insets(12, 16, 12, 16));
            itemRow.setStyle("""
                -fx-background-color: #16213E;
                -fx-background-radius: 12;
                -fx-border-color: rgba(255,255,255,0.08);
                -fx-border-radius: 12;
                -fx-cursor: hand;
            """);

            Label trackEmoji = new Label(topic.getTrack().getEmoji());
            trackEmoji.setStyle("-fx-font-size: 20px;");

            VBox infoCol = new VBox(2);
            Label nameLbl = new Label("#" + topic.getSequenceNumber() + " " + topic.getName());
            nameLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label subLbl = new Label(topic.getTag() + " • " + topic.getTimeComplexity() + " • " + topic.getSummary());
            subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #94A3B8;");
            infoCol.getChildren().addAll(nameLbl, subLbl);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label diffLbl = new Label(topic.getDifficultyStars());
            diffLbl.setStyle("-fx-text-fill: #FFB703; -fx-font-size: 12px;");

            Button btnBookmark = new Button(AppSessionManager.getInstance().getCurrentProgress().isBookmarked(topic.getId()) ? "⭐" : "☆");
            btnBookmark.setStyle("-fx-background-color: transparent; -fx-font-size: 16px; -fx-cursor: hand;");
            btnBookmark.setOnAction(e -> {
                topicService.toggleBookmark(topic);
                btnBookmark.setText(AppSessionManager.getInstance().getCurrentProgress().isBookmarked(topic.getId()) ? "⭐" : "☆");
            });

            itemRow.setOnMouseClicked(e -> {
                if (e.getTarget() != btnBookmark) {
                    mainLayout.showTopicDetailView(topic);
                }
            });

            itemRow.getChildren().addAll(trackEmoji, infoCol, spacer, diffLbl, btnBookmark);
            resultsListContainer.getChildren().add(itemRow);
        }
    }
}
