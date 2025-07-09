/**
 * File Name: Car.java
 * Date: 08/07/2025
 * Author: Luis, Peralta 
 * Purpose: Represents a car in the traffic simulation. Each car is a runnable thread
 *          that moves horizontally, reacts to traffic light states, and updates its labels.
 */
package project3;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Car implements Runnable {
    private double speed = 3;
    private double position = 0;
    private Circle carcircle;
    private Label positionLabel;
    private Label speedLabel;
    private boolean active = true;
    private volatile boolean paused = false;

    public Car() {
        carcircle = new Circle(10, Color.BLACK);
        carcircle.setCenterX(0);

        positionLabel = new Label();
        speedLabel = new Label(String.format("Speed: %.1f m/s", speed));
    }

    public void height(double height, int speed) {
        carcircle.setCenterY(height);
        this.speed = speed;
        speedLabel.setText(String.format("Speed: %.1f m/s", this.speed));
    }
 // Moves the car forward and updates label positions
    public void move() {
        position += speed;
        carcircle.setCenterX(carcircle.getCenterX() + speed);

        // Update labels based on new position
        positionLabel.setText(String.format("X: %.1f, Y: %.1f", carcircle.getCenterX(), carcircle.getCenterY()));
        positionLabel.setLayoutX(carcircle.getCenterX() + 20);
        positionLabel.setLayoutY(carcircle.getCenterY() - 10);

        speedLabel.setLayoutX(carcircle.getCenterX() + 20);
        speedLabel.setLayoutY(carcircle.getCenterY() + 10);
    }

    public Circle getcircle() {
        return carcircle;
    }

    public Label getPositionLabel() {
        return positionLabel;
    }

    public Label getSpeedLabel() {
        return speedLabel;
    }

    public double getspeed() {
        return speed;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
    // Thread logic for car movement
    public void run() {
        active = true;
        while (active) {
            if (paused) {
                try {
                    Thread.sleep(100);
                    continue;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            boolean shouldStop = false;

            for (Trafficlights light : Controllers.intersections) {
                double carX = carcircle.getCenterX();
                int lightX = light.getPositionX();

                if (Math.abs(carX - lightX) < 10) {
                    if (light.getlight() == TrafficLightColor.RED) {
                        shouldStop = true;
                        break;
                    }
                }
            }

            if (carcircle.getCenterX() >= Controllers.cardisplay.getWidth()) {
                stop();
                break;
            }

            if (shouldStop) {
                Platform.runLater(() -> speedLabel.setText("Speed: 0.0 m/s"));
            } else {
                Platform.runLater(() -> {
                    speedLabel.setText(String.format("Speed: %.1f m/s", speed));
                    move();
                });
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        active = false;
    }
}
