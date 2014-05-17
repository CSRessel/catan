package board;

import java.util.ArrayList;

import game.Player;


/**
 * This is a superclass for Settlement and City
 */
public abstract class Structure {
	
	private Player owner = null;
	private VertexLocation location;
	private int type;
		// Either 0 (Settlement), or 1 (City)
	
	/**
	 * This method will give resources of type resType to the owner of the Structure
	 * @param resType the type of resource to be given to the owner
	 */
	public abstract void giveResources(String resType);

	/**
	 * Setter for the Structure's owner
	 * Can only be set if Structure is unowned (only settable once)
	 * @param p the new owner of the Structure
	 */
	public void setOwner(Player p) {
		if (null == owner)
			owner = p;
	}

	/**
	 * Getter for the Structure's owner
	 * @return the Structure's owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**
	 * Setter for the Structure's location
	 * @param loc the new value for the Structure's location
	 */
	public void setLocation(VertexLocation loc) {
		location = loc;
	}

	/**
	 * Getter for the Structure's location
	 * @return the Structure's location
	 */
	public VertexLocation getLocation() {
		return location;
	}
	
	/**
	 * Getter for the Structure'ss type
	 * @return the Structure's type
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Setter for the Structure's type
	 * @param t the new type of the Structure
	 */
	public void setType(int t) {
		type = t;
	}
	
}