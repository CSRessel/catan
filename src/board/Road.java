package board;

import game.Player;

/**
 * This class models a road from Settlers of Catan
 */
public class Road {

	private Player owner;
	private EdgeLocation location;


	/**
	 * Constructor for the Road
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param o the orienation
	 */
	public Road(int x, int y, int o) {
		location = new EdgeLocation(x, y, o);
	}

	/**
	 * Setter for the Road's owner (only settable if no current owner; i.e. only settable once)
	 * @param p the new owner
	 */
	public void setOwner(Player p) {
		if (null == owner)
			owner = p;
	}

	/**
	 * Getter for the Road's owner
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Getter for the Road's location
	 * @return the location
	 */
	public EdgeLocation getLocation() {
		return location;
	}
}
