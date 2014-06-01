package board;

import java.util.ArrayList;


/**
 * Tile represents a single tile in a hexagonal grid
 */
public class Tile {
	
	private int number = 0;
	private Location location;
	private boolean hasRobber = false;
	private final String type;
		/*
		 * Possible values:
		 * DESERT, BRICK, WOOL, LUMBER, GRAIN, ORE
		 */
	
	
	/**
	 * Constructor, with given params for its fields
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param n the number of the Tile
	 * @param str the resource type of the Tile
	 */
	public Tile(int x, int y, int n, String str) {
		location = new Location(x, y);
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
	 * Getter for the Tile's location
	 * @return the Tile's location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Setter for Tile's location
	 * @param col the x coordinate
	 * @param row the y coordinate
	 */
	public void setCoords(int col, int row) {
		location = new Location(col, row);
	}
	
	/**
	 * Setter for Tile's number
	 * @param n the tile's number
	 */
	public void setNumber(int n) {
		number = n;
	}
	/**
	 * Getter for the Tile's number
	 * @return number the tile's number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Getter for this tile's resource type
	 * @return String resource type this tile is
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Getter for boolean field determining whether class has Robber or not
	 * @return boolean whether Tile has Robber or not
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
