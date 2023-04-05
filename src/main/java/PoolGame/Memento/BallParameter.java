package PoolGame.Memento;

import PoolGame.Items.Ball;
import PoolGame.Memento.UndoConfig;
import javafx.scene.paint.Color;

/** The class that deals with Balls for undo function*/
public class BallParameter implements UndoConfig {
    private double x;
    private double y;
    private Color colour;
    private boolean disabled;

    /**
     * Initialise a config for position using a JSONObject
     * @param ball A JSONObject containing key 'x' and 'y'
     */
    public BallParameter(Ball ball) {
    	this.x = ball.getXPos();
    	this.y = ball.getYPos();
    	this.disabled = ball.isDisabled();
    	this.colour = ball.getColour();
    }
     
     /**
     * Get the x coordinate of the object as defined in the config
     * @return The x coordinate of the object position
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the y coordinate of the object as defined in the config
     * @return The y coordinate of the object position
     */
    public double getY() {
        return this.y;
    }
    /**
     * Get boolean value of ball is disable
     * @return The whether the ball is disabled
     */
    public Boolean getmfd() {
        return this.disabled;
    }
    /**
     * Get color of the Ball
     * @return colour The color of the ball
     */
    public Color getcolour() {
    	return this.colour;
    }

    /**
     * Update the Ball position from undo
     * @param ball  The Ball present on Table
     * @param cur THe boolean for ball is disable or not
     */
	public void update(Ball ball, Boolean cur) {
    	this.x = ball.getXPos();
    	this.y = ball.getYPos();
    	this.disabled = cur;
    	this.colour = ball.getColour();
	}
}