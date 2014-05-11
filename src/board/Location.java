package board;


/**
 * This class models the location of a Tile on the board
 */
public class Location {

	private int xCoord;
	private int yCoord;
	
	
	/**
	 * Constructor for the location
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Location(int x, int y) {
		xCoord = x;
		yCoord = y;
	}
	
	/**
	 * Getter for the Location's x coordinate
	 * @return the x coordinate
	 */
	public int getXCoord() {
		return xCoord;
	}
	
	/**
	 * Getter for the Location's y coordinate
	 * @return the y coordinate
	 */
	public int getYCoord() {
		return yCoord;
	}
}