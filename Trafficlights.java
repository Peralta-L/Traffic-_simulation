/**
 * File Name: Trafficlights.java
 * Date: 08/07/2025
 * Author: Luis, Peralta 
 * Purpose: Represents a traffic light at an intersection. Each light cycles through
 *          RED, GREEN, and YELLOW states in its own thread.
 */
package project3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

enum TrafficLightColor {  
	  RED, GREEN, YELLOW 
	} 

public class Trafficlights implements Runnable{
	private TrafficLightColor color;
	private TrafficLightColor light = TrafficLightColor.RED;
    private Circle lightcircle;
    private boolean running;
    private int positionx;
    private boolean paused = false;
    
    public Trafficlights(TrafficLightColor init) {  
		color = init; 
	  }

    public Trafficlights() {
    	lightcircle = new Circle (10, Color.RED);
    	color = TrafficLightColor.RED; 
    }
    
    public Trafficlights(int x) {
        lightcircle = new Circle(10, Color.RED);
        positionx = x;
        lightcircle.setCenterX(x);
        lightcircle.setCenterY(100); // Adjust as needed
        color = TrafficLightColor.RED;
    }
    public int getPositionX() {
        return positionx;
    }
    
    public Circle getcircle() {
    	return lightcircle;
    }
    
    public TrafficLightColor getlight() {
    	return light;
    }
    
    public void stop() {
    	running = false;
    }
    
    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
    
    @Override
    public void run() {
        running = true;
        while (running) {
        	 // Check every 100ms if resumed
            if (paused) {
                try {
                    Thread.sleep(100);
                    continue;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            try {
                switch (light) {
                    case RED:
                        light = TrafficLightColor.GREEN;
                        colorchange(Color.GREEN);
                        Thread.sleep(5000);
                        break;
                    case GREEN:
                        light = TrafficLightColor.YELLOW;
                        colorchange(Color.YELLOW);
                        Thread.sleep(3000);
                        break;
                    case YELLOW:
                        light = TrafficLightColor.RED;
                        colorchange(Color.RED);
                        Thread.sleep(8000);
                        break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
	private void colorchange(Color color) {
		javafx.application.Platform.runLater(()-> {
			lightcircle.setFill(color);
		});
	}
}
