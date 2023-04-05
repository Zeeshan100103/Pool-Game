package PoolGame.Memento;


import java.util.List;

import PoolGame.Items.Ball;
import PoolGame.Items.Clock;
import PoolGame.Drawable;
import PoolGame.Config.TableConfig;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Undo implements Drawable{
	protected Button shape;
	protected UndoMemento undomem;
	
	public Undo (TableConfig config, Clock clock, List<Ball> balls) {
        this.shape = new Button("Undo");
        double posX = config.getSizeConfig().getX() +  10;
        double posY = 90;
        this.shape.setLayoutX(posX);
        this.shape.setLayoutY(posY);
        this.shape.setMinWidth(60);
        this.shape.setStyle("-fx-font: 14 arial;");
        this.registerbuttonAction();
        undomem = new UndoMemento(config, clock, balls);
	}
		
	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return this.shape;
	}

	@Override
	public void addToGroup(ObservableList<Node> groupChildren) {
		// TODO Auto-generated method stub
		groupChildren.add(this.shape);
	}
	
	public UndoMemento returnmemto()
	{
		return this.undomem;
	}
	
	private void registerbuttonAction() {
        this.shape.setOnMouseClicked (
            (actionEvent) -> {
            	undomem.resort_record();
            }
        );
        
    }

}