package board;


/**
 * This class models a city from Settlers of Catan
 */
public class City extends Structure {

	/**
	 * Constructor with the desired location of the City as a param
	 * @param loc the desire location of the City
	 */
	public City(VertexLocation loc) {
		setLocation(loc);
		setType(1);
	}

	/**
	 * Gives two resources of type resType to the owner of the Settlement
	 * @param resType the type of resources to be given
	 */
	public void giveResources(String resType) {
		// Increment int associated with String resType in owner's hashmap of resources by two
		getOwner().setNumberResourcesType(resType, getOwner().getNumberResourcesType(resType) + 2);
	}
}