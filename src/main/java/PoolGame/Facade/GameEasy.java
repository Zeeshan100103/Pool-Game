package PoolGame.Facade;

/** The class that builds easy game level */
public class GameEasy implements GameDifficulty {
	public String type = "config_easy";

	/**
	 * Method to call the easy level file  name
	 * @return The configuration file name of the easy level
	 */
	public String returnFileName() {
		return type;
	}
}
