package PoolGame.Config;

import org.json.simple.JSONObject;

/** A config class for the pocket configuration */
public class PocketConfig implements Configurable {
	private PositionConfig position;
	private double radius;

    /**
     * Initialise a config for table using a JSONObject
     * @param obj A JSONObject containing required keys for table
     */
    public PocketConfig(Object obj) {
        this.parseJSON(obj);
    }

    /**
     * Initialise a config for velocity using the provided values
     * @param radius The colour of the table as String
     * @param posConf The friction of the table
     */
    public PocketConfig(double radius, PositionConfig posConf) {
        this.init(radius, posConf);
    }

    private void init(double radius, PositionConfig posConf) {
    	if (radius <= 0) {
            throw new IllegalArgumentException("Mass of ball must be greater than 0");
        }
        this.radius = radius;
        this.position = posConf;

    }

    public Configurable parseJSON(Object obj) {
    	JSONObject json = (JSONObject) obj;
    	double radius = (double)json.get("radius");
        PositionConfig posConf = new PositionConfig(json.get("position"));
        this.init(radius, posConf);
        return null;
    }

    /**
     * Get the colour of the table as defined in the config.
     * @return The colour of the table
     */
    public double getradius() {
        return this.radius;
    }

    /**
     * Get the position config instance as defined in the config.
     * @return The position config instance
     */
    public PositionConfig getPositionConfig() {
        return this.position;
    }
}