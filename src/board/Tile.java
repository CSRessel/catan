package board;

/**
 * <code>Tile</code> represents a single tile.
 */
public class Tile {
	private int xCoord,
				yCoord,
				number;
	private boolean hasRobber;
	private enum Resource {
		DESERT, BRICK, SHEEP, TIMBER, WHEAT, ORE
	}
	
/**
 * Calls each settlement to give the appropriate resource to its owner. 
 */
	public void giveResources(){
		
	}
	
/**
 * Finds all settlements next to this tile.
 * @return ArrayList of adjacent <code>Settlement</code>s.
 */
	public ArrayList<Settlement> findAdjacentSettlements(){
		
	}
	
/**
 * Returns this tile's resource type.
 * @return Resource type this tile is.
 */
	public Resource getType(){
		
	}
	
	
	
	
}
