package com.candyquest.view.component.visualizer;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactive Array & Sorting Visualizer with candy-bar columns.
 */
public class ArrayVisualizer extends VBox implements AlgorithmVisualizer {
    private final int[] initialValues = {35, 12, 78, 24, 60, 45, 90, 18};
    private int[] currentValues;
    private final HBox arrayContainer;
    private final List<StackPane> barNodes = new ArrayList<>();
    private final Label statusLabel;
    private int stepIndex = 0;
    private int i = 0, j = 0;
    private Timeline autoPlayTimeline;

    public ArrayVisualizer() {
        setAlignment(Pos.CENTER);
        setSpacing(16);
        setStyle("-fx-background-color: rgba(26, 26, 46, 0.85); -fx-padding: 16; -fx-background-radius: 12; -fx-border-color: #E63946; -fx-border-width: 1.5; -fx-border-radius: 12;");

        Label title = new Label("🍬 Candy Array & Sort Visualizer");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #FFB703;");

        arrayContainer = new HBox(12);
        arrayContainer.setAlignment(Pos.BOTTOM_CENTER);
        arrayContainer.setPrefHeight(160);

        statusLabel = new Label("Click 'Step Forward' or 'Play' to watch sorting in action!");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #F1FAEE; -fx-font-weight: bold;");

        // Control Buttons
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button btnStep = new Button("▶ Next Step");
        btnStep.setStyle("-fx-background-color: #E63946; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 14;");
        btnStep.setOnAction(e -> stepForward());

        Button btnPlay = new Button("⚡ Auto Play");
        btnPlay.setStyle("-fx-background-color: #FB8500; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 14;");
        btnPlay.setOnAction(e -> play());

        Button btnReset = new Button("🔄 Reset");
        btnReset.setStyle("-fx-background-color: #3A3E59; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 14;");
        btnReset.setOnAction(e -> reset());

        controls.getChildren().addAll(btnStep, btnPlay, btnReset);

        getChildren().addAll(title, arrayContainer, statusLabel, controls);

        reset();
    }

    @Override
    public Node getViewNode() {
        return this;
    }

    @Override
    public void reset() {
        pause();
        currentValues = initialValues.clone();
        stepIndex = 0;
        i = 0;
        j = 0;
        renderBars();
        statusLabel.setText("Array reset! Ready to simulate.");
    }

    private void renderBars() {
        arrayContainer.getChildren().clear();
        barNodes.clear();

        for (int idx = 0; idx < currentValues.length; idx++) {
            int val = currentValues[idx];
            StackPane barStack = new StackPane();
            barStack.setAlignment(Pos.BOTTOM_CENTER);

            double barHeight = val * 1.3 + 20;
            Rectangle rect = new Rectangle(36, barHeight);
            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setFill(Color.web("#E63946"));
            rect.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.5));
            rect.setStrokeWidth(1.5);

            Label valLabel = new Label(String.valueOf(val));
            valLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 0 0 6 0;");

            Label idxLabel = new Label("[" + idx + "]");
            idxLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-size: 10px;");

            VBox column = new VBox(4, new StackPane(rect, valLabel), idxLabel);
            column.setAlignment(Pos.BOTTOM_CENTER);
            barStack.getChildren().add(column);

            barNodes.add(barStack);
            arrayContainer.getChildren().add(barStack);
        }
    }

    @Override
    public void stepForward() {
        int n = currentValues.length;
        if (i < n - 1) {
            if (j < n - i - 1) {
                // Highlight comparing indices j and j+1
                highlightComparing(j, j + 1);

                if (currentValues[j] > currentValues[j + 1]) {
                    // Swap values
                    int temp = currentValues[j];
                    currentValues[j] = currentValues[j + 1];
                    currentValues[j + 1] = temp;
                    statusLabel.setText(String.format("Comparing index %d (%d) > %d (%d) → Swapped!", j, temp, j + 1, currentValues[j]));
                    animateSwap(j, j + 1);
                } else {
                    statusLabel.setText(String.format("Comparing index %d (%d) <= %d (%d) → In order.", j, currentValues[j], j + 1, currentValues[j + 1]));
                }
                j++;
            } else {
                j = 0;
                i++;
                stepForward();
            }
        } else {
            statusLabel.setText("🎉 Array is completely sorted!");
            highlightAllSorted();
            pause();
        }
    }

    private void highlightComparing(int idx1, int idx2) {
        for (int k = 0; k < barNodes.size(); k++) {
            Rectangle rect = getBarRect(k);
            if (rect != null) {
                if (k == idx1 || k == idx2) {
                    rect.setFill(Color.web("#FB8500")); // Highlight Orange
                } else {
                    rect.setFill(Color.web("#E63946")); // Normal Strawberry
                }
            }
        }
    }

    private void animateSwap(int idx1, int idx2) {
        renderBars();
        Rectangle r1 = getBarRect(idx1);
        Rectangle r2 = getBarRect(idx2);
        if (r1 != null) r1.setFill(Color.web("#2EC4B6")); // Cyan swap highlight
        if (r2 != null) r2.setFill(Color.web("#2EC4B6"));
    }

    private void highlightAllSorted() {
        for (int k = 0; k < barNodes.size(); k++) {
            Rectangle rect = getBarRect(k);
            if (rect != null) {
                rect.setFill(Color.web("#2EC4B6")); // Sorted Watermelon Green
            }
        }
    }

    private Rectangle getBarRect(int index) {
        if (index >= 0 && index < barNodes.size()) {
            StackPane stack = barNodes.get(index);
            VBox col = (VBox) stack.getChildren().get(0);
            StackPane rectStack = (StackPane) col.getChildren().get(0);
            return (Rectangle) rectStack.getChildren().get(0);
        }
        return null;
    }

    @Override
    public void play() {
        if (autoPlayTimeline != null) autoPlayTimeline.stop();
        autoPlayTimeline = new Timeline(new KeyFrame(Duration.millis(600), e -> stepForward()));
        autoPlayTimeline.setCycleCount(Animation.INDEFINITE);
        autoPlayTimeline.play();
    }

    @Override
    public void pause() {
        if (autoPlayTimeline != null) {
            autoPlayTimeline.stop();
        }
    }

    @Override
    public String getCurrentStepDescription() {
        return statusLabel.getText();
    }
}
