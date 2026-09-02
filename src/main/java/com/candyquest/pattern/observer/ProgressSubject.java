package com.candyquest.pattern.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <h1>Design Pattern: Observer (Subject / Observable)</h1>
 * <p>
 * <b>Why chosen:</b> Maintains a thread-safe list of {@link ProgressObserver} instances
 * and broadcasts notifications when milestones are achieved.
 * </p>
 */
public class ProgressSubject {
    private final List<ProgressObserver> observers = new CopyOnWriteArrayList<>();

    public void registerObserver(ProgressObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(ProgressObserver observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    public void notifyObservers(ProgressEvent event) {
        for (ProgressObserver observer : observers) {
            try {
                observer.onProgressUpdated(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearObservers() {
        observers.clear();
    }
}
