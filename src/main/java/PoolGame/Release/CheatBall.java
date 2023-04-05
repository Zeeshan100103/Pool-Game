package PoolGame.Release;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import PoolGame.Items.Ball;

/** The instance that implements the Cheats */
public class CheatBall implements DisappearBall {
	List<Ball> balls;
	UpdateScoreObservable scr;
	private static Dictionary<KeyCode, Color> keymap;

	/**
	 * Instance that associate KeyCode with Ball Color
	 */
	public CheatBall() {
		this.balls = null;
		scr = new UpdateScoreObservable();
		keymap = new Hashtable<KeyCode, Color>();
		keymap.put(KeyCode.R, Color.RED);
		keymap.put(KeyCode.Y, Color.YELLOW);
		keymap.put(KeyCode.G, Color.GREEN);
		keymap.put(KeyCode.B, Color.BROWN);
		keymap.put(KeyCode.L, Color.BLUE);
		keymap.put(KeyCode.P, Color.PURPLE);
		keymap.put(KeyCode.A, Color.BLACK);
		keymap.put(KeyCode.O, Color.ORANGE);
	}


	/**
	 * Update the Ball with respect to the observer
	 * @param balls The list of different balls
	 */
	public void updateBallReference(List<Ball> balls) {
		this.balls = balls;
		scr.addObserver();
	}

	/**
	 * Method that disappear balls based on KeyCode
	 * @param key The KeyCode of the key pressed
	 */
	public void cheatUpdate(KeyCode key) {
		// TODO Auto-generated method stub
		System.out.println(key);
		if (keymap.get(key) == null) {
			return;
		}
		Color col = keymap.get(key);
		for (Ball ball : balls) {
			if(ball.getColour() == col) {
				ball.disable();
				scr.updateScore(col);
			}
		}
	}
}
