package board;


/**
 * This class models a settlement in Settlers of Catan
 */
public class Settlement extends Structure {

	/**
	 * Constructor with three values to be used for the Settlement's location
	 * @param x the x coordinate of the location
	 * @param y the y coordinate of the location
	 * @param o the orientation of the location
	 */
	public Settlement(int x, int y, int o) {
		setLocation(new VertexLocation(x, y, o));
	}
	
	/**
	 * Gives one resource of type resType to the owner of the Settlement
	 * @param resType the type of resource to be given
	 */
	public void giveResources(String resType) {
		// Increment int associated with String resType in owner's hashmap of resources by one
		getOwner().setNumberResourcesType(resType, getOwner().getNumberResourcesType(resType) + 1);
	}

	/**
	 * Creates and returns a new City with the same fields to replace this settlement
	 * @return a new City with the same fields
	 */
	public City upgrade() {
		City upgraded = new City(getLocation());
		upgraded.setOwner(getOwner());
		
		return upgraded;
	}
}