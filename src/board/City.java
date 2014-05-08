
package board;

import game.Player;

public class City implements Structure {

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
	public City(int x, int y, int o) {
		xCoord = x;
		yCoord = y;
		orientation = o;
	}
	
	/**
	 * Gives 2 resources to the player in the <code>owner</code> field of the <code>City</code>
	 */
	public void giveResources() {
		//TODO
	}

	/**
	 * Set's the final field <code>owner</code> to the given <code>Player</code>
	 * @param p the <code>Player</code> to set the field to
	 */
	public void setPlayer(Player p) {
		if (null == owner)
			owner = p;
	}

	/**
	 * Accessor for the <code>City</code>'s <code>Player</code> field
	 * @return the class's <code>Player</code>
	 */
	public Player getPlayer() {
		return owner;
	}

}