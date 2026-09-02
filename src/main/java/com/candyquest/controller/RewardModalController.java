package com.candyquest.controller;

import com.candyquest.config.AnimationConfig;
import com.candyquest.model.ToyReward;
import com.candyquest.view.component.CandyRooSprite;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Celebration modal for the "Free Toy Inside" reveal animation.
 */
public class RewardModalController {
    private final ToyReward toy;
    private final MainLayoutController mainLayout;
    private final StackPane rootView;

    public RewardModalController(ToyReward toy, MainLayoutController mainLayout) {
        this.toy = toy;
        this.mainLayout = mainLayout;

        rootView = new StackPane();
        rootView.setStyle("-fx-background-color: rgba(10, 10, 20, 0.95);");

        buildView();
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        VBox card = new VBox(16);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(32));
        card.setMaxSize(480, 520);
        card.setStyle("""
            -fx-background-color: #16213E;
            -fx-background-radius: 24;
            -fx-border-color: #FFB703;
            -fx-border-width: 3;
            -fx-border-radius: 24;
            -fx-effect: dropshadow(gaussian, rgba(255, 183, 3, 0.5), 24, 0, 0, 4);
        """);

        Label packTornBanner = new Label("🎁 FREE TOY INSIDE UNLOCKED!");
        packTornBanner.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: #FFB703;");

        // Animated Toy Icon with spinning/pulse effect
        StackPane toyIconContainer = new StackPane();
        toyIconContainer.setPrefSize(120, 120);

        Label toyIcon = new Label(toy != null ? toy.getIconEmoji() : "🎁");
        toyIcon.setStyle("-fx-font-size: 64px;");

        RotateTransition spin = new RotateTransition(AnimationConfig.TOY_REVEAL_SPIN_DURATION, toyIcon);
        spin.setByAngle(720);
        spin.play();

        ScaleTransition pulse = new ScaleTransition(Duration.millis(600), toyIcon);
        pulse.setFromX(0.2);
        pulse.setFromY(0.2);
        pulse.setToX(1.1);
        pulse.setToY(1.1);
        pulse.play();

        toyIconContainer.getChildren().add(toyIcon);

        Label toyTitle = new Label(toy != null ? toy.getTitle() : "Surprise Toy");
        toyTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: 900; -fx-text-fill: white;");

        Label toyType = new Label(toy != null ? "[" + toy.getToyType() + "]" : "");
        toyType.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2EC4B6;");

        Label toyDesc = new Label(toy != null ? toy.getDescription() : "");
        toyDesc.setWrapText(true);
        toyDesc.setMaxWidth(380);
        toyDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #E2E8F0; -fx-text-alignment: center;");

        CandyRooSprite mascot = new CandyRooSprite();
        mascot.say("Woohoo! You reached a 10-topic milestone and unlocked a real Free Toy Inside!");

        Button btnClaim = new Button("🍬 Collect & Continue Quest");
        btnClaim.setStyle("""
            -fx-background-color: #FFB703;
            -fx-text-fill: #1A1A2E;
            -fx-font-size: 15px;
            -fx-font-weight: 900;
            -fx-background-radius: 16;
            -fx-padding: 12 32;
            -fx-cursor: hand;
        """);
        btnClaim.setOnAction(e -> mainLayout.showDashboardView());

        card.getChildren().addAll(packTornBanner, toyIconContainer, toyTitle, toyType, toyDesc, mascot, btnClaim);
        rootView.getChildren().add(card);
    }
}
