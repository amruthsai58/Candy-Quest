package com.candyquest.view.component.visualizer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactive Linked List Visualizer showing nodes, data fields, and pointers.
 */
public class LinkedListVisualizer extends VBox implements AlgorithmVisualizer {
    private final List<String> initialNodes = List.of("🍓 10", "🍊 25", "🍇 40", "🍉 65");
    private final List<String> currentNodes = new ArrayList<>();
    private final HBox listContainer;
    private final Label statusLabel;
    private int step = 0;

    public LinkedListVisualizer() {
        setAlignment(Pos.CENTER);
        setSpacing(16);
        setStyle("-fx-background-color: rgba(26, 26, 46, 0.85); -fx-padding: 16; -fx-background-radius: 12; -fx-border-color: #FB8500; -fx-border-width: 1.5; -fx-border-radius: 12;");

        Label title = new Label("🍊 Orange Track: Linked List Visualizer");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #FB8500;");

        listContainer = new HBox(8);
        listContainer.setAlignment(Pos.CENTER);
        listContainer.setPrefHeight(90);

        statusLabel = new Label("Linked List initialized with Head pointing to first node.");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #F1FAEE; -fx-font-weight: bold;");

        // Controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button btnInsert = new Button("➕ Insert Head");
        btnInsert.setStyle("-fx-background-color: #FB8500; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnInsert.setOnAction(e -> insertAtHead());

        Button btnReverse = new Button("🔄 Reverse List");
        btnReverse.setStyle("-fx-background-color: #7209B7; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnReverse.setOnAction(e -> reverseList());

        Button btnReset = new Button("🔄 Reset");
        btnReset.setStyle("-fx-background-color: #3A3E59; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 6 12;");
        btnReset.setOnAction(e -> reset());

        controls.getChildren().addAll(btnInsert, btnReverse, btnReset);

        getChildren().addAll(title, listContainer, statusLabel, controls);

        reset();
    }

    @Override
    public Node getViewNode() {
        return this;
    }

    @Override
    public void reset() {
        currentNodes.clear();
        currentNodes.addAll(initialNodes);
        step = 0;
        renderList();
        statusLabel.setText("Linked List reset: Head -> " + String.join(" -> ", currentNodes) + " -> NULL");
    }

    private void renderList() {
        listContainer.getChildren().clear();

        // HEAD pointer marker
        Label headLabel = new Label("HEAD ➜");
        headLabel.setStyle("-fx-text-fill: #FFB703; -fx-font-weight: bold; -fx-font-size: 12px;");
        listContainer.getChildren().add(headLabel);

        for (int i = 0; i < currentNodes.size(); i++) {
            String value = currentNodes.get(i);

            // Node box [Data | Next]
            HBox nodeBox = new HBox(0);
            nodeBox.setStyle("-fx-background-color: #16213E; -fx-border-color: #FB8500; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8;");

            Label dataPart = new Label(value);
            dataPart.setStyle("-fx-text-fill: white; -fx-padding: 8 12; -fx-font-weight: bold; -fx-font-size: 12px;");

            StackPane pointerPart = new StackPane();
            pointerPart.setPrefSize(24, 34);
            pointerPart.setStyle("-fx-background-color: #242B45; -fx-border-color: #FB8500; -fx-border-width: 0 0 0 2;");
            Circle dot = new Circle(4, Color.web("#FB8500"));
            pointerPart.getChildren().add(dot);

            nodeBox.getChildren().addAll(dataPart, pointerPart);

            // Arrow to next node
            HBox arrowBox = new HBox(0);
            arrowBox.setAlignment(Pos.CENTER);
            Line line = new Line(0, 0, 18, 0);
            line.setStroke(Color.web("#FB8500"));
            line.setStrokeWidth(2.5);
            Polygon arrowHead = new Polygon(0, -4, 6, 0, 0, 4);
            arrowHead.setFill(Color.web("#FB8500"));
            arrowBox.getChildren().addAll(line, arrowHead);

            listContainer.getChildren().addAll(nodeBox, arrowBox);
        }

        Label nullLabel = new Label("NULL");
        nullLabel.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-color: #242B45; -fx-padding: 4 8; -fx-background-radius: 6;");
        listContainer.getChildren().add(nullLabel);
    }

    private void insertAtHead() {
        String newCandy = "🍬 " + (int) (Math.random() * 90 + 10);
        currentNodes.add(0, newCandy);
        renderList();
        statusLabel.setText("Inserted new node [" + newCandy + "] at HEAD in O(1) time!");
    }

    private void reverseList() {
        java.util.Collections.reverse(currentNodes);
        renderList();
        statusLabel.setText("Reversed list in O(N) time by flipping next pointers!");
    }

    @Override
    public void stepForward() {
        insertAtHead();
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
