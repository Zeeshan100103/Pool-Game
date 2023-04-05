package PoolGame.Facade;

/** The class that builds hard game level */
public class GameHard implements GameDifficulty {
	public String type = "config_hard";

	/**
	 * Method to call the hard level file  name
	 * @return The configuration file name of the hard level
	 */
	public String returnFileName() {
		return type;
	}
}
