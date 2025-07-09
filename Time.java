/**
 * File Name: Time.java
 * Date: 08/07/2025
 * Author: Luis, Peralta 
 * Purpose: Manages the clock timer for the simulation. Updates label display every second
 *          and supports pause/resume functionality using threads.
 */
package project3;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Time implements Runnable {
    private Label display;
    private volatile boolean active = true;
    private volatile boolean paused = false;
    private int seconds = 0;

    public Time(Label display) {
        this.display = display;
    }

    public void reset() {
        seconds = 0;
        Platform.runLater(() -> display.setText("Time: 0s"));
    }

    public void stop() {
        active = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    @Override
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

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            seconds++;
            Platform.runLater(() -> display.setText("Time: " + seconds + "s"));
        }
    }
}