package edu.dtcc.csc241.gui;

import edu.edu.csc241.aircraft.SystemDriver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Tester extends Application {
	public static void main(String[] args) { 
		SystemDriver driver = new SystemDriver();
		
		driver.initSystem(1000, 600.0, 0.0);
		Thread t = new Thread(driver);
		t.start();
		launch(args);
		
		
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane root = new StackPane();
		root.getChildren().add(btn);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}