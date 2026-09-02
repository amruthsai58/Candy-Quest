package com.candyquest.view.component.visualizer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactive Binary Tree & Graph Visualizer showing root, left/right subtrees, and animated BFS/DFS traversals.
 */
public class TreeVisualizer extends VBox implements AlgorithmVisualizer {
    private final Pane treeCanvas;
    private final Label statusLabel;
    private final List<Circle> nodeCircles = new ArrayList<>();
    private final int[] nodeValues = {50, 30, 70, 20, 40, 60, 80};
    private int traversalStep = 0;
    private final int[] bfsOrder = {0, 1, 2, 3, 4, 5, 6}; // Level order indices

    public TreeVisualizer() {
        setAlignment(Pos.CENTER);
        setSpacing(14);
        setStyle("-fx-background-color: rgba(26, 26, 46, 0.85); -fx-padding: 16; -fx-background-radius: 12; -fx-border-color: #7209B7; -fx-border-width: 1.5; -fx-border-radius: 12;");

        Label title = new Label("🍇 Grape Track: Binary Tree Visualizer");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #9D4EDD;");

        treeCanvas = new Pane();
        treeCanvas.setPrefSize(380, 150);
        treeCanvas.setMaxSize(380, 150);

        statusLabel = new Label("Binary Search Tree rooted at [50].");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #F1FAEE; -fx-font-weight: bold;");

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button btnBfs = new Button("🌊 BFS Step");
        btnBfs.setStyle("-fx-background-color: #7209B7; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnBfs.setOnAction(e -> stepForward());

        Button btnReset = new Button("🔄 Reset Tree");
        btnReset.setStyle("-fx-background-color: #3A3E59; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnReset.setOnAction(e -> reset());

        controls.getChildren().addAll(btnBfs, btnReset);

        getChildren().addAll(title, treeCanvas, statusLabel, controls);

        reset();
    }

    @Override
    public Node getViewNode() {
        return this;
    }

    @Override
    public void reset() {
        traversalStep = 0;
        renderTree();
        statusLabel.setText("BST initialized. Ready for BFS Level-Order traversal.");
    }

    private void renderTree() {
        treeCanvas.getChildren().clear();
        nodeCircles.clear();

        // Node Coordinates:
        // Root: 0 (190, 25)
        // Level 1: 1 (110, 75), 2 (270, 75)
        // Level 2: 3 (70, 125), 4 (150, 125), 5 (230, 125), 6 (310, 125)
        double[][] pos = {
            {190, 25},
            {110, 75}, {270, 75},
            {70, 125}, {150, 125}, {230, 125}, {310, 125}
        };

        // Draw connecting edges
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}};
        for (int[] edge : edges) {
            Line line = new Line(pos[edge[0]][0], pos[edge[0]][1], pos[edge[1]][0], pos[edge[1]][1]);
            line.setStroke(Color.web("#5C6180"));
            line.setStrokeWidth(2);
            treeCanvas.getChildren().add(line);
        }

        // Draw Nodes
        for (int i = 0; i < pos.length; i++) {
            Circle c = new Circle(pos[i][0], pos[i][1], 16);
            c.setFill(Color.web("#7209B7"));
            c.setStroke(Color.WHITE);
            c.setStrokeWidth(1.5);
            nodeCircles.add(c);

            Label valLbl = new Label(String.valueOf(nodeValues[i]));
            valLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
            valLbl.setLayoutX(pos[i][0] - 8);
            valLbl.setLayoutY(pos[i][1] - 8);

            treeCanvas.getChildren().addAll(c, valLbl);
        }
    }

    @Override
    public void stepForward() {
        if (traversalStep < bfsOrder.length) {
            int nodeIdx = bfsOrder[traversalStep];
            nodeCircles.get(nodeIdx).setFill(Color.web("#2EC4B6")); // Visited color
            statusLabel.setText("BFS visiting Node [" + nodeValues[nodeIdx] + "] (Step " + (traversalStep + 1) + " of " + bfsOrder.length + ")");
            traversalStep++;
        } else {
            statusLabel.setText("🎉 BFS Level-Order Traversal Complete!");
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
