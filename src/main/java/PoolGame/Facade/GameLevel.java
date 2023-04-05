package PoolGame.Facade;

import PoolGame.ConfigReader;
import PoolGame.Drawable;
import PoolGame.Game;
import PoolGame.Config.TableConfig;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

/** The class that deals with Game Level */
public class GameLevel implements Drawable{
	protected int level;
	protected Button shape;
	protected String buttonLabel[] = {"Easy","Normal","Hard"};
	protected ReloadJson reloadjson;

	/**
	 * Initialise a game Level method based on config file
	 * @param config A TableConfig information of level
	 * @param levl Level of the game based on easy, normal and hard
	 * @param Confr Configruation from ConfigReader class
	 */
	public GameLevel(TableConfig config, int levl, ConfigReader Confr) {
		level = levl;
        this.shape = new Button(buttonLabel[level]);
        double posX = config.getSizeConfig().getX() +  10 + (75 * level);
        double posY = 60;
        this.shape.setLayoutX(posX);
        this.shape.setLayoutY(posY);
        this.shape.setMinWidth(60);
        reloadjson =  new ReloadJson(Confr);
        this.shape.setStyle("-fx-font: 14 arial;");
        this.registerButtonAction();
	}
		
	public Node getNode() {
		return this.shape;
	}

	public void addToGroup(ObservableList<Node> groupChildren) {
		groupChildren.add(this.shape);
	}
	
	public void setGame(Game gm) {
		reloadjson.setGame(gm);
	}
	
	private void registerButtonAction() {
        this.shape.setOnMouseClicked (
            (actionEvent) -> {
            	reloadjson.loadjson(level);
            }
        );
        
    }

}
