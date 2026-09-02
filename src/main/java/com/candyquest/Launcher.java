package com.candyquest;

/**
 * Bootstrap launcher that doesn't extend javafx.application.Application.
 * Solves the JavaFX runtime initialization when launched from fat jars or standard classpath.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}
