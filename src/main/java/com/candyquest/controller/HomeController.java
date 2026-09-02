package com.candyquest.controller;

import com.candyquest.config.AnimationConfig;
import com.candyquest.config.AppConfig;
import com.candyquest.model.Topic;
import com.candyquest.model.Track;
import com.candyquest.model.UserProgress;
import com.candyquest.pattern.singleton.AppSessionManager;
import com.candyquest.service.MascotService;
import com.candyquest.service.ProgressService;
import com.candyquest.service.TopicService;
import com.candyquest.view.component.CandyJarView;
import com.candyquest.view.component.CandyRooSprite;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Home Screen featuring the animated Candy Pack Burst intro
 * and the 4 Track flavor jars.
 */
public class HomeController {
    private final TopicService topicService;
    private final ProgressService progressService;
    private final MascotService mascotService;
    private final MainLayoutController mainLayout;

    private final StackPane rootView;
    private final VBox mainContentBox;
    private final StackPane candyPackOverlay;
    private final Map<Track, CandyJarView> jarViews = new EnumMap<>(Track.class);
    private final CandyRooSprite mascotSprite;

    public HomeController(TopicService topicService, ProgressService progressService,
                          MascotService mascotService, MainLayoutController mainLayout) {
        this.topicService = topicService;
        this.progressService = progressService;
        this.mascotService = mascotService;
        this.mainLayout = mainLayout;

        rootView = new StackPane();
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        mainContentBox = new VBox(20);
        mainContentBox.setAlignment(Pos.TOP_CENTER);
        mainContentBox.setPadding(new Insets(24, 32, 24, 32));

        mascotSprite = new CandyRooSprite();

        candyPackOverlay = createCandyPackBurstOverlay();

        buildContent();
        rootView.getChildren().addAll(mainContentBox, candyPackOverlay);
    }

    public Node getView() {
        return rootView;
    }

    private void buildContent() {
        // Hero Header Banner
        VBox heroBanner = new VBox(6);
        heroBanner.setAlignment(Pos.CENTER);

        Label logoMark = new Label("🍬 " + AppConfig.APP_NAME);
        logoMark.setStyle("""
            -fx-font-family: 'Segoe UI', 'Outfit', sans-serif;
            -fx-font-size: 32px;
            -fx-font-weight: 900;
            -fx-text-fill: #E63946;
            -fx-effect: dropshadow(gaussian, rgba(230, 57, 70, 0.6), 12, 0, 0, 3);
        """);

        Label tagline = new Label(AppConfig.APP_TAGLINE);
        tagline.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFB703; -fx-font-weight: bold;");

        heroBanner.getChildren().addAll(logoMark, tagline);

        // Mascot Welcome Row
        String username = AppSessionManager.getInstance().getCurrentUser().getUsername();
        mascotSprite.say(mascotService.getWelcomeGreeting(username));

        // 4 Flavor Jars Grid Row
        HBox jarsRow = new HBox(20);
        jarsRow.setAlignment(Pos.CENTER);

        for (Track track : Track.values()) {
            CandyJarView jar = new CandyJarView(track);
            jar.setCursor(javafx.scene.Cursor.HAND);
            jar.setOnMouseClicked(e -> mainLayout.showTrackMapView(track));

            List<Topic> trackTopics = topicService.getTopicsForTrack(track);
            UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
            int completed = (int) trackTopics.stream().filter(t -> progress.isTopicCompleted(t.getId())).count();
            jar.updateProgress(completed, trackTopics.size());

            jarViews.put(track, jar);
            jarsRow.getChildren().add(jar);
        }

        // Action Bar (Quick Continue + Track Selection Prompt)
        HBox actionRow = new HBox(16);
        actionRow.setAlignment(Pos.CENTER);

        Button btnQuickContinue = new Button("🚀 Quick Continue Next Topic");
        btnQuickContinue.setStyle("""
            -fx-background-color: #E63946;
            -fx-text-fill: white;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-background-radius: 24;
            -fx-padding: 12 28;
            -fx-effect: dropshadow(gaussian, rgba(230, 57, 70, 0.4), 8, 0, 0, 3);
        """);
        btnQuickContinue.setOnAction(e -> {
            Topic next = findFirstIncompleteTopic();
            if (next != null) {
                mainLayout.showTopicDetailView(next);
            } else {
                mainLayout.showTrackMapView(Track.FOUNDATIONS);
            }
        });

        Button btnPlayground = new Button("💻 DSA Code Playground");
        btnPlayground.setStyle("""
            -fx-background-color: #2EC4B6;
            -fx-text-fill: #1A1A2E;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-background-radius: 24;
            -fx-padding: 12 24;
            -fx-effect: dropshadow(gaussian, rgba(46, 196, 182, 0.4), 8, 0, 0, 3);
        """);
        btnPlayground.setOnAction(e -> mainLayout.showPlaygroundView());

        actionRow.getChildren().addAll(btnQuickContinue, btnPlayground);

        mainContentBox.getChildren().addAll(heroBanner, mascotSprite, jarsRow, actionRow);
    }

    private StackPane createCandyPackBurstOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(10, 10, 20, 0.92);");

        VBox packBox = new VBox(12);
        packBox.setAlignment(Pos.CENTER);

        // Candy Pack Wrapper Visual
        StackPane packWrapper = new StackPane();
        packWrapper.setPrefSize(320, 380);
        packWrapper.setMaxSize(320, 380);

        Rectangle packBg = new Rectangle(320, 380);
        packBg.setArcWidth(32);
        packBg.setArcHeight(32);
        packBg.setFill(new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#E63946")),
            new Stop(0.3, Color.web("#C9184A")),
            new Stop(0.7, Color.web("#FB8500")),
            new Stop(1, Color.web("#7209B7"))
        ));
        packBg.setStroke(Color.web("#FFB703"));
        packBg.setStrokeWidth(3);
        packBg.setEffect(new DropShadow(25, Color.web("#E63946", 0.8)));

        VBox packDetails = new VBox(8);
        packDetails.setAlignment(Pos.CENTER);
        packDetails.setPadding(new Insets(20));

        Label brand = new Label("🍬 CANDYMAN");
        brand.setStyle("-fx-font-size: 16px; -fx-font-weight: 900; -fx-text-fill: #FFD166;");

        Label logo = new Label("CANDY\nQUEST");
        logo.setStyle("-fx-font-size: 34px; -fx-font-weight: 900; -fx-text-fill: white; -fx-text-alignment: center;");

        Label chew = new Label("FRUITEE FUN SOFT CHEWS\n4 EXCITING DATA STRUCTURES");
        chew.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #FFF0F3; -fx-text-alignment: center;");

        Label toyRibbon = new Label("🎁 FREE TOY INSIDE!");
        toyRibbon.setStyle("""
            -fx-background-color: #FFB703;
            -fx-text-fill: #1A1A2E;
            -fx-font-weight: 900;
            -fx-font-size: 13px;
            -fx-padding: 6 16;
            -fx-background-radius: 12;
        """);

        packDetails.getChildren().addAll(brand, logo, chew, toyRibbon);
        packWrapper.getChildren().addAll(packBg, packDetails);

        Button btnBurst = new Button("💥 CLICK TO BURST OPEN PACK!");
        btnBurst.setStyle("""
            -fx-background-color: #FFB703;
            -fx-text-fill: #1A1A2E;
            -fx-font-size: 16px;
            -fx-font-weight: 900;
            -fx-background-radius: 24;
            -fx-padding: 12 32;
            -fx-cursor: hand;
        """);

        btnBurst.setOnAction(e -> playPackBurstAnimation(overlay, packWrapper));

        packBox.getChildren().addAll(packWrapper, btnBurst);
        overlay.getChildren().add(packBox);

        return overlay;
    }

    private void playPackBurstAnimation(StackPane overlay, StackPane packWrapper) {
        // Tear & Scale Transition
        ScaleTransition packPop = new ScaleTransition(AnimationConfig.PACK_TEAR_DURATION, packWrapper);
        packPop.setFromX(1.0);
        packPop.setFromY(1.0);
        packPop.setToX(1.3);
        packPop.setToY(1.3);

        FadeTransition packFade = new FadeTransition(AnimationConfig.PACK_TEAR_DURATION, packWrapper);
        packFade.setFromValue(1.0);
        packFade.setToValue(0.0);

        FadeTransition overlayFade = new FadeTransition(AnimationConfig.BURST_PARTICLE_DURATION, overlay);
        overlayFade.setFromValue(1.0);
        overlayFade.setToValue(0.0);

        ParallelTransition pt = new ParallelTransition(packPop, packFade, overlayFade);
        pt.setOnFinished(e -> {
            rootView.getChildren().remove(overlay);
            // Spawn candy explosion in main layout particle pane
            if (mainLayout.getGlobalParticlePane() != null) {
                mainLayout.getGlobalParticlePane().spawnCandyBurst(500, 350, AnimationConfig.BURST_PARTICLE_COUNT);
            }
            mascotSprite.setMood(MascotService.MascotMood.CHEERING_CLAP, "Yum! The candy pack is open! Let's start with Strawberry Track!");
        });
        pt.play();
    }

    private Topic findFirstIncompleteTopic() {
        UserProgress progress = AppSessionManager.getInstance().getCurrentProgress();
        for (Track track : Track.values()) {
            for (Topic topic : topicService.getTopicsForTrack(track)) {
                if (!progress.isTopicCompleted(topic.getId()) && progressService.isTopicUnlocked(topic)) {
                    return topic;
                }
            }
        }
        return topicService.getAllTopics().get(0);
    }
}
