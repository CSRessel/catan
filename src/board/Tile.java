package board;

import java.util.ArrayList;


/**
 * Tile represents a single tile
 */
public class Tile {
	
	private int xCoord,
				yCoord,
				number;
	private boolean hasRobber = false;
	private final String type;
		/*
		 * Possible values:
		 * DESERT, BRICK, SHEEP, TIMBER, WHEAT, ORE
		 */
	
	
	/**
	 * Constructor, with given params for its fields
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param n the number of the Tile
	 * @param str the resource type of the Tile
	 */
	public Tile(int x, int y, int n, String str) {
		xCoord = x;
		yCoord = y;
		number = n;
		type = str;
	}
	
	/**
	 * Constructor, only initializing type
	 * @param str the resource type
	 */
	public Tile(String str) {
		type = str;
	}
	
	/**
	 * Constructor, only initializing type and hasRobber
	 * @param str the resource type
	 * @param boolean whether tile has robber
	 */
	public Tile(String str, boolean b) {
		type = str;
		hasRobber = b;
	}
	
	/**
	 * Modifier for Tile's coordinates
	 * @param col the x coordinate
	 * @param row the y coordinate
	 */
	public void setCoords(int col, int row) {
		xCoord = col;
		yCoord = row;
	}
	
	/**
	 * Modifier for Tile's number
	 * @param n the tile's number
	 */
	public void setNumber(int n) {
		number = n;
	}
	/**
	 * Accessor for the Tile's number
	 * @return number the tile's number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Calls each settlement to give the appropriate resource to its owner
	 */
	public void giveResources() {
		
	}
	
	/**
	 * Finds all settlements next to this tile
	 * @return ArrayList of adjacent Settlements
	 */
	private ArrayList<Settlement> findAdjacentSettlements() {
		ArrayList<Settlement> settlements = new ArrayList<Settlement>();
		
		//TODO
		
		return settlements;
	}
	
	/**
	 * Returns this tile's resource type
	 * @return String of resource type this tile is
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Accessor for the boolean field determining whether class has Robber or not
	 * @return boolean of whether Tile has Robber or not
	 */
	public boolean hasRobber() {
		return hasRobber;
	}
	
	/**
	 * Setter for the boolean field determining whether class has Robber or not
	 * @param b whether Tile has Robber or not
	 */
	public void setRobber(boolean b) {
		hasRobber = b;
	}
}
