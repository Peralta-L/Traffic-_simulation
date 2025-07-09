/**
 * File Name: Controllers.java
 * Date: 08/07/2025
 * Author: Luis, Peralta 
 * Purpose: Manages the simulation logic, GUI layout, and button actions for traffic and vehicle updates.
 */
package project3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class Controllers {
    private VBox mainLayout = new VBox(10);
    private Label clockLabel = new Label("Time: 0s");
    private Pane lightDisplay = new Pane();
    static Pane cardisplay = new Pane();
    private List<Car> cars = new ArrayList<>();
    static List<Trafficlights> intersections = new ArrayList<>();
    private Time timeUpdater;
    private int lightcount = 3;
    private int carcount = 3;
    private int spacing = 150;

    public Controllers() {
    	//GUI set up
    	Label text = new Label("Welcome to a Trafic simulation.");
    	Label text1 = new Label("This will set cars on random speeds fro 1-5 and move from left to right");
    	VBox txt = new VBox();
        txt.getChildren().addAll(text, text1);
        txt.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.getChildren().addAll(txt);
        mainLayout.getChildren().add(clockLabel);
        mainLayout.getChildren().add(lightDisplay); 
        mainLayout.getChildren().add(cardisplay);
        
        
     
        // Add control buttons
        Button addcar = new Button("add car");
        Button removecar = new Button("remove car");
        Button start = new Button("Start/Reset");
        Button stop = new Button("Stop");
        Button move = new Button("Continue");
        Button addLight = new Button("Add Light");
        Button removeLight = new Button("Remove Light");

        start.setOnAction(e -> Startsimulation());
        stop.setOnAction(e -> stopSimulation());
        move.setOnAction (e -> continuesimulation());
        addLight.setOnAction(e -> {
            lightcount++;
            addtrafficlights(lightcount);
            refreshLightDisplay();
        });
        removeLight.setOnAction(e -> {
        	lightcount = Math.max(0, lightcount - 1);
            addtrafficlights(lightcount);
            refreshLightDisplay();
        });
        addcar.setOnAction(e -> {
        	carcount++;
        	addcars(carcount);
        	refreshcardisplay();
        });
        removecar.setOnAction(e -> {
        	carcount = Math.max(0, carcount - 1);
        	addcars(carcount);
        	refreshcardisplay();
        });


        HBox controlBox = new HBox(10, addcar, removecar, start, stop, move, addLight, removeLight);
        mainLayout.getChildren().add(controlBox);

        // Initial setup
        addtrafficlights(lightcount);
        addcars(carcount);
        refreshLightDisplay();
        refreshcardisplay();
    }

    public VBox getmainLayout() {
        return mainLayout;
    }

    public void addtrafficlights(int count) {
        intersections.clear();
        for (int i = 0; i < count; i++) {
            int posX = 100 + i * spacing;
            intersections.add(new Trafficlights(posX));
        }
    }
    
    public void addcars(int count) {
    	cars.clear();
    	for (int i = 0; i < count; i++) {
            cars.add(new Car());
        }
    }

    public void refreshLightDisplay() {
        lightDisplay.getChildren().clear();
        

        for (int i = 0; i < intersections.size(); i++) {
            Trafficlights light = intersections.get(i);
            Circle circle = light.getcircle();
            circle.setCenterX(100 + i * spacing);
            circle.setCenterY(100);

            Label label = new Label("Intersection " + (i + 1));
            label.setLayoutX(circle.getCenterX() - 20);
            label.setLayoutY(circle.getCenterY() - 30);

            lightDisplay.getChildren().addAll(circle, label);
        }
    }
    
    public void refreshcardisplay() {
        cardisplay.getChildren().clear();
        for (int i = 0; i < cars.size(); i++) {
            Car auto = cars.get(i);
            auto.height(50 * i + 1, 1 + (int)(Math.random() * 5));
            Circle circle = auto.getcircle();

            Label carLabel = new Label("Car " + (i + 1));
            carLabel.setLayoutX(circle.getCenterX() - 20);
            carLabel.setLayoutY(circle.getCenterY() - 30);

            cardisplay.getChildren().addAll(circle, carLabel, auto.getPositionLabel(), auto.getSpeedLabel());
        }
    }

    public void Startsimulation() {
        addtrafficlights(lightcount);
        addcars(carcount);
        refreshLightDisplay();
        refreshcardisplay();

        for (Trafficlights light : intersections) {
            new Thread(light).start();
        }

        for (Car car : cars) {
            new Thread(car).start();
        }

        if (timeUpdater == null) {
            timeUpdater = new Time(clockLabel);
        } else {
            timeUpdater.stop();
            timeUpdater.reset(); 
            timeUpdater = new Time(clockLabel); 
        }
        new Thread(timeUpdater).start();
        
    }

    public void stopSimulation() {
        for (Trafficlights light : intersections) {
            light.pause();
        }

        for (Car car : cars) {
            car.pause();
        }
        
        timeUpdater.pause();
    }
    
    public void continuesimulation() {
    	 for (Car car : cars) {
    	        car.resume();
    	    }
    	    for (Trafficlights light : intersections) {
    	        light.resume();
    	    }
    	    if (timeUpdater != null) {
    	        timeUpdater.resume();
    	    }
    }
}
