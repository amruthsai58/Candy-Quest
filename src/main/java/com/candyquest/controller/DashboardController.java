package com.candyquest.controller;

import com.candyquest.model.Topic;
import com.candyquest.model.ToyReward;
import com.candyquest.model.Track;
import com.candyquest.model.UserProfile;
import com.candyquest.pattern.command.UserActionCommand;
import com.candyquest.pattern.decorator.BadgeComponent;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.service.ProgressService;
import com.candyquest.service.RewardService;
import com.candyquest.service.TopicService;
import com.candyquest.view.component.CandyJarView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Controller for the Progress Dashboard: 4 Animated Candy Jars, Decorated Badges,
 * Unlocked Toys, and Command History Log.
 */
public class DashboardController {
    private final TopicService topicService;
    private final ProgressService progressService;
    private final RewardService rewardService;
    private final MainLayoutController mainLayout;

    private final VBox rootView;

    public DashboardController(TopicService topicService, ProgressService progressService,
                               RewardService rewardService, MainLayoutController mainLayout) {
        this.topicService = topicService;
        this.progressService = progressService;
        this.rewardService = rewardService;
        this.mainLayout = mainLayout;

        rootView = new VBox(20);
        rootView.setPadding(new Insets(20, 28, 20, 28));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        buildView();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Dashboard Title
        HBox headerRow = new HBox(16);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Button btnHome = new Button("⬅ Back to Home");
        btnHome.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        btnHome.setOnAction(e -> mainLayout.showHomeView());

        UserProfile user = AppSessionManager.getInstance().getCurrentUser();
        Label title = new Label("📊 " + user.getUsername() + "'s Candy Confectionary Dashboard");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: white;");

        headerRow.getChildren().addAll(btnHome, title);

        // Overall Stats Banner
        HBox statsCard = new HBox(24);
        statsCard.setAlignment(Pos.CENTER);
        statsCard.setPadding(new Insets(16));
        statsCard.setStyle("-fx-background-color: #16213E; -fx-background-radius: 14; -fx-border-color: #E63946; -fx-border-width: 1.5; -fx-border-radius: 14;");

        int totalCompleted = AppSessionManager.getInstance().getCurrentProgress().getCompletedCount();
        statsCard.getChildren().addAll(
            createStatPill("🍬 Total XP", String.valueOf(user.getTotalXp()), "#FFB703"),
            createStatPill("⭐ Level", String.valueOf(user.getLevel()), "#2EC4B6"),
            createStatPill("🔥 Streak", user.getStreakDays() + " Days", "#FB8500"),
            createStatPill("🏆 Topics Cleared", totalCompleted + " / 150", "#E63946")
        );

        // 4 Candy Jars Section
        Label jarsSectionTitle = new Label("🏺 Flavor Track Progress Jars");
        jarsSectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFD166;");

        HBox jarsRow = new HBox(20);
        jarsRow.setAlignment(Pos.CENTER);
        for (Track track : Track.values()) {
            CandyJarView jar = new CandyJarView(track);
            jar.setCursor(javafx.scene.Cursor.HAND);
            jar.setOnMouseClicked(e -> mainLayout.showTrackMapView(track));

            List<Topic> trackTopics = topicService.getTopicsForTrack(track);
            int count = AppSessionManager.getInstance().getCurrentProgress().getCompletedCountForTrack(
                track,
                topicService.getAllTopics().stream().collect(java.util.stream.Collectors.toMap(Topic::getId, t -> t))
            );
            jar.updateProgress(count, trackTopics.size());
            jarsRow.getChildren().add(jar);
        }

        // Achievements & Badges Shelf (Using Decorator Pattern)
        Label badgeSectionTitle = new Label("🏆 Achievement Badges (Decorated)");
        badgeSectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFD166;");

        FlowPane badgesGrid = new FlowPane();
        badgesGrid.setHgap(12);
        badgesGrid.setVgap(12);
        badgesGrid.setPadding(new Insets(12));
        badgesGrid.setStyle("-fx-background-color: #16213E; -fx-background-radius: 14;");

        List<BadgeComponent> decoratedBadges = rewardService.getDecoratedBadges();
        for (BadgeComponent badgeComp : decoratedBadges) {
            VBox badgeCard = new VBox(6);
            badgeCard.setAlignment(Pos.CENTER);
            badgeCard.setPadding(new Insets(10));
            badgeCard.setPrefWidth(180);
            badgeCard.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 10;
                -fx-border-color: %s;
                -fx-border-width: 1.5;
                -fx-border-radius: 10;
            """.formatted(
                badgeComp.getBadge().isUnlocked() ? "#242B45" : "#1A1A2E",
                badgeComp.getBadge().isUnlocked() ? "#FFB703" : "#3A3E59"
            ));

            Label icon = new Label(badgeComp.getBadge().getIconEmoji());
            icon.setStyle("-fx-font-size: 28px;");

            Label name = new Label(badgeComp.getDisplayName());
            name.setWrapText(true);
            name.setAlignment(Pos.CENTER);
            name.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + 
                          (badgeComp.getBadge().isUnlocked() ? "white" : "#64748B") + "; -fx-text-alignment: center;");

            Label desc = new Label(badgeComp.getBadge().getDescription());
            desc.setWrapText(true);
            desc.setAlignment(Pos.CENTER);
            desc.setStyle("-fx-font-size: 10px; -fx-text-fill: #94A3B8; -fx-text-alignment: center;");

            badgeCard.getChildren().addAll(icon, name, desc);
            badgesGrid.getChildren().add(badgeCard);
        }

        // Free Toy Inside Collection
        Label toySectionTitle = new Label("🎁 'Free Toy Inside' Collection (Every 10 Topics)");
        toySectionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFD166;");

        FlowPane toysGrid = new FlowPane();
        toysGrid.setHgap(12);
        toysGrid.setVgap(12);
        toysGrid.setPadding(new Insets(12));
        toysGrid.setStyle("-fx-background-color: #16213E; -fx-background-radius: 14;");

        for (ToyReward toy : rewardService.getAllToys()) {
            VBox toyCard = new VBox(4);
            toyCard.setAlignment(Pos.CENTER);
            toyCard.setPadding(new Insets(10));
            toyCard.setPrefWidth(160);
            toyCard.setStyle("""
                -fx-background-color: %s;
                -fx-background-radius: 10;
                -fx-border-color: %s;
                -fx-border-width: 1.5;
                -fx-border-radius: 10;
            """.formatted(toy.isClaimed() ? "#242B45" : "#1A1A2E", toy.isClaimed() ? "#2EC4B6" : "#3A3E59"));

            Label icon = new Label(toy.getIconEmoji());
            icon.setStyle("-fx-font-size: 26px;");

            Label titleLbl = new Label(toy.getTitle());
            titleLbl.setWrapText(true);
            titleLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + (toy.isClaimed() ? "#2EC4B6" : "#64748B") + "; -fx-text-alignment: center;");

            Label unlockReq = new Label(toy.isClaimed() ? "CLAIMED" : "Unlocks at " + toy.getUnlockThreshold() + " Topics");
            unlockReq.setStyle("-fx-font-size: 10px; -fx-text-fill: #FFB703; -fx-font-weight: bold;");

            toyCard.getChildren().addAll(icon, titleLbl, unlockReq);
            toysGrid.getChildren().add(toyCard);
        }

        // Action History Log (Command Pattern)
        Label historyTitle = new Label("📜 Recent Study Action History (Command Log)");
        historyTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #FFD166;");

        VBox historyBox = new VBox(6);
        historyBox.setPadding(new Insets(12));
        historyBox.setStyle("-fx-background-color: #0D1117; -fx-background-radius: 10; -fx-font-family: 'Consolas', monospace;");

        List<UserActionCommand> actions = topicService.getCommandHistory().getFullActionLog();
        if (actions.isEmpty()) {
            Label noHistory = new Label("No actions recorded yet. Start studying topics!");
            noHistory.setStyle("-fx-text-fill: #64748B; -fx-font-size: 12px;");
            historyBox.getChildren().add(noHistory);
        } else {
            for (int i = actions.size() - 1; i >= Math.max(0, actions.size() - 8); i--) {
                UserActionCommand cmd = actions.get(i);
                Label logLine = new Label("• [" + cmd.getTimestamp().toLocalTime().toString().substring(0, 8) + "] " + cmd.getDescription());
                logLine.setStyle("-fx-text-fill: #7EE787; -fx-font-size: 12px;");
                historyBox.getChildren().add(logLine);
            }
        }

        VBox scrollContent = new VBox(16, statsCard, jarsSectionTitle, jarsRow, badgeSectionTitle, badgesGrid, toySectionTitle, toysGrid, historyTitle, historyBox);
        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        rootView.getChildren().addAll(headerRow, scrollPane);
    }

    private VBox createStatPill(String title, String value, String colorHex) {
        VBox pill = new VBox(4);
        pill.setAlignment(Pos.CENTER);
        pill.setPadding(new Insets(8, 18, 8, 18));
        pill.setStyle("-fx-background-color: #242B45; -fx-background-radius: 10;");

        Label valLbl = new Label(value);
        valLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: 900; -fx-text-fill: " + colorHex + ";");

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #94A3B8; -fx-font-weight: bold;");

        pill.getChildren().addAll(valLbl, titleLbl);
        return pill;
    }
}
