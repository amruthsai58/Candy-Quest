package com.candyquest.config;

import javafx.animation.Interpolator;
import javafx.util.Duration;

/**
 * Centralized Animation Configuration for Candy Quest.
 * Tunable constants for all JavaFX transitions, particle systems, mascot bounces, and easing.
 */
public final class AnimationConfig {

    private AnimationConfig() {}

    // Screen Transitions
    public static final Duration SCREEN_TRANSITION_DURATION = Duration.millis(300);
    public static final Interpolator SCREEN_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1.0);

    // Button Micro-Interactions (Soft Chew Squish)
    public static final Duration BUTTON_PRESS_DURATION = Duration.millis(120);
    public static final double BUTTON_SQUISH_SCALE_X = 0.94;
    public static final double BUTTON_SQUISH_SCALE_Y = 0.94;

    // Candy Pack Burst Intro
    public static final Duration PACK_TEAR_DURATION = Duration.millis(600);
    public static final Duration BURST_PARTICLE_DURATION = Duration.millis(900);
    public static final int BURST_PARTICLE_COUNT = 45;

    // Mascot (Candy Roo) Animations
    public static final Duration MASCOT_IDLE_CYCLE = Duration.millis(1200);
    public static final double MASCOT_BOUNCE_HEIGHT = 14.0;
    public static final Duration MASCOT_HOP_DURATION = Duration.millis(450);
    public static final Duration MASCOT_CHEER_DURATION = Duration.millis(700);

    // Candy Drop Particles (Quiz Success)
    public static final Duration CANDY_DROP_FALL_DURATION = Duration.millis(1100);
    public static final Duration CANDY_DROP_FADE_DURATION = Duration.millis(900);
    public static final double CANDY_DROP_ROTATION_DEGREES = 360.0;
    public static final int QUIZ_SUCCESS_PARTICLE_COUNT = 30;

    // Progress Bar & Candy Jar Fill
    public static final Duration JAR_FILL_DURATION = Duration.millis(800);
    public static final Interpolator ELASTIC_OUT = Interpolator.SPLINE(0.2, 0.8, 0.3, 1.0);

    // Topic Map Pulsing Current Node
    public static final Duration NODE_PULSE_DURATION = Duration.millis(1000);
    public static final double NODE_PULSE_SCALE = 1.12;

    // Reward / Free Toy Inside Reveal
    public static final Duration TOY_REVEAL_SPIN_DURATION = Duration.millis(1200);
    public static final Duration TOY_GOLD_SHINE_DURATION = Duration.millis(1500);
}
