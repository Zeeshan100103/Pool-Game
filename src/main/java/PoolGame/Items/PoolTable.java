package PoolGame.Items;

import java.util.*;


import PoolGame.Drawable;
import PoolGame.Facade.GameLevel;
import PoolGame.Game;
import PoolGame.Config.TableConfig;
import PoolGame.Items.Ball.BallType;
import PoolGame.Memento.Undo;
import PoolGame.Release.UpdateScoreObservable;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** A pool table */
public class PoolTable implements Drawable {
    private long[] dim;
    private String colourName;
    private Color colour;
    private double friction;
    private Rectangle shape;
    private List<Ball> balls;
    private List<Pocket> pockets;
    private final double POCKET_OFFSET = 5;
    
    private Clock clk;
    private UpdateScoreObservable scre;
    private Score score;
    private Undo udo;
    private GameLevel glevel[];

    /**
     * Build the pool table with the provided values
     * @param colourName The colour of the table in String
     * @param friction The friction of the table
     * @param dimX The dimension of the table in the x-axis
     * @param dimY The dimension of the table in the y-axis
     */
    public PoolTable(String colourName, double friction, long dimX, long dimY) {
        this.init(colourName, friction, dimX, dimY);
    }

    /**
     * Build the pool table using a `TableConfig` instance
     * @param config The `TableConfig` instance
     */
    public PoolTable(TableConfig config) {
        this.init(config.getColour(),
            config.getFriction(),
            config.getSizeConfig().getX(),
            config.getSizeConfig().getY());
    }

    /**
     * Initialising the Default Pool Table
     * @param colourName The color of the Table
     * @param friction The friction of the Table
     * @param dimX The width of table
     * @param dimY The height of the Table
     */
    private void init(String colourName, double friction, long dimX, long dimY) {
        this.colourName = colourName;
        this.colour = Color.valueOf(this.colourName);
        this.friction = friction;
        this.dim = new long[2];
        this.dim[0] = dimX;
        this.dim[1] = dimY;
        this.shape = new Rectangle(this.dim[0], this.dim[1], this.colour);
        this.balls = new LinkedList<>();
        this.pockets = new ArrayList<>();
        this.pockets.add(new Pocket(POCKET_OFFSET, POCKET_OFFSET));
        this.pockets.add(new Pocket(dimX / 2, POCKET_OFFSET));
        this.pockets.add(new Pocket(dimX - POCKET_OFFSET, POCKET_OFFSET));
        this.pockets.add(new Pocket(POCKET_OFFSET, dimY - POCKET_OFFSET));
        this.pockets.add(new Pocket(dimX / 2, dimY - POCKET_OFFSET));
        this.pockets.add(new Pocket(dimX - POCKET_OFFSET, dimY - POCKET_OFFSET));
    }

    /**
     * Get the x dimension of the table.
     * @return The dimension of the table for the x axis.
     */
    public long getDimX() {
        return this.dim[0];
    }

    /**
     * Get the y dimension of the table.
     * @return The dimension of the table for the y axis.
     */
    public long getDimY() {
        return this.dim[1];
    }

    /**
     * Get the colour of the table.
     * @return The colour of the table.
     */
    public Color getColour() {
        return this.colour;
    }

    /**
     * Get the friction of the table.
     * @return The friction value of the table.
     */
    public double getFriction() {
        return this.friction;
    }

    /**
     * Get the `Node` instance of the ball.
     * @return The node instance for the ball `Shape`.
     */
    public Node getNode() {
        return this.shape;
    }

    /**
     * Add a ball onto the pool table
     * @param ball The ball to be added
     */
    public void addBall(Ball ball) {
        if (!this.balls.contains(ball)) {
            this.balls.add(ball);
        }
    }

    /**
     * Get all balls on table.
     * @return List of ball on the table.
     */
    public List<Ball> getBalls() {
        return this.balls;
    }

    /**
     * Set up table with the list of balls, which includes the CueBall.
     * @param balls The list of balls to be added to the table
     */
    public void setupBalls(List<Ball> balls) {
        for (Ball ball : balls) {
            this.addBall(ball);
        }
    }

    /**
     * Initialising the default game tools
     * @param scr The current Score
     * @param ck The game Clock
     * @param glev The current game level
     * @param undo The Undo tool
     */
    public void init_txt(Score scr, Clock ck, GameLevel glev[], Undo undo)
    {
    	this.score = scr;
    	this.clk = ck;
    	this.glevel = glev;
    	this.udo = undo;
    	this.scre = new UpdateScoreObservable();
    	this.scre.registerScore(this.score);
    	this.scre.addObserver();
    }
    
    /**
     * Add the table and the balls to the JavaFX group so they can be drawn.
     * @param groupChildren The list of `Node` obtained from the JavaFX Group.
     */
    public void addToGroup(ObservableList<Node> groupChildren) {
        if (!(groupChildren.contains(score.getNode())))
        	groupChildren.add(score.getNode());
        
        if (!(groupChildren.contains(clk.getNode())))
        	groupChildren.add(clk.getNode());
        if (!(groupChildren.contains(this.shape)))
        	groupChildren.add(this.shape);
        if (!(groupChildren.contains(udo.getNode())))
        	groupChildren.add(udo.getNode());
        
    	for (int y = 0; y < 3; y = y + 1)
    	{
    		if (!(groupChildren.contains(glevel[y].getNode() )))
    			glevel[y].addToGroup(groupChildren);
    	}
        
        for (Pocket pocket : this.pockets) {
        	if (!(groupChildren.contains(pocket.getNode() )))
        		pocket.addToGroup(groupChildren);
        }
        for (Ball ball : this.balls) {
        	if (!(groupChildren.contains(ball.getNode() )))
        		ball.addToGroup(groupChildren);
        }
    }

    /**
     * Apply friction to all the balls
     */
    public void applyFrictionToBalls() {
        for (Ball ball : this.balls) {
            ball.applyFriction(this.getFriction());
        }
    }

    /**
     * Check if any of the balls is in a pocket and handle the ball in the 
     * pocket
     * @param game The instance of the game
     */
    public void checkPocket(Game game) {
        for (Pocket pocket : this.pockets) {
            for (Ball ball : this.balls) {
                if (ball.isDisabled()) {
                    continue;
                }
                Point2D ballCenter = new Point2D(ball.getXPos(), ball.getYPos());
                if (pocket.isInPocket(ballCenter)) {
                    ball.fallIntoPocket(game);
                    this.scre.updateScore(ball);
                }
            }
        }
    }

    /**
     * Handle the collision between the balls and table and between balls.
     */
    public void handleCollision() {
        Bounds tableBounds = this.shape.getBoundsInLocal();
        for (Ball ball : this.balls) {
            if (ball.isDisabled()) {
                continue;
            }
            Bounds ballBound = ball.getLocalBounds();
            if (!tableBounds.contains(ballBound)) {
                if (ballBound.getMaxX() >= tableBounds.getMaxX()) {
                    ball.setXVel(-ball.getXVel());
                    ball.setXPos(tableBounds.getMaxX() - ball.getRadius());
                } else if (ballBound.getMinX() <= tableBounds.getMinX()){
                    ball.setXVel(-ball.getXVel());
                    ball.setXPos(tableBounds.getMinX() + ball.getRadius());
                }
                if (ballBound.getMaxY() >= tableBounds.getMaxY()) {
                    ball.setYVel(-ball.getYVel());
                    ball.setYPos(tableBounds.getMaxY() - ball.getRadius());
                } else if (ballBound.getMinY() <= tableBounds.getMinY()) {
                    ball.setYVel(-ball.getYVel());
                    ball.setYPos(tableBounds.getMinY() + ball.getRadius());
                }
            }
            for (Ball ballB : this.balls) {
                if (ballB.isDisabled()) {
                    continue;
                }
                if (ball.isColliding(ballB)) {
                    ball.handleCollision(ballB);
                }
            }
            
        }
    }

    /**
     * If all the balls has been disabled except the cue ball, the game has ended
     * and the player won.
     * @return The win status of the game.
     */
    public boolean hasWon() {
        boolean won = true;
        for (Ball ball : this.balls) {
            if (ball.getBallType() == BallType.CUEBALL) {
                continue;
            }
            if (!ball.isDisabled()) {
                won = false;
                break;
            }
        }
        return won;
    }

    /**
     * Reset the game.
     */
    public void reset() {
    	this.scre.resetScore();
    	clk.resetTime();
        for (Ball ball : this.balls) {
            ball.reset();
        }
    }

    /**
     * Initialising the game level pool table
     * @param config The Table Configuration of the level
     * @param groupChildren The object present on the table
     */
    public void reinit(TableConfig config, ObservableList<Node> groupChildren) {

    	this.colourName = config.getColour();
        this.colour = Color.valueOf(this.colourName);
        this.friction = config.getFriction();
        this.dim[0] = config.getSizeConfig().getX();
        this.dim[1] = config.getSizeConfig().getY();
        this.shape.setWidth(this.dim[0]);
        this.shape.setHeight(this.dim[1]);
        this.shape.setFill(this.colour);
        for (Ball ball : this.balls) {
        	if (groupChildren.contains(ball.getNode())) {
        		groupChildren.remove(ball.getNode());
        	}
        }
        for (Pocket pocket : this.pockets) {
        	if (groupChildren.contains(pocket.getNode())) {
        		groupChildren.remove(groupChildren.contains(pocket.getNode()));
        	}
        }
        this.balls.removeAll(this.balls);
    }

    /**
     * Get the ball list as defined in the config.
     * @return A list of balls
     */
    public List<Pocket> getPocket() {
        return this.pockets;
    }
 
}
