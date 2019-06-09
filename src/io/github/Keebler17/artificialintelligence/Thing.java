package io.github.Keebler17.artificialintelligence;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Thing extends Circle {
	public float fitness;

	private float initx = 100;
	private float inity = 250;

	public float radius = 5;

	private int ptr = 0;
	private int vectorsAmnt = 4999;
	float[][] vectors = new float[vectorsAmnt+1][2];

	public Thing(float[][] vectors) {
		super();
		super.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		resetCircle();

		this.vectors = vectors;
	}

	public void resetCircle() {
		super.setRadius(5);
		super.setCenterX(initx);
		super.setCenterY(inity);
	}

	public void draw(Group group) {
		group.getChildren().add(this);
	}

	public boolean move(Circle dest) {
		if(ptr == vectorsAmnt) return false;
		this.setCenterX(this.getCenterX() + vectors[ptr][0]);
		this.setCenterY(this.getCenterY() + vectors[ptr][1]);
		ptr++;
		return true;
	}

	public float calculateFitness(Circle dest) {
		if(dest.contains(new Point2D(this.getCenterX(), this.getCenterY()))) {
			fitness = 1;
			return 1;
		} else {
			float xDist = 0;
			float yDist = 0;

			if(this.getCenterX() > dest.getCenterX()) {
				xDist = (float) ((this.getCenterX() - this.getRadius()) - (dest.getCenterX() - dest.getRadius()));
			} else {
				xDist = (float) ((dest.getCenterX() - dest.getRadius()) - (this.getCenterX() - this.getRadius()));
			}

			if(this.getCenterY() > dest.getCenterY()) {
				yDist = (float) ((this.getCenterY() - this.getRadius()) - (dest.getCenterY() - dest.getRadius()));
			} else {
				yDist = (float) ((dest.getCenterY() - dest.getRadius()) - (this.getCenterY() - this.getRadius()));
			}
			float dist = (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
			int vectors = 1800;
			dist = vectors-dist;
			fitness = dist/1800;
			return dist/1800;
		}
	}
m
	public void resetPointer() {
		ptr = 0;
	}

	@Override
	public String toString() {
		return String.valueOf(fitness);
	}
}
