package PoolGame.Config;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

/** A config class for the pocket configuration */
public class PocketsConfig  implements Configurable {
    private List<PocketConfig> pocket;

    /**
     * Initialise the instance with the provided JSONArray of ball
     * @param obj A JSONArray containing all the ball configuration
     */
    public PocketsConfig(Object obj) {
        this.parseJSON(obj);
    }

    /**
     * Initialise the instance with the provided ball configs
     * @param pocketList A list of ball configs
     */
    public PocketsConfig(List<PocketConfig> pocketList) {
        this.init(pocketList);
    }
    
    private void init(List<PocketConfig> pocketList) {
        this.pocket = pocketList;
    }

    public Configurable parseJSON(Object obj) {
        List<PocketConfig> list = new ArrayList<>();
        JSONArray json = (JSONArray) obj;
        for (Object b : json) {
            list.add(new PocketConfig(b));
        }
        this.init(list);
        return this;
    }

    /**
     * Get the ball list as defined in the config.
     * @return A list of balls
     */
    public List<PocketConfig> getPocketConfigs() {
        return this.pocket;
    }
}