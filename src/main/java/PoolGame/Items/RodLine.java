package PoolGame.Items;

import PoolGame.Drawable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/** A cue Stick */
public class RodLine implements Drawable{
	protected Line shape;

	/**
	 * Build the cue Stick
	 */
	RodLine() {
		this.shape = new Line();
		this.shape.setStartX(0);
		this.shape.setStartY(0);
		this.shape.setStroke(Color.DARKGOLDENROD);
		this.shape.setStrokeWidth(8);
		this.shape.setStrokeType(StrokeType.CENTERED);
		this.shape.setStrokeLineCap(StrokeLineCap.ROUND);
	}

	/**
	 * Add the table and the balls to the JavaFX group so they can be drawn.
	 * @param groupChildren The list of `Node` obtained from the JavaFX Group.
	 */
	public void addToGroup(ObservableList<Node> groupChildren) {
		groupChildren.add(this.shape);
		
	}

	/**
	 * Get the `Node` instance of the ball.
	 * @return The node instance for the ball `Shape`.
	 */
	public Node getNode() {
		return this.shape;
	}

	/**
	 * Remove the Cue Stick from the Table.
	 */
	public void disableRod() {
		this.shape.setVisible(false);
	}

	/**
	 * Enable the Cue Stick on the Table.
	 */
	public void enableRod() {
		this.shape.setVisible(true);
	}

	/**
	 * Update the Cue Stick position.
	 * @param x The x coordinated of the cuestick
	 * @param y The y coordinated of the cuestick
	 */
	public void updaterod(double x, double y) {
		this.shape.setStartX(x);
		this.shape.setStartY(y);
	}

	/**
	 * Update the Cue Stick angle value.
	 * @param startx The start x coordinated of the cuestick
	 * @param starty The start y coordinated of the cuestick
	 * @param endx The end x coordinated of the cuestick
	 * @param endy The end y coordinated of the cuestick
	 */
	public void updateangle(double startx, double starty, double endx, double endy) {
		double dist = Math.sqrt(Math.pow((startx - endx),2) + Math.pow((starty - endy),2));
		double rod_length = 150;
		if (dist < 2) {
			this.disableRod();
			return;
		} else {
			this.enableRod();
		}
		double a = endy - starty;
		double radian_angle = Math.acos(a/dist);
		double headxarc = 0;
		double headyarc = 0;
		headxarc = Math.sin(radian_angle) * rod_length;
		headyarc = Math.cos(radian_angle) * rod_length;
		if (endy < starty) {
			headyarc = starty - Math.abs(headyarc);
		} else {
			headyarc = starty + Math.abs(headyarc);
		}
		if (endx < startx) {
			headxarc = startx - Math.abs(headxarc);
		} else {
			headxarc = startx + Math.abs(headxarc);
		}
		this.shape.setEndX(headxarc);
		this.shape.setEndY(headyarc);
		//Start Node
		rod_length = 16;
		headxarc = Math.sin(radian_angle) * rod_length;
		headyarc = Math.cos(radian_angle) * rod_length;

		if (endy < starty) {
			headyarc = starty - Math.abs(headyarc);
		} else {
			headyarc = starty + Math.abs(headyarc);
		}
		if (endx < startx) {
			headxarc = startx - Math.abs(headxarc);
		} else {
			headxarc = startx + Math.abs(headxarc);
		}
		this.shape.setStartX(headxarc);
		this.shape.setStartY(headyarc);
	}
}
