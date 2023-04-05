package PoolGame.Facade;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import PoolGame.ConfigReader;
import PoolGame.Game;
import PoolGame.ConfigReader.ConfigKeyMissingException;

/** The class that is responsible for different level Json */
public class ReloadJson {
	protected GameDifficulty gamejsonEasy;
	protected GameDifficulty gamejsonNorm;
	protected GameDifficulty gamejsonHard;
	protected Game game;
	protected ConfigReader config;

	/**
	 * Initialise the json file for the level
	 * @param Confr Configruation from ConfigReader class
	 */
	public ReloadJson(ConfigReader Confr) {
		this.config = Confr;
		this.gamejsonEasy = new GameEasy();
		this.gamejsonNorm = new GameMedium();
		this.gamejsonHard = new GameHard();
	}
	
	public void setGame(Game gm) {
		game= gm;
	}

	/**
	 * Method to select the file path used for file reading later on
	 * @param level A level of difficult selected
	 * @return filepath The json file path for the level selected
	 */
	private String returnJsonfile(int level) {
		String filepath = "src/main/resources/";
		switch(level){
			case 0:	filepath = filepath + gamejsonEasy.returnFileName() + ".json";
			break;
			case 1: filepath = filepath + gamejsonNorm.returnFileName() + ".json";
			break;
			case 2:filepath = filepath + gamejsonHard.returnFileName() + ".json";
			break;
		}
		return filepath;
		
	}

	/**
	 * Initialise the json file of a particular level selected
	 * @param level A level of difficult selected

	 */
	public void loadjson(int level) {
    	String confPath = returnJsonfile(level);
    	System.out.println(confPath);
        try {
        	config.updateconfig(confPath, false);
			game.new_setup(config);
			game.reset();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ConfigKeyMissingException e) {
			e.printStackTrace();
		}
	}

}
