package PoolGame.Config;

import org.json.simple.JSONObject;

/** A config class for the game configuration */
public class GameConfig implements Configurable {
    private TableConfig table;
    private BallsConfig balls;
    private PocketsConfig pockets;

    /**
     * Initialise the game config with the provided value
     * @param table An instance of the table config
     * @param balls An instance of the balls config containing all the balls
     */
    public GameConfig(TableConfig table, BallsConfig balls) {
        this.init(table, balls);
    }
    
    private void init(TableConfig table, BallsConfig balls) {
        this.table = table;
        this.balls = balls;
    }
    /*
     * reload new data
     */
    
    public GameConfig(TableConfig table, PocketsConfig pock, BallsConfig balls) {
        this.init(table,pock, balls);
    }
    
    private void init(TableConfig table, PocketsConfig pock, BallsConfig balls) {
        this.table = table;
        this.balls = balls;
        this.pockets = pock;
    }
    
    public Configurable parseJSON(Object obj) {
        JSONObject json = (JSONObject) obj;
        this.table = new TableConfig(json.get("Table"));
        this.pockets = new PocketsConfig(json.get("Pockets"));
        this.balls = new BallsConfig(json.get("Balls"));
        this.init(table, pockets, balls);
        return this;
    }

    /**
     * Get the table config instance as defined in the config
     * @return The table config instance
     */
    public TableConfig getTableConfig() {
        return this.table;
    }

    /**
     * Get the balls config instance as defined in the config
     * @return The balls config instance
     */
    public BallsConfig getBallsConfig() {
        return this.balls;
    }
    /**
     * Get the Pocket config instance as defined in the config
     * @return The Pocket config instance
     */
    public PocketsConfig getPocketsConfig() {
        return this.pockets;
    }
}
