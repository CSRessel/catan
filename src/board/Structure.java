package board;

import game.Player;


/**
 * <code>Structure</code> is an interface for the classes <code>Settlement</code> and <code>City</code>
 */
public interface Structure {
	
	/**
	 * Gives resources to the player in the <code>owner</code> field of the <code>Structure</code>
	 */
	void giveResources();
	
	/**
	 * Set's the final field <code>owner</code> to the given <code>Player</code>
	 * @param p the <code>Player</code> to set the field to
	 */
	void setPlayer(Player p);
	
	/**
	 * Accessor for the <code>Structure</code>'s <code>Player</code> field
	 * @return the class's <code>Player</code>
	 */
	Player getPlayer();
	
}