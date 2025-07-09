/**
 * File Name: Main.java
 * Date: 08/07/2025
 * Author: Luis, Peralta
 * Purpose: Entry point for the Traffic Simulation GUI. Sets up the primary stage and scene and aquairs the layout from controllers.
 */
package project3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Controllers controller = new Controllers();
        VBox layout = controller.getmainLayout();
        
        StackPane root = new StackPane();
        root.getChildren().add(layout);
        StackPane.setAlignment(layout, Pos.CENTER);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Traffic Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
