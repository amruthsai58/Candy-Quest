package com.candyquest.view.component;

import com.candyquest.config.AnimationConfig;
import com.candyquest.model.Track;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Custom visual component depicting a candy jar filled with colorful candy pieces per track.
 */
public class CandyJarView extends VBox {
    private final Track track;
    private final Pane jarBodyPane;
    private final Rectangle fillLevelRect;
    private final Pane candiesContainer;
    private final Label titleLabel;
    private final Label progressTextLabel;
    private double currentPercentage = 0.0;
    private static final double JAR_WIDTH = 130;
    private static final double JAR_HEIGHT = 160;

    public CandyJarView(Track track) {
        this.track = track;
        setAlignment(Pos.CENTER);
        setSpacing(8);
        getStyleClass().add("candy-jar-view");

        // Track Title & Flavor Badge
        titleLabel = new Label(track.getEmoji() + " " + track.getFlavorName());
        titleLabel.setStyle("-fx-font-family: 'Segoe UI', 'Outfit', sans-serif; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");

        // Jar Outer Container
        StackPane jarStack = new StackPane();
        jarStack.setPrefSize(JAR_WIDTH, JAR_HEIGHT + 20);
        jarStack.setMaxSize(JAR_WIDTH, JAR_HEIGHT + 20);

        // Cork Lid
        Rectangle cork = new Rectangle(JAR_WIDTH * 0.6, 14);
        cork.setArcWidth(4);
        cork.setArcHeight(4);
        cork.setFill(Color.web("#D4A373"));
        cork.setStroke(Color.web("#8D5B4C"));
        cork.setStrokeWidth(1.5);
        StackPane.setAlignment(cork, Pos.TOP_CENTER);

        // Glass Jar Body
        jarBodyPane = new Pane();
        jarBodyPane.setPrefSize(JAR_WIDTH, JAR_HEIGHT);
        jarBodyPane.setMaxSize(JAR_WIDTH, JAR_HEIGHT);
        jarBodyPane.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.08);
            -fx-background-radius: 16 16 28 28;
            -fx-border-color: rgba(255, 255, 255, 0.4);
            -fx-border-width: 2.5;
            -fx-border-radius: 16 16 28 28;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 4);
        """);

        // Fill Level Rectangle (starts at 0 height from bottom)
        fillLevelRect = new Rectangle(JAR_WIDTH - 6, 0);
        fillLevelRect.setArcWidth(22);
        fillLevelRect.setArcHeight(22);
        fillLevelRect.setFill(createFlavorFillGradient(track));
        fillLevelRect.setLayoutX(3);
        fillLevelRect.setLayoutY(JAR_HEIGHT - 3);

        candiesContainer = new Pane();
        candiesContainer.setPrefSize(JAR_WIDTH, JAR_HEIGHT);
        candiesContainer.setMouseTransparent(true);

        // Gloss Reflection Line on the glass
        Rectangle glassGloss = new Rectangle(6, JAR_HEIGHT - 24);
        glassGloss.setArcWidth(3);
        glassGloss.setArcHeight(3);
        glassGloss.setFill(Color.web("#FFFFFF", 0.25));
        glassGloss.setLayoutX(10);
        glassGloss.setLayoutY(12);

        jarBodyPane.getChildren().addAll(fillLevelRect, candiesContainer, glassGloss);
        StackPane.setAlignment(jarBodyPane, Pos.BOTTOM_CENTER);

        jarStack.getChildren().addAll(jarBodyPane, cork);

        // Progress Text
        progressTextLabel = new Label("0% Filled");
        progressTextLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E2E8F0; -fx-font-weight: bold;");

        getChildren().addAll(titleLabel, jarStack, progressTextLabel);

        // Bounce micro-animation on hover
        setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }

    private LinearGradient createFlavorFillGradient(Track track) {
        Color primary = Color.web(track.getPrimaryColor(), 0.75);
        Color accent = Color.web(track.getDarkAccentColor(), 0.9);
        return new LinearGradient(
            0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
            new Stop(0, primary),
            new Stop(1, accent)
        );
    }

    public void updateProgress(int completedTopics, int totalTopics) {
        double percentage = totalTopics > 0 ? (double) completedTopics / totalTopics : 0.0;
        percentage = Math.min(1.0, Math.max(0.0, percentage));
        this.currentPercentage = percentage;

        progressTextLabel.setText(String.format("%d/%d (%d%%)", completedTopics, totalTopics, (int) (percentage * 100)));

        double targetHeight = (JAR_HEIGHT - 10) * percentage;
        double targetY = (JAR_HEIGHT - 3) - targetHeight;

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(fillLevelRect.heightProperty(), fillLevelRect.getHeight()),
                new KeyValue(fillLevelRect.layoutYProperty(), fillLevelRect.getLayoutY())
            ),
            new KeyFrame(AnimationConfig.JAR_FILL_DURATION,
                new KeyValue(fillLevelRect.heightProperty(), targetHeight, AnimationConfig.ELASTIC_OUT),
                new KeyValue(fillLevelRect.layoutYProperty(), targetY, AnimationConfig.ELASTIC_OUT)
            )
        );
        timeline.play();

        renderCandiesInside(completedTopics);
    }

    private void renderCandiesInside(int count) {
        candiesContainer.getChildren().clear();
        int candiesToDraw = Math.min(count, 18);
        Random r = new Random(track.ordinal() * 100L);

        for (int i = 0; i < candiesToDraw; i++) {
            Circle candy = new Circle(5 + r.nextDouble() * 3);
            candy.setFill(Color.web(track.getPrimaryColor()).brighter());
            candy.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.7));
            candy.setStrokeWidth(1);

            double cx = 16 + r.nextDouble() * (JAR_WIDTH - 32);
            double cy = (JAR_HEIGHT - 14) - (i * 7.0) + (r.nextDouble() * 4 - 2);
            candy.setCenterX(cx);
            candy.setCenterY(Math.max(20, cy));

            candiesContainer.getChildren().add(candy);

            // Subtle floating idle motion
            TranslateTransition floatAnim = new TranslateTransition(Duration.millis(1200 + r.nextInt(600)), candy);
            floatAnim.setByY(-4);
            floatAnim.setAutoReverse(true);
            floatAnim.setCycleCount(Animation.INDEFINITE);
            floatAnim.play();
        }
    }

    public Track getTrack() {
        return track;
    }
}
