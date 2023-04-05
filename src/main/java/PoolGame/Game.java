package PoolGame;

import java.util.ArrayList;
import java.util.List;

import PoolGame.Builder.BallBuilderDirector;
import PoolGame.Config.BallConfig;
import PoolGame.Config.PocketConfig;
import PoolGame.Facade.GameLevel;
import PoolGame.Items.Ball;
import PoolGame.Items.Pocket;
import PoolGame.Items.PoolTable;
import PoolGame.Memento.Undo;
import PoolGame.Release.CheatBall;
import PoolGame.Release.UpdateScoreObservable;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;
import PoolGame.Items.*;

/** The game class that runs the game */
public class Game {
    private PoolTable table;
    private Clock clock;
    private Undo undo;
    private CheatBall chb;
    private static Score score;
    private GameLevel glevel[];
    private boolean shownWonText = false;
    private ObservableList<Node> gc;
    private final Text winText = new Text(50, 50, "Win and Bye");
    private UpdateScoreObservable scre;

    /**
     * Initialise the game with the provided config
     * @param config The config parser to load the config from
     */
    public Game(ConfigReader config) {
    	glevel = new GameLevel[3];
    	this.setup(config);
    	scre = new UpdateScoreObservable();
    }

    private void setup(ConfigReader config) {
        this.table = new PoolTable(config.getConfig().getTableConfig());
        List<BallConfig> ballsConf = config.getConfig().getBallsConfig().getBallConfigs();
        List<Ball> balls = new ArrayList<>();
        BallBuilderDirector builder = new BallBuilderDirector();
        builder.registerDefault();
        /*
         * added Time and Score
         */

        this.clock = new Clock(config.getConfig().getTableConfig());
        score = score.getInstance();
        score.updatePosition(config.getConfig().getTableConfig());
        this.undo =  new Undo(config.getConfig().getTableConfig(),this.clock, balls);
        chb = new CheatBall();
        for (int y = 0 ;y < 3; y = y +  1)
        {
        	this.glevel[y] = new GameLevel(config.getConfig().getTableConfig(),y, config);
        	glevel[y].setGame(this);
        }

        this.table.init_txt(score, clock, glevel, this.undo);
        
        for (BallConfig ballConf: ballsConf) {
            Ball ball = builder.construct(ballConf);
            if (ball == null) {
                System.err.println("WARNING: Unknown ball, skipping...");
            } else {
            	ball.registerUndoOption(this.undo);
                balls.add(ball);
            }
        }
        chb.updateBallReference(balls);
        this.table.setupBalls(balls);
        this.winText.setVisible(false);
        this.winText.setX(table.getDimX() / 2);
        this.winText.setY(table.getDimY() / 2);
    }

    /**
     * Get the window dimension in the x-axis
     * @return The x-axis size of the window dimension
     */
    public double getWindowDimX() {
        return this.table.getDimX();
    }

    /**
     * Get the window dimension in the y-axis
     * @return The y-axis size of the window dimension
     */
    public double getWindowDimY() {
        return this.table.getDimY();
    }

    /**
     * Get the pool table associated with the game
     * @return The pool table instance of the game
     */
    public PoolTable getPoolTable() {
        return this.table;
    }

    /** Add all drawable object to the JavaFX group
     * @param root The JavaFX `Group` instance
    */
    public void addDrawables(Group root) {
        ObservableList<Node> groupChildren = root.getChildren();
        table.addToGroup(groupChildren);
        groupChildren.add(this.winText);
        gc = groupChildren;
        
        root.setOnKeyPressed(e -> {
        	chb.cheatUpdate(e.getCode());
        });
        
    }

    /** Reset the game */
    public void reset() {
        this.winText.setVisible(false);
        this.shownWonText = false;
        this.table.reset();
        scre.resetScore();
    }
   
    /** Code to execute every tick. */
    public void tick() {
        if (table.hasWon() && !this.shownWonText) {
            System.out.println(this.winText.getText());
            this.winText.setVisible(true);
            this.shownWonText = true;
        }
        table.checkPocket(this);
        table.handleCollision();
        /*
         * add clock andscore
         */
        clock.updateTime();
        this.table.applyFrictionToBalls();
        for (Ball ball : this.table.getBalls()) {
            ball.move();
        }
    }

    /**
     * Setup the game Table through the level Configuration
     * @param config The configuration file
     */
    public void new_setup(ConfigReader config) {
    	
        this.table.reinit(config.getConfig().getTableConfig(), gc);
        List<BallConfig> ballsConf = config.getConfig().getBallsConfig().getBallConfigs();
        List<Ball> balls = new ArrayList<>();
        BallBuilderDirector builder = new BallBuilderDirector();
        builder.registerDefault();

        for (BallConfig ballConf: ballsConf) {
            Ball ball = builder.construct(ballConf);
            if (ball == null) {
                System.err.println("WARNING: Unknown ball, skipping...");
            } else {
                balls.add(ball);
            }
        }
        this.undo.returnmemto().update_ball_ref(balls);
        chb.updateBallReference(balls);
        List<PocketConfig> pocketsConf = config.getConfig().getPocketsConfig().getPocketConfigs();
        List<Pocket> pock = this.table.getPocket();
        int i = 0;
        for (PocketConfig pocketConf: pocketsConf) {
        	pock.get(i).updatePocketPoc(pocketConf.getPositionConfig().getX(), pocketConf.getPositionConfig().getY());
        	i = i + 1;
        }
        this.table.setupBalls(balls);
        table.addToGroup(gc);
    }
}
