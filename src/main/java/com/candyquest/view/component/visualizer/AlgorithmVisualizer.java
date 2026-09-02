package com.candyquest.view.component.visualizer;

import javafx.scene.Node;

/**
 * Common interface for interactive DSA Algorithm Visualizers in Candy Quest.
 */
public interface AlgorithmVisualizer {

    /**
     * Returns the root JavaFX node containing the visualizer UI.
     */
    Node getViewNode();

    /**
     * Resets visualizer data to starting state.
     */
    void reset();

    /**
     * Executes one step forward in the algorithm animation.
     */
    void stepForward();

    /**
     * Runs the animation continuously.
     */
    void play();

    /**
     * Pauses the animation.
     */
    void pause();

    /**
     * Explanatory status caption of what current step is doing.
     */
    String getCurrentStepDescription();
}
