package board;


/**
 * This class models the location of a road from Settlers of Catan
 */
public class EdgeLocation extends Location {

	private int orientation; // 0 = top left, 1 = top right, 2 = right
	
	
	/**
	 * Constructor for the EdgeLocation
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param o the orientation
	 */
	public EdgeLocation(int x, int y, int o) {
		super(x, y);
		orientation = o;
	}
	
	/**
	 * Getter for the EdgeLocation's orientation
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}
}
