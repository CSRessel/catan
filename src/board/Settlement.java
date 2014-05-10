package board;

import game.Player;


/**
 * The <code>Settlement</code> class models a settlement from Settlers of Catan
 */
public class Settlement implements Structure {

	private Player owner;
	private int xCoord,
				yCoord,
				orientation; //0 = top, 1 = bottom
	
	
	/**
	 * Constructor takes three params for its three int fields
	 * @param x gets set to xCoord
	 * @param y gets set to yCoord
	 * @param o gets set to orientation;
	 */
	public Settlement(int x, int y, int o) {
		xCoord = x;
		yCoord = y;
		orientation = o;
	}
	
	/**
	 * Gives 1 resource to the player in the <code>owner</code> field of the <code>Settlement</code>
	 */
	public void giveResources() {
		//TODO
	}
	
	/**
	 * Replaces this <code>Settlement</code> with a <code>City</code>
	 */
	public City upgrade() {
		City upgraded = new City (xCoord, yCoord, orientation);
		upgraded.setPlayer(owner);
		
		return upgraded;
	}
	
	/**
	 * Set's the final field <code>owner</code> to the given <code>Player</code>
	 * @param p the <code>Player</code> to set the field to
	 */
	public void setPlayer(Player p) {
		if (null == owner) {
			owner = p;
		}
	}
	
	/**
	 * Accessor for the <code>Settlement</code>'s <code>Player</code> field
	 * @return the class's <code>Player</code>
	 */
	public Player getPlayer() {
		return owner;
	}
}