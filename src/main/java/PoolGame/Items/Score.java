package PoolGame.Items;

import java.util.Observable;
import java.util.Observer;

import PoolGame.Drawable;
import PoolGame.Config.TableConfig;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** The class that handles with Game Score. */
public class Score  implements Drawable, Observer {
	private static Score instance;
	protected int score;
	protected Text shape;

	/**
	 * Initialise the Game Score.
	 */
	public Score () {
		score = 0;
        this.shape = new Text();
        this.shape.setY(30);
        this.shape.setFill(Color.BLACK);
        this.shape.setStyle("-fx-font: 20 arial;");
        this.shape.setText("Score : 0");
	}

	 /**
	 * Update the position of score on the application.
	 * @param config  The Table configuration.
	 */
	public void updatePosition(TableConfig config) {
		double posX = config.getSizeConfig().getX() +  120;
		this.shape.setX(posX);
	}

	 /**
	 * Get the Current Score of the game.
	 * @return The current score.
	 */
	public int getScore() {
		return this.score;
	}

	 /**
	 * Get the `Node` instance of the ball.
	 * @return The node instance for the ball `Shape`.
	 */
	public Node getNode() {
		return this.shape;
	}

	 /**
	 * Add the score to the JavaFX group, so they can be drawn.
	 * @param groupChildren The list of `Node` obtained from the JavaFX Group.
	 */
	public void addToGroup(ObservableList<Node> groupChildren) {
		groupChildren.add(this.shape);
		
	}

	static {
        instance = new Score();
    }

	/**
	 * Get the Instance of the Score.
	 * @return instance Return the Score at that instance
	 */
    public static Score getInstance() {
        return instance;
    }

	 /**
	 * Update the Score on the application.
	 * @param  o The Observable component for observer pattern.
	 * @param arg The Object Score.
	 */
	public void update(Observable o, Object arg) {
		score = (int) arg;
		String score_str = String.format("Score : %d",score);
		this.shape.setText(score_str);
	}
	
}
