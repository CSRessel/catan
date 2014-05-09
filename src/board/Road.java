package board;

import game.Player;


/**
 * The <code>Road</code> class models a road from Settlers of Catan
 */
public class Road {

	private Player owner;
	private int xCoord,
				yCoord,
				orientation;
	

	/**
	 * Constructor takes three params for its three int fields
	 * @param x gets set to xCoord
	 * @param y gets set to yCoord
	 * @param o gets set to orientation;
	 */
	public Road(int x, int y, int o) {
		xCoord = x;
		yCoord = y;
		orientation = o;
	}

	/**
	 * Set's the field <code>owner</code> to the given <code>Player</code>
	 * @param p the <code>Player</code> to set the field to
	 */
	public void setPlayer(Player p) {
		if (null == owner)
			owner = p;
	}
	
	/**
	 * Accessor for the <code>Road</code>'s <code>Player</code> field
	 * @return the class's <code>Player</code>
	 */
	public Player getPlayer() {
		return owner;
	}
}
