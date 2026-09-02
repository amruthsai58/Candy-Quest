package com.candyquest.view.component;

import com.candyquest.config.AnimationConfig;
import com.candyquest.model.Topic;
import com.candyquest.pattern.state.TopicState;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Interactive Candy Trail Node for the Track Map view.
 */
public class TrackNodeButton extends VBox {
    private final Topic topic;
    private final TopicState state;
    private final StackPane nodeCircleStack;
    private final Circle outerGlowRing;
    private final Circle mainCandyCircle;
    private final Label nodeIconLabel;
    private final Label nodeTitleLabel;
    private ScaleTransition pulseAnimation;

    public TrackNodeButton(Topic topic, TopicState state, Consumer<Topic> onClickAction) {
        this.topic = topic;
        this.state = state;

        setAlignment(Pos.CENTER);
        setSpacing(6);
        setPrefWidth(110);
        setMaxWidth(110);

        nodeCircleStack = new StackPane();
        nodeCircleStack.setPrefSize(56, 56);
        nodeCircleStack.setMaxSize(56, 56);

        outerGlowRing = new Circle(32, Color.TRANSPARENT);
        outerGlowRing.setStrokeWidth(3);

        mainCandyCircle = new Circle(24);
        nodeIconLabel = new Label();
        nodeIconLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        nodeCircleStack.getChildren().addAll(outerGlowRing, mainCandyCircle, nodeIconLabel);

        // Sub-label for topic name
        nodeTitleLabel = new Label(topic.getSequenceNumber() + ". " + topic.getName());
        nodeTitleLabel.setWrapText(true);
        nodeTitleLabel.setMaxWidth(100);
        nodeTitleLabel.setAlignment(Pos.CENTER);
        nodeTitleLabel.setStyle("-fx-font-size: 11px; -fx-text-alignment: center; -fx-text-fill: #E2E8F0; -fx-font-weight: 600;");

        getChildren().addAll(nodeCircleStack, nodeTitleLabel);

        applyStateStyling();

        // Tooltip
        Tooltip tooltip = new Tooltip(String.format(
            "[%s] %s\nDifficulty: %s | %s\nStatus: %s",
            topic.getTag(), topic.getName(), topic.getDifficultyStars(),
            topic.getTimeComplexity(), state.getStatusDescription()
        ));
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(this, tooltip);

        // Click Handling & Micro-Interactions
        if (state.canOpen()) {
            setCursor(javafx.scene.Cursor.HAND);
            setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(120), nodeCircleStack);
                st.setToX(1.15);
                st.setToY(1.15);
                st.play();
            });
            setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(120), nodeCircleStack);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });
            setOnMouseClicked(e -> {
                // Soft chew press squish
                ScaleTransition squish = new ScaleTransition(AnimationConfig.BUTTON_PRESS_DURATION, nodeCircleStack);
                squish.setToX(AnimationConfig.BUTTON_SQUISH_SCALE_X);
                squish.setToY(AnimationConfig.BUTTON_SQUISH_SCALE_Y);
                squish.setAutoReverse(true);
                squish.setCycleCount(2);
                squish.setOnFinished(ev -> {
                    if (onClickAction != null) onClickAction.accept(topic);
                });
                squish.play();
            });
        } else {
            setOpacity(0.5);
            setCursor(javafx.scene.Cursor.DEFAULT);
        }
    }

    private void applyStateStyling() {
        Color trackColor = Color.web(topic.getTrack().getPrimaryColor());

        switch (state.getStateName()) {
            case "LOCKED" -> {
                mainCandyCircle.setFill(Color.web("#3A3E59"));
                mainCandyCircle.setStroke(Color.web("#5C6180"));
                mainCandyCircle.setStrokeWidth(2);
                nodeIconLabel.setText("🔒");
                outerGlowRing.setVisible(false);
            }
            case "IN_PROGRESS" -> {
                mainCandyCircle.setFill(trackColor);
                mainCandyCircle.setStroke(Color.WHITE);
                mainCandyCircle.setStrokeWidth(2.5);
                nodeIconLabel.setText(String.valueOf(topic.getSequenceNumber()));

                outerGlowRing.setVisible(true);
                outerGlowRing.setStroke(trackColor.deriveColor(0, 1, 1, 0.7));

                // Pulsing glowing ring
                pulseAnimation = new ScaleTransition(AnimationConfig.NODE_PULSE_DURATION, nodeCircleStack);
                pulseAnimation.setFromX(1.0);
                pulseAnimation.setFromY(1.0);
                pulseAnimation.setToX(AnimationConfig.NODE_PULSE_SCALE);
                pulseAnimation.setToY(AnimationConfig.NODE_PULSE_SCALE);
                pulseAnimation.setAutoReverse(true);
                pulseAnimation.setCycleCount(Animation.INDEFINITE);
                pulseAnimation.play();

                nodeCircleStack.setEffect(new DropShadow(15, trackColor));
            }
            case "COMPLETED" -> {
                mainCandyCircle.setFill(trackColor);
                mainCandyCircle.setStroke(Color.web("#FFD166"));
                mainCandyCircle.setStrokeWidth(2.5);
                nodeIconLabel.setText("✓");
                outerGlowRing.setVisible(false);
            }
            case "MASTERED" -> {
                mainCandyCircle.setFill(Color.web("#FFD166"));
                mainCandyCircle.setStroke(Color.WHITE);
                mainCandyCircle.setStrokeWidth(3);
                nodeIconLabel.setText("👑");
                nodeIconLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1A1A2E;");
                outerGlowRing.setVisible(true);
                outerGlowRing.setStroke(Color.web("#FFE169", 0.6));
                nodeCircleStack.setEffect(new DropShadow(18, Color.web("#FFD166")));
            }
        }
    }

    public Topic getTopic() {
        return topic;
    }
}
