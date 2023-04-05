package PoolGame.Memento;

import PoolGame.Items.Ball;

/** An interface for undo configuration class */
public interface UndoConfig {

	/**
	 * Update the Ball and the disable state saved
	 * @param ball An Ball present on Table
	 * @param cur An bollean state of ball regarding disable
	 */
	public void update(Ball ball, Boolean cur);
}
