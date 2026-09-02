package com.candyquest.view.component;

import com.candyquest.config.AnimationConfig;
import com.candyquest.service.MascotService.MascotMood;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Animated Vector Mascot Sprite for "Candy Roo" — the Australian Kangaroo DSA guide.
 */
public class CandyRooSprite extends HBox {
    private final StackPane mascotBodyContainer;
    private final Group rooVectorGroup;
    private final StackPane speechBubble;
    private final Label speechLabel;
    private MascotMood currentMood = MascotMood.IDLE_BOUNCE;
    private Timeline idleAnimation;

    // Body parts for mood posing
    private Circle leftEye;
    private Circle rightEye;
    private Arc mouthArc;
    private Polygon leftEar;
    private Polygon rightEar;
    private Circle candyInPouch;

    public CandyRooSprite() {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(12);

        rooVectorGroup = new Group();
        buildRooVector();

        mascotBodyContainer = new StackPane(rooVectorGroup);
        mascotBodyContainer.setPrefSize(90, 100);

        // Speech Bubble
        speechBubble = new StackPane();
        speechBubble.setMaxWidth(280);
        speechBubble.setStyle("""
            -fx-background-color: #FFFFFF;
            -fx-background-radius: 18;
            -fx-border-color: #E63946;
            -fx-border-width: 2;
            -fx-border-radius: 18;
            -fx-padding: 10 14 10 14;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 3);
        """);

        speechLabel = new Label("G'day! I'm Candy Roo! Let's solve some DSA!");
        speechLabel.setWrapText(true);
        speechLabel.setTextAlignment(TextAlignment.LEFT);
        speechLabel.setStyle("-fx-font-family: 'Segoe UI', 'Outfit', sans-serif; -fx-font-size: 13px; -fx-text-fill: #1A1A2E; -fx-font-weight: bold;");

        speechBubble.getChildren().add(speechLabel);

        getChildren().addAll(mascotBodyContainer, speechBubble);

        startIdleAnimation();
    }

    private void buildRooVector() {
        Color furColor = Color.web("#D48B47");       // Golden Kangaroo Brown
        Color bellyColor = Color.web("#FDE2B8");     // Cream Pouch/Belly
        Color innerEarColor = Color.web("#FFCCD5");  // Soft Pink

        // Tail
        QuadCurve tail = new QuadCurve(20, 80, 5, 95, 2, 70);
        tail.setStroke(furColor);
        tail.setStrokeWidth(10);
        tail.setFill(Color.TRANSPARENT);
        tail.setStrokeLineCap(StrokeLineCap.ROUND);

        // Left Ear
        leftEar = new Polygon(30, 25, 22, 2, 38, 15);
        leftEar.setFill(furColor);
        Polygon leftInnerEar = new Polygon(29, 23, 24, 6, 36, 16);
        leftInnerEar.setFill(innerEarColor);

        // Right Ear
        rightEar = new Polygon(55, 25, 63, 2, 47, 15);
        rightEar.setFill(furColor);
        Polygon rightInnerEar = new Polygon(56, 23, 61, 6, 49, 16);
        rightInnerEar.setFill(innerEarColor);

        // Body
        Ellipse body = new Ellipse(43, 65, 24, 28);
        body.setFill(furColor);

        // Belly / Pouch
        Ellipse pouch = new Ellipse(43, 70, 15, 18);
        pouch.setFill(bellyColor);

        // Candy piece tucked inside the pouch!
        candyInPouch = new Circle(43, 63, 6, Color.web("#E63946"));
        candyInPouch.setStroke(Color.WHITE);
        candyInPouch.setStrokeWidth(1.5);

        // Head
        Ellipse head = new Ellipse(43, 35, 18, 16);
        head.setFill(furColor);

        // Muzzle
        Ellipse muzzle = new Ellipse(43, 41, 10, 8);
        muzzle.setFill(bellyColor);

        // Nose
        Circle nose = new Circle(43, 38, 3, Color.web("#3A2010"));

        // Eyes
        leftEye = new Circle(37, 32, 2.8, Color.web("#1A1A2E"));
        rightEye = new Circle(49, 32, 2.8, Color.web("#1A1A2E"));

        // Mouth (friendly smile)
        mouthArc = new Arc(43, 42, 6, 4, 180, 180);
        mouthArc.setType(ArcType.OPEN);
        mouthArc.setStroke(Color.web("#3A2010"));
        mouthArc.setStrokeWidth(1.5);
        mouthArc.setFill(Color.TRANSPARENT);

        // Feet
        Ellipse leftFoot = new Ellipse(30, 92, 10, 5);
        leftFoot.setFill(furColor);
        Ellipse rightFoot = new Ellipse(56, 92, 10, 5);
        rightFoot.setFill(furColor);

        rooVectorGroup.getChildren().addAll(
            tail, leftFoot, rightFoot, leftEar, leftInnerEar, rightEar, rightInnerEar,
            body, pouch, candyInPouch, head, muzzle, nose, leftEye, rightEye, mouthArc
        );
    }

    private void startIdleAnimation() {
        idleAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(mascotBodyContainer.translateYProperty(), 0),
                new KeyValue(leftEar.rotateProperty(), 0),
                new KeyValue(rightEar.rotateProperty(), 0)
            ),
            new KeyFrame(Duration.millis(600), 
                new KeyValue(mascotBodyContainer.translateYProperty(), -6, Interpolator.EASE_BOTH),
                new KeyValue(leftEar.rotateProperty(), -4, Interpolator.EASE_BOTH),
                new KeyValue(rightEar.rotateProperty(), 4, Interpolator.EASE_BOTH)
            ),
            new KeyFrame(Duration.millis(1200), 
                new KeyValue(mascotBodyContainer.translateYProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(leftEar.rotateProperty(), 0, Interpolator.EASE_BOTH),
                new KeyValue(rightEar.rotateProperty(), 0, Interpolator.EASE_BOTH)
            )
        );
        idleAnimation.setCycleCount(Animation.INDEFINITE);
        idleAnimation.play();
    }

    public void setMood(MascotMood mood, String speechText) {
        this.currentMood = mood;
        if (speechText != null) {
            say(speechText);
        }

        switch (mood) {
            case CHEERING_CLAP -> cheer();
            case CONFUSED_SHRUG -> shrug();
            case POINTING_HINT -> point();
            case IDLE_BOUNCE, WAVING_HELLO -> {
                mouthArc.setLength(180);
                candyInPouch.setFill(Color.web("#E63946"));
            }
        }
    }

    public void say(String text) {
        speechLabel.setText(text);
        
        // Pop in animation for speech bubble
        ScaleTransition pop = new ScaleTransition(Duration.millis(200), speechBubble);
        pop.setFromX(0.85);
        pop.setFromY(0.85);
        pop.setToX(1.0);
        pop.setToY(1.0);
        pop.play();
    }

    private void cheer() {
        mouthArc.setLength(240); // Big open happy smile
        candyInPouch.setFill(Color.web("#FFB703")); // Glowing golden candy

        // Jump high in excitement!
        TranslateTransition jump = new TranslateTransition(Duration.millis(220), mascotBodyContainer);
        jump.setByY(-22);
        jump.setAutoReverse(true);
        jump.setCycleCount(4);
        jump.setInterpolator(Interpolator.SPLINE(0.2, 0.8, 0.4, 1.0));
        jump.play();
    }

    private void shrug() {
        mouthArc.setLength(60); // Puzzled mouth
        candyInPouch.setFill(Color.web("#4CC9F0"));

        // Gentle head tilt
        RotateTransition tilt = new RotateTransition(Duration.millis(200), rooVectorGroup);
        tilt.setByAngle(12);
        tilt.setAutoReverse(true);
        tilt.setCycleCount(2);
        tilt.play();
    }

    private void point() {
        candyInPouch.setFill(Color.web("#2EC4B6"));
        ScaleTransition bounce = new ScaleTransition(Duration.millis(200), mascotBodyContainer);
        bounce.setToX(1.1);
        bounce.setToY(1.1);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(2);
        bounce.play();
    }
}
