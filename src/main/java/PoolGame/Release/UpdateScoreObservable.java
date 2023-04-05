package PoolGame.Release;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Observable;

import PoolGame.Items.Ball;
import PoolGame.Items.Score;
import javafx.scene.paint.Color;

/** The class that implements the Score */
public class UpdateScoreObservable extends Observable {
	private static Dictionary<Color, Integer> scoremap;
	private static Score score;

	/**
	 * Instance that associate Ball Color with their score value
	 */
	public UpdateScoreObservable() {
		scoremap = new Hashtable<Color, Integer>();
        scoremap.put(Color.RED,1);
        scoremap.put(Color.YELLOW,2);
        scoremap.put(Color.GREEN,3);
        scoremap.put(Color.BROWN,4);
        scoremap.put(Color.BLUE,5);
        scoremap.put(Color.PURPLE,6);
        scoremap.put(Color.BLACK,7);
        scoremap.put(Color.ORANGE,8);
	}

	/**
	 * update the Score based on ball  inside the pocket
	 * @param ball The Balls on the Table
	 */
	public void updateScore(Ball ball) {
		Color col = ball.getColour();
		int value = 0;
		if(scoremap.get(col) == null)
			return;
		value = scoremap.get(col);
		int score = UpdateScoreObservable.score.getScore() + value;
		setChanged();
		notifyObservers(score);
	}

	/**
	 * update the Score based on  color of the ball
	 * @param col The Color of the ball on the Table
	 */
	public void updateScore(Color col) {
		int value = 0;
		if(scoremap.get(col) == null)
			return;
		value = scoremap.get(col);
		int score = UpdateScoreObservable.score.getScore() + value;
		setChanged();
		notifyObservers(score);
	}

	/**
	 * Reset the Score to Default 0
	 */
	public void resetScore() {
		setChanged();
		notifyObservers(0);
	}

	/**
	 * Register the Score to the Observer
	 * @param score The Score of the Game
	 */
	public void registerScore(Score score) {
		UpdateScoreObservable.score = score;
	}

	/**
	 * Add the observer to the score
	 */
	public void addObserver() {
		this.addObserver(score);
	}

	/**
	 * Reset the Score to specific observer score
	 * @param scr The Score input
	 */
	public void resetScore(int scr) {
		setChanged();
		notifyObservers(scr);
	}

}
