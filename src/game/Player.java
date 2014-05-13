package game;

import java.awt.Color;
import java.util.HashMap;

/**
 * This class is a Player in the game Settlers of Catan
 */
public class Player {
	
	private final String name;
	private final Color color;
	private int victoryPoints;
	private HashMap<String, Integer> resources;
	
	
	/**
	 * Constructor takes params for assignment to fields
	 * @param n is the Player's name
	 * @param c is the Player's color in game
	 */
	public Player(String n, Color c) {
		
		name = n;
		color = c;
		victoryPoints = 0;
		
		resources = new HashMap<String, Integer>(5);
		resources.put("BRICK", 0);
		resources.put("SHEEP", 0);
		resources.put("TIMBER", 0);
		resources.put("WHEAT", 0);
		resources.put("ORE", 0);
	}
	
	/**
	 * Getter for the Player's name
	 * @return name of Player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the Player's color
	 * @return color of Player
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Getter for the number of victory points of this Player
	 * @return number of victory points
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}
	
	/**
	 * Setter for the number of victory points of this Player
	 * @param vP new number of victory points
	 */
	public void setVictoryPoints(int vP) {
		victoryPoints = vP;
	}
	
	/**
	 * Getter for this Player's quantity of given resource type
	 * @param str resource to work with
	 * @return number of resources str owned by this Player
	 */
	public int getNumberResourcesType(String str) {
		return resources.get(str).intValue();
	}
	
	/**
	 * Setter for this Player's quantity of given resource type
	 * @param str resource to work with
	 * @param n new number of resources of type str
	 */
	public void setNumberResourcesType(String str, int n) {
		resources.put(str, Integer.valueOf(n));
	}
}