package PoolGame.Facade;

/** The class that builds Medium game level */
public class GameMedium implements GameDifficulty {
	public String type = "config_normal";

	/**
	 * Method to call the Medium level file  name
	 * @return The configuration file name of the Medium level
	 */
	public String returnFileName() {
		return type;
	}
}
