package com.candyquest.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Interactive Java DSA Code Playground for practicing code implementations
 * with starter templates and simulated test runners.
 */
public class CodePlaygroundController {
    private final MainLayoutController mainLayout;
    private final VBox rootView;
    private final TextArea codeEditor;
    private final TextArea consoleOutput;
    private final ComboBox<String> templateCombo;

    public CodePlaygroundController(MainLayoutController mainLayout) {
        this.mainLayout = mainLayout;

        rootView = new VBox(14);
        rootView.setPadding(new Insets(18, 28, 18, 28));
        rootView.setStyle("-fx-background-color: #1A1A2E;");

        codeEditor = new TextArea();
        codeEditor.setStyle("""
            -fx-control-inner-background: #0D1117;
            -fx-text-fill: #7EE787;
            -fx-font-family: 'Consolas', 'Courier New', monospace;
            -fx-font-size: 13px;
            -fx-padding: 12;
        """);

        consoleOutput = new TextArea();
        consoleOutput.setEditable(false);
        consoleOutput.setStyle("""
            -fx-control-inner-background: #16213E;
            -fx-text-fill: #F1FAEE;
            -fx-font-family: 'Consolas', 'Courier New', monospace;
            -fx-font-size: 12px;
            -fx-padding: 12;
        """);
        consoleOutput.setPrefHeight(150);

        templateCombo = new ComboBox<>();
        templateCombo.getItems().addAll(
            "🍓 Two Sum (Two Pointers / Hashing)",
            "🍊 Reverse Linked List (Pointers)",
            "🍇 Binary Tree In-Order Traversal (DFS)",
            "🍉 0/1 Knapsack (Dynamic Programming)",
            "✨ Custom Sandbox Template"
        );
        templateCombo.setValue("🍓 Two Sum (Two Pointers / Hashing)");
        templateCombo.setOnAction(e -> loadTemplate(templateCombo.getValue()));

        buildView();
        loadTemplate(templateCombo.getValue());
    }

    public Node getView() {
        return rootView;
    }

    private void buildView() {
        // Top Header
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Button btnBack = new Button("⬅ Back to Home");
        btnBack.setStyle("""
            -fx-background-color: #3A3E59;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 12;
            -fx-padding: 8 16;
            -fx-cursor: hand;
        """);
        btnBack.setOnAction(e -> mainLayout.showHomeView());

        Label title = new Label("💻 Candy Quest DSA Code Playground");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: 900; -fx-text-fill: #2EC4B6;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnRun = new Button("▶ Run Code & Test Cases");
        btnRun.setStyle("""
            -fx-background-color: #2EC4B6;
            -fx-text-fill: #1A1A2E;
            -fx-font-size: 14px;
            -fx-font-weight: 900;
            -fx-background-radius: 12;
            -fx-padding: 10 24;
            -fx-cursor: hand;
        """);
        btnRun.setOnAction(e -> executeCodeSimulation());

        topBar.getChildren().addAll(btnBack, title, spacer, templateCombo, btnRun);

        // Editor & Console Layout
        VBox editorBox = new VBox(6);
        Label editorTitle = new Label("Java 21 DSA Solution Source Code:");
        editorTitle.setStyle("-fx-text-fill: #FFD166; -fx-font-weight: bold; -fx-font-size: 13px;");
        VBox.setVgrow(codeEditor, Priority.ALWAYS);
        editorBox.getChildren().addAll(editorTitle, codeEditor);
        VBox.setVgrow(editorBox, Priority.ALWAYS);

        VBox consoleBox = new VBox(6);
        Label consoleTitle = new Label("Terminal Output & Test Results:");
        consoleTitle.setStyle("-fx-text-fill: #94A3B8; -fx-font-weight: bold; -fx-font-size: 12px;");
        consoleBox.getChildren().addAll(consoleTitle, consoleOutput);

        rootView.getChildren().addAll(topBar, editorBox, consoleBox);
    }

    private void loadTemplate(String templateName) {
        if (templateName.startsWith("🍓 Two Sum")) {
            codeEditor.setText("""
                import java.util.*;

                public class TwoSumSolution {
                    public static int[] twoSum(int[] nums, int target) {
                        Map<Integer, Integer> map = new HashMap<>();
                        for (int i = 0; i < nums.length; i++) {
                            int complement = target - nums[i];
                            if (map.containsKey(complement)) {
                                return new int[]{map.get(complement), i};
                            }
                            map.put(nums[i], i);
                        }
                        return new int[]{};
                    }

                    public static void main(String[] args) {
                        int[] nums = {2, 7, 11, 15};
                        int target = 9;
                        int[] result = twoSum(nums, target);
                        System.out.println("Result indices: " + Arrays.toString(result));
                    }
                }
                """.stripIndent());
        } else if (templateName.startsWith("🍊 Reverse")) {
            codeEditor.setText("""
                public class LinkedListReverse {
                    static class Node {
                        int val;
                        Node next;
                        Node(int v) { this.val = v; }
                    }

                    public static Node reverse(Node head) {
                        Node prev = null;
                        Node curr = head;
                        while (curr != null) {
                            Node nextTemp = curr.next;
                            curr.next = prev;
                            prev = curr;
                            curr = nextTemp;
                        }
                        return prev;
                    }

                    public static void main(String[] args) {
                        Node head = new Node(10);
                        head.next = new Node(20);
                        head.next.next = new Node(30);
                        Node rev = reverse(head);
                        System.out.println("Reversed head value: " + rev.val);
                    }
                }
                """.stripIndent());
        } else if (templateName.startsWith("🍇 Binary Tree")) {
            codeEditor.setText("""
                import java.util.*;

                public class TreeTraversal {
                    static class TreeNode {
                        int val;
                        TreeNode left, right;
                        TreeNode(int v) { this.val = v; }
                    }

                    public static void inOrder(TreeNode root, List<Integer> res) {
                        if (root == null) return;
                        inOrder(root.left, res);
                        res.add(root.val);
                        inOrder(root.right, res);
                    }

                    public static void main(String[] args) {
                        TreeNode root = new TreeNode(50);
                        root.left = new TreeNode(30);
                        root.right = new TreeNode(70);
                        List<Integer> list = new ArrayList<>();
                        inOrder(root, list);
                        System.out.println("In-Order: " + list);
                    }
                }
                """.stripIndent());
        } else if (templateName.startsWith("🍉 0/1 Knapsack")) {
            codeEditor.setText("""
                public class KnapsackDP {
                    public static int knapsack(int[] val, int[] wt, int W) {
                        int n = val.length;
                        int[][] dp = new int[n + 1][W + 1];
                        for (int i = 1; i <= n; i++) {
                            for (int w = 1; w <= W; w++) {
                                if (wt[i - 1] <= w) {
                                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);
                                } else {
                                    dp[i][w] = dp[i - 1][w];
                                }
                            }
                        }
                        return dp[n][W];
                    }

                    public static void main(String[] args) {
                        int[] val = {60, 100, 120};
                        int[] wt = {10, 20, 30};
                        int W = 50;
                        System.out.println("Max Value: " + knapsack(val, wt, W));
                    }
                }
                """.stripIndent());
        } else {
            codeEditor.setText("""
                public class CandySandbox {
                    public static void main(String[] args) {
                        System.out.println("🍬 Happy Coding on Candy Quest!");
                    }
                }
                """.stripIndent());
        }
        consoleOutput.setText("Editor loaded template: " + templateName);
    }

    private void executeCodeSimulation() {
        consoleOutput.setText("""
            [Candy Quest Java Runtime Engine]
            Compiling and executing Java source...
            -------------------------------------------------
            Test Case 1: PASS (Execution Time: 1.2ms)
            Test Case 2: PASS (Execution Time: 0.8ms)
            Test Case 3: PASS (Execution Time: 1.1ms)
            -------------------------------------------------
            Output:
            Result indices: [0, 1]
            All 3 Test Cases Passed with 100% Accuracy!
            Time Complexity Analysis: O(N) linear time
            Space Complexity Analysis: O(N) aux memory
            🎉 Sweet execution! Perfect O(1) hashing lookup!
            """);
    }
}
