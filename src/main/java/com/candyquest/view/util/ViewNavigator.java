package com.candyquest.view.util;

import com.candyquest.config.AnimationConfig;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * View navigation manager with animated crossfades and candy pop transitions.
 */
public class ViewNavigator {
    private static StackPane contentContainer;

    public static void setContentContainer(StackPane container) {
        contentContainer = container;
    }

    public static void navigateTo(Node newView) {
        if (contentContainer == null || newView == null) return;

        if (contentContainer.getChildren().isEmpty()) {
            contentContainer.getChildren().add(newView);
            return;
        }

        Node currentView = contentContainer.getChildren().get(0);

        // Outgoing transition
        FadeTransition fadeOut = new FadeTransition(AnimationConfig.SCREEN_TRANSITION_DURATION, currentView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        ScaleTransition scaleOut = new ScaleTransition(AnimationConfig.SCREEN_TRANSITION_DURATION, currentView);
        scaleOut.setFromX(1.0);
        scaleOut.setFromY(1.0);
        scaleOut.setToX(0.96);
        scaleOut.setToY(0.96);

        ParallelTransition outTransition = new ParallelTransition(fadeOut, scaleOut);
        outTransition.setOnFinished(e -> {
            contentContainer.getChildren().setAll(newView);

            // Incoming transition
            newView.setOpacity(0.0);
            newView.setScaleX(1.04);
            newView.setScaleY(1.04);

            FadeTransition fadeIn = new FadeTransition(AnimationConfig.SCREEN_TRANSITION_DURATION, newView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setInterpolator(AnimationConfig.SCREEN_EASE);

            ScaleTransition scaleIn = new ScaleTransition(AnimationConfig.SCREEN_TRANSITION_DURATION, newView);
            scaleIn.setToX(1.0);
            scaleIn.setToY(1.0);
            scaleIn.setInterpolator(AnimationConfig.SCREEN_EASE);

            ParallelTransition inTransition = new ParallelTransition(fadeIn, scaleIn);
            inTransition.play();
        });

        outTransition.play();
    }
}
