package io.github.Keebler17.artificialintelligence;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	static int amntThings = 20;
	Thing[] things = new Thing[amntThings];

	static float destinationX = 1800;
	static float destinationY = 250;

	final int mutationRate = 5; // 5 in 5000

	Circle destination = new Circle();

	@Override
	public void start(Stage stage) throws InterruptedException {
		Group root = new Group();
		Scene scene = new Scene(root, 1920, 500);

		stage.setScene(scene);
		stage.show();


		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				event.consume();
			}
		});

		destination.setCenterX(destinationX);
		destination.setCenterY(destinationY);
		destination.setRadius(50f);
		destination.setFill(Color.RED);
		root.getChildren().add(destination);

		for(int i = 0; i < amntThings; i++) {
			things[i] = new Thing(generateRandomVectors());
			things[i].draw(root);
		}

		Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {

			@Override
		    public void handle(ActionEvent event) {
		    	boolean endGeneration = false;
		        for(int i = 0; i < amntThings; i++) {
		        	if(!things[i].move(destination)) endGeneration = true;
		        }

		        if(endGeneration) {
		        	sort();
		        	root.getChildren().clear();
		        	root.getChildren().add(destination);
		        	reproduce();
		        	resetAll();
		        	for(int i = 0; i < 20; i++) {
		    			things[i].draw(root);
		    		}
		        }
		    }
		}));
		timeLine.setCycleCount(Timeline.INDEFINITE);
		timeLine.play();
	}

	public void sort() {
		for(int i = 0; i < amntThings; i++) {
			things[i].calculateFitness(destination);
		}
		for(int i = 0; i < amntThings; i++) {
			for(int j = 0; j < amntThings; j++) {
				if(things[i].fitness > things[j].fitness) {
					Thing temp = things[i];
					things[i] = things[j];
					things[j] = temp;
				}
			}
		}
	}

	public void reproduce() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 4999; j++) {
				for(int k = 0; k < 2; k++) {
					things[i+10].vectors[j][k] = things[i].vectors[j][k];
				}
			}
		}

		for(int i = 10; i < 20; i++) {
			things[i] = mutate(things[i]);
		}
	}

	public Thing mutate(Thing thing) {
		Random r = new Random();
		for(int i = 0; i < 4999; i++) {
			for(int j = 0; j < 2; j++) {
				if(r.nextInt(5000) < 300) {
					thing.vectors[i][j] = r.nextInt(3) - 1;
				}
			}
		}

		return thing;
	}

	public void resetAll() {
		for(int i = 0; i < 20; i++) {
			things[i].fitness = 0;
			things[i].resetPointer();
			things[i].resetCircle();
		}
	}

	public float[][] generateRandomVectors() {
		float[][] vectors = new float[5000][2];
		int ptr = 0;
		while(ptr < 5000) {
			vectors[ptr][0] = ThreadLocalRandom.current().nextInt(-1, 2);
			vectors[ptr][1] = ThreadLocalRandom.current().nextInt(-1, 2);
			ptr++;
		}
		return vectors;
	}

	public static void main(String[] args) {
		Platform.setImplicitExit(false);
		launch(args);
	}
}
