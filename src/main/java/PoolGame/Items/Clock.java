package PoolGame.Items;

import PoolGame.Drawable;
import PoolGame.Config.TableConfig;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** The Clock class for displaying game time. */
public class Clock implements Drawable {
	protected Text shape;
	protected static long starttime;

	/**
	 * Initiallised the Clock class and display on table.
	 * @param config The Pool Table Configuration
	 */
	public Clock(TableConfig config) {
		starttime = System.currentTimeMillis();
        this.shape = new Text();
        double posX = config.getSizeConfig().getX() +  30;
        this.shape.setX(posX);
        this.shape.setY(30);
        this.shape.setFill(Color.BLACK);
        this.shape.setStyle("-fx-font: 20 arial;");
        this.shape.setText("00:00:00");
	}

	/**
	 * Start the Time Clock
	 */
	public void resetTime()
	{
		starttime = System.currentTimeMillis();
		this.shape.setText("00:00:00");
	}

	/**
	 * Reset the Clock Time in HH:MM:SS
	 * @param current_time The current time displayed
	 */
	public void resetTime(long current_time)
	{
		starttime = starttime +  (System.currentTimeMillis() - current_time);
		long diffSeconds = starttime / 1000 % 60;
		long diffMinutes = starttime / (60 * 1000) % 60;
		long diffHours = starttime / (60 * 60 * 1000) % 24;
		
		String formatted = String.format("%02d:%02d:%02d",diffHours,diffMinutes,diffSeconds);
		this.shape.setText(formatted);		
	}

	/**
	 * Update the Time Clock
	 */
	public void updateTime()
	{
		Long timediff = System.currentTimeMillis() - starttime;
		long diffSeconds = timediff / 1000 % 60;
		long diffMinutes = timediff / (60 * 1000) % 60;
		long diffHours = timediff / (60 * 60 * 1000) % 24;
		
		String formatted = String.format("%02d:%02d:%02d",diffHours,diffMinutes,diffSeconds);
		this.shape.setText(formatted);		
	}

	/**
	 * Get the Current time
	 * @return Time The current Time in Millis.
	 */
	public long getTime() {
		return System.currentTimeMillis();
	}

	 /**
	 * Get the `Node` instance of the ball.
	 * @return The node instance for the ball `Shape`.
	 */
	public Node getNode() {
		return this.shape;
	}

	 /**
	 * Add the score to the JavaFX group so they can be drawn.
	 * @param groupChildren The list of `Node` obtained from the JavaFX Group.
	 */
	public void addToGroup(ObservableList<Node> groupChildren) {
		groupChildren.add(this.shape);
		
	}

}
