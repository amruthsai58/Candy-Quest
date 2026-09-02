package com.candyquest.view.component;

import com.candyquest.config.AnimationConfig;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Overlay pane that spawns animated candy particles (jelly beans, chews, candy drops, sparkles).
 */
public class ParticleEffectPane extends Pane {
    private static final Random random = new Random();
    private static final Color[] CANDY_COLORS = {
        Color.web("#E63946"), // Strawberry Red
        Color.web("#FB8500"), // Juicy Orange
        Color.web("#7209B7"), // Royal Grape
        Color.web("#2EC4B6"), // Watermelon Green
        Color.web("#FFB703"), // Honey Lemon
        Color.web("#FF5D8F"), // Pink Bubblegum
        Color.web("#4CC9F0")  // Sour Blue
    };

    public ParticleEffectPane() {
        setMouseTransparent(true); // Don't block clicks underneath
    }

    /**
     * Spawns a candy burst explosion at the given (x, y) coordinates.
     */
    public void spawnCandyBurst(double x, double y, int count) {
        for (int i = 0; i < count; i++) {
            Node candy = createRandomCandyShape();
            candy.setLayoutX(x);
            candy.setLayoutY(y);
            getChildren().add(candy);

            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = 80 + random.nextDouble() * 220;
            double targetX = Math.cos(angle) * distance;
            double targetY = Math.sin(angle) * distance - 50; // slight upward pop

            TranslateTransition translate = new TranslateTransition(AnimationConfig.BURST_PARTICLE_DURATION, candy);
            translate.setByX(targetX);
            translate.setByY(targetY);
            translate.setInterpolator(Interpolator.SPLINE(0.2, 0.8, 0.2, 1.0));

            RotateTransition rotate = new RotateTransition(AnimationConfig.BURST_PARTICLE_DURATION, candy);
            rotate.setByAngle(random.nextInt(720) - 360);

            ScaleTransition scale = new ScaleTransition(AnimationConfig.BURST_PARTICLE_DURATION, candy);
            scale.setFromX(1.4);
            scale.setFromY(1.4);
            scale.setToX(0.2);
            scale.setToY(0.2);

            FadeTransition fade = new FadeTransition(AnimationConfig.BURST_PARTICLE_DURATION, candy);
            fade.setFromValue(1.0);
            fade.setToValue(0.0);

            ParallelTransition burst = new ParallelTransition(translate, rotate, scale, fade);
            burst.setOnFinished(e -> getChildren().remove(candy));
            burst.play();
        }
    }

    /**
     * Spawns falling candy drops from top to bottom (celebrating quiz success or level up).
     */
    public void spawnFallingCandyRain(int count) {
        double width = getWidth() > 0 ? getWidth() : 900;
        double height = getHeight() > 0 ? getHeight() : 600;

        for (int i = 0; i < count; i++) {
            Node candy = createRandomCandyShape();
            double startX = random.nextDouble() * width;
            candy.setLayoutX(startX);
            candy.setLayoutY(-30 - random.nextDouble() * 100);
            getChildren().add(candy);

            Duration fallDuration = Duration.millis(900 + random.nextInt(600));

            TranslateTransition fall = new TranslateTransition(fallDuration, candy);
            fall.setByY(height + 100);
            fall.setByX((random.nextDouble() - 0.5) * 120);
            fall.setInterpolator(Interpolator.EASE_IN);

            RotateTransition rotate = new RotateTransition(fallDuration, candy);
            rotate.setByAngle(AnimationConfig.CANDY_DROP_ROTATION_DEGREES * (random.nextBoolean() ? 1 : -1));

            FadeTransition fade = new FadeTransition(AnimationConfig.CANDY_DROP_FADE_DURATION, candy);
            fade.setDelay(Duration.millis(300));
            fade.setFromValue(1.0);
            fade.setToValue(0.0);

            ParallelTransition drop = new ParallelTransition(fall, rotate, fade);
            drop.setOnFinished(e -> getChildren().remove(candy));
            drop.play();
        }
    }

    private Node createRandomCandyShape() {
        Color color = CANDY_COLORS[random.nextInt(CANDY_COLORS.length)];
        int shapeType = random.nextInt(3);
        double size = 12 + random.nextDouble() * 10;

        return switch (shapeType) {
            case 0 -> {
                // Jelly bean / round candy
                Circle c = new Circle(size / 2, color);
                c.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.6));
                c.setStrokeWidth(1.5);
                yield c;
            }
            case 1 -> {
                // Soft chew square
                Rectangle r = new Rectangle(size, size, color);
                r.setArcWidth(6);
                r.setArcHeight(6);
                r.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.6));
                r.setStrokeWidth(1.5);
                yield r;
            }
            default -> {
                // Diamond candy
                Polygon p = new Polygon(
                    size / 2, 0,
                    size, size / 2,
                    size / 2, size,
                    0, size / 2
                );
                p.setFill(color);
                p.setStroke(Color.WHITE.deriveColor(0, 1, 1, 0.6));
                p.setStrokeWidth(1.5);
                yield p;
            }
        };
    }
}
