package com.candyquest.view.component.visualizer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactive Recursion & DP Call Stack Visualizer.
 */
public class RecursionVisualizer extends VBox implements AlgorithmVisualizer {
    private final Pane canvas;
    private final Label statusLabel;
    private final List<Circle> callNodes = new ArrayList<>();
    private int step = 0;
    private final String[] callLabels = {"fib(4)", "fib(3)", "fib(2)", "fib(2)", "fib(1)", "fib(1)", "fib(0)"};

    public RecursionVisualizer() {
        setAlignment(Pos.CENTER);
        setSpacing(14);
        setStyle("-fx-background-color: rgba(26, 26, 46, 0.85); -fx-padding: 16; -fx-background-radius: 12; -fx-border-color: #2EC4B6; -fx-border-width: 1.5; -fx-border-radius: 12;");

        Label title = new Label("🍉 Watermelon Track: Recursion Tree Visualizer");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2EC4B6;");

        canvas = new Pane();
        canvas.setPrefSize(380, 150);
        canvas.setMaxSize(380, 150);

        statusLabel = new Label("Call Tree: fib(4) splitting into subproblems.");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #F1FAEE; -fx-font-weight: bold;");

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button btnStep = new Button("⚡ Recursive Call");
        btnStep.setStyle("-fx-background-color: #2EC4B6; -fx-text-fill: #1A1A2E; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnStep.setOnAction(e -> stepForward());

        Button btnReset = new Button("🔄 Reset");
        btnReset.setStyle("-fx-background-color: #3A3E59; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnReset.setOnAction(e -> reset());

        controls.getChildren().addAll(btnStep, btnReset);

        getChildren().addAll(title, canvas, statusLabel, controls);

        reset();
    }

    @Override
    public Node getViewNode() {
        return this;
    }

    @Override
    public void reset() {
        step = 0;
        renderTree();
        statusLabel.setText("Root call: fib(4) waiting to split into fib(3) + fib(2).");
    }

    private void renderTree() {
        canvas.getChildren().clear();
        callNodes.clear();

        double[][] pos = {
            {190, 25},
            {110, 75}, {270, 75},
            {70, 125}, {150, 125}, {230, 125}, {310, 125}
        };

        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}};
        for (int[] edge : edges) {
            Line line = new Line(pos[edge[0]][0], pos[edge[0]][1], pos[edge[1]][0], pos[edge[1]][1]);
            line.setStroke(Color.web("#5C6180"));
            line.setStrokeWidth(2);
            canvas.getChildren().add(line);
        }

        for (int i = 0; i < pos.length; i++) {
            Circle c = new Circle(pos[i][0], pos[i][1], 18);
            c.setFill(Color.web("#2EC4B6", 0.3));
            c.setStroke(Color.web("#2EC4B6"));
            c.setStrokeWidth(1.5);
            callNodes.add(c);

            Label valLbl = new Label(callLabels[i]);
            valLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 9px;");
            valLbl.setLayoutX(pos[i][0] - 15);
            valLbl.setLayoutY(pos[i][1] - 6);

            canvas.getChildren().addAll(c, valLbl);
        }
    }

    @Override
    public void stepForward() {
        if (step < callNodes.size()) {
            callNodes.get(step).setFill(Color.web("#FF5D8F")); // Active stack frame
            statusLabel.setText("Active Frame: " + callLabels[step] + " pushed to recursion stack.");
            step++;
        } else {
            statusLabel.setText("✨ Base cases reached! Values unwinding and memoized!");
            for (Circle c : callNodes) {
                c.setFill(Color.web("#2EC4B6"));
            }
        }
    }

    @Override
    public void play() {
        stepForward();
    }

    @Override
    public void pause() {}

    @Override
    public String getCurrentStepDescription() {
        return statusLabel.getText();
    }
}
