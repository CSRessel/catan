package board;

/**
 * This class models the location of a settlement or city (hence, vertex) from Settlers of Catan
 */
public class VertexLocation extends Location {

	private int orientation; // 0 = top, 1 = bottom
	

	/**
	 * Constructor for the VertexLocation
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param o the orientation
	 */
	public VertexLocation(int x, int y, int o) {
		super(x, y);
		orientation = o;
	}
	
	/**
	 * Getter for the VertexLocation's orientation
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}
}