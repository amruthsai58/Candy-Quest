package com.candyquest.pattern.observer;

/**
 * <h1>Design Pattern: Observer (Observer Interface)</h1>
 * <p>
 * <b>Why chosen:</b> In Candy Quest, multiple decoupled visual components (e.g. Header XP Meter,
 * Candy Jar Views, Candy Roo Mascot Reaction Box, and Badge Notifications) must update instantly
 * when the user gains XP, finishes a quiz, or unlocks a reward, without tightly coupling the
 * quiz/topic business logic to every UI node.
 * </p>
 */
@FunctionalInterface
public interface ProgressObserver {
    /**
     * Called whenever a progression event occurs.
     *
     * @param event the progress event containing topic, XP, badge, or toy details
     */
    void onProgressUpdated(ProgressEvent event);
}
