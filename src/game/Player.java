package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import board.DevCard;
import board.Road;

/**
 * This class is a Player in the game Settlers of Catan
 */
public class Player {

	private final String name;
	private final Color color;
	private HashMap<String, Integer> resources;
	private ArrayList<DevCard> hand;
	private ArrayList<Road> roads;
	private int numbKnights = 0;
	private int numbSettlements = 2;
	private int victoryPoints = 2;
	private int numbRoads = 2;
	private int numbCities = 0;
	private boolean hasLargestArmy;
	private boolean[] ports = {false, false, false, false, false, false};
					// 0 = general
					// 1 = brick
					// 2 = wool
					// 3 = ore
					// 4 = grain
					// 5 = lumber


	/**
	 * Constructor takes params for assignment to fields
	 * @param n is the Player's name
	 * @param c is the Player's color in game
	 */
	public Player(String n, Color c) {

		name = n;
		color = c;
		roads = new ArrayList<Road>();

		resources = new HashMap<String, Integer>(5);
		resources.put("BRICK", 0);
		resources.put("WOOL", 0);
		resources.put("ORE", 0);
		resources.put("GRAIN", 0);
		resources.put("LUMBER", 0);

		hand = new ArrayList<DevCard>();
	}

	/**
	 * Dev testing constructor to make player w/ predefined fields
	 * @param n
	 * @param c
	 * @param brick
	 * @param wool
	 * @param ore
	 * @param grain
	 * @param lumber
	 * @param vP
	 */
	public Player(String n, Color c, int brick, int wool, int ore, int grain, int lumber, int vP) {

		this(n,c);

		setNumberResourcesType("BRICK", brick);
		setNumberResourcesType("WOOL", wool);
		setNumberResourcesType("ORE", ore);
		setNumberResourcesType("GRAIN", grain);
		setNumberResourcesType("LUMBER", lumber);
		
		victoryPoints = vP;
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
		if (str == null || str.equals("DESERT"))
			return 0;
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

	/**
	 * Adds given DevCard to this Player's hand of DevCards
	 * @param dC the DevCard to give to this Player
	 */
	public void addDevCard(DevCard dC) {
		hand.add(dC);
		if (dC.getType().equals("Victory Point")) {
			victoryPoints++;
		}
	}

//	/**
//	 * Getter for this Player's hand of DevCards
//	 * @return an ArrayList of DevCards owned by this Player
//	 */
//	public ArrayList<DevCard> getHand() {
//		return hand;
//	}

	/**
	 * Adds given road to list of owned roads
	 * @param r road added
	 */
	public void addRoad(Road r){
		roads.add(r);
	}

	/**
	 * Getter for this player's roads
	 * @return ArrayList<Road> list or owned roads
	 */
	public ArrayList<Road> getRoads(){
		return roads;
	}

	/**
	 * Method to get an ArrayList of all the resources this Player has one or more of
	 * @return an ArrayList of resources that this Player as one or more of
	 */
	public ArrayList<String> getOwnedResources() {

		ArrayList<String> res = new ArrayList<String>();
		if (resources.get("BRICK").intValue() > 0) {
			res.add("BRICK");
		}
		if (resources.get("GRAIN").intValue() > 0) {
			res.add("GRAIN");
		}
		if (resources.get("WOOL").intValue() > 0) {
			res.add("WOOL");
		}
		if (resources.get("LUMBER").intValue() > 0) {
			res.add("LUMBER");
		}
		if (resources.get("ORE").intValue() > 0) {
			res.add("ORE");
		}

		return res;
	}

	/**
	 * Increments the field indicating the number of knights played by this Player
	 */
	public void incrementNumbKnights() {
		numbKnights++;
	}

	/**
	 * Getter for the number of knights this Player has played
	 * @return the number of knights previously played by this player
	 */
	public int getNumbKnights() {
		return numbKnights;
	}

	/**
	 * Setter for whether this player has the largest army or not
	 * @param b whether this player has the largest army
	 */
	public void setHasLargestArmy(Boolean b) {
		if (hasLargestArmy == true && b == false)
			victoryPoints--;
		else if (hasLargestArmy == false && b == true)
			victoryPoints++;
		hasLargestArmy = b;
	}

	/**
	 * Getter for whether this player has the largest army or not
	 * @return whether this player has the largest army or not
	 */
	public boolean hasLargestArmy() {
		return hasLargestArmy;
	}

	/**
	 * Checks if this Player has the specified resources
	 * @param res the resources to check
	 * @return whether the Player has those resources
	 */
	public boolean hasResources(ArrayList<String> res) {
		int wool = 0,
			ore = 0,
			lumber = 0,
			brick = 0,
			grain = 0;

		for (String s : res) {
			if (res.equals("WOOL"))
				wool++;
			else if (res.equals("ORE"))
				ore++;
			else if (res.equals("LUMBER"))
				lumber++;
			else if (res.equals("BRICK"))
				brick++;
			else if (res.equals("GRAIN"))
				grain++;
		}

		if (wool > resources.get("WOOL") || ore > resources.get("ORE") || lumber > resources.get("LUMBER") || brick > resources.get("BRICK") || grain > resources.get("GRAIN"))
			return false;
		else
			return true;
	}

	/**
	 * Checks if the Player has a dev card of the given type
	 * @param dC the dev card to check
	 * @return if a card of its type is in their hand
	 */
	public boolean hasCard(String str) {

		for (DevCard dev : hand) {
			if (dev.getSubType() == str || dev.getType() == str)
				return true;
		}

		return false;
	}

	public void removeCard(String str) {
		for (DevCard dC : hand) {
			if (dC.getSubType() == str || dC.getType() == str) {
				hand.remove(dC);
				break;
			}
		}
	}

	/**
	 * Sets the corresponding port to true
	 * @param portTag 0 = general
					  1 = brick
					  2 = wool
					  3 = ore
					  4 = grain
					  5 = lumber
	 */
	public void addPort(int portTag) {
		ports[portTag] = true;
	}

	/**
	 * Getter for list of ports
	 * @return ports list of ports
	 */
	public boolean[] getPorts() {
		return ports;
	}

	/**
	 * Overridden toString method
	 * @return name
	 */
	public String toString() {
		return name;
	}

	/**
	 * Getter for numbSettlements
	 * @return int number of settlements
	 */
	public int getNumbSettlements() {
		return numbSettlements;
	}

	/**
	 * Getter for numbCities
	 * @return int number of cities
	 */
	public int getNumbCities() {
		return numbCities;
	}

	/**
	 * Getter for numbRoads
	 * @return int number of roads
	 */
	public int getNumbRoads() {
		return numbRoads;
	}
	
	/**
	 * Adds 1 to numbSettlements
	 */
	public void addSettlement() {
		numbSettlements++;
	}
	
	/**
	 * Adds 1 to numbCities
	 */
	public void upCity() {
		numbSettlements--;
		numbCities++;
	}
	
	/**
	 * Adds 1 to numbRoads
	 */
	public void addRoadCount() {
		numbRoads++;
	}
	
	/**
	 * Adds one to specified resource
	 * @param str the resource type to increment
	 */
	public void giveResourceType(String str) {
		if (str == null || str == "DESERT") {
			return;
		}
		resources.put(str, resources.get(str) + 1);
	}
		
	/**
	 * Removes all resources in given list from this player
	 * @param rez list of resources to be removed
	 */
	public void removeResources(ArrayList<String> rez) {
		for (String s: rez) {
			//System.out.println("Removed " + s);
			setNumberResourcesType(s, getNumberResourcesType(s) - 1);
		}
	}
	
	/**
	 * Adds all resources in given list to this player
	 * @param rez list of resources to be added
	 */
	public void addResources(ArrayList<String> rez) {
		for (String s: rez) {
			//System.out.println("Removed " + s);
			setNumberResourcesType(s, getNumberResourcesType(s) + 1);
		}
	}
	
	/**
	 * Gets total amount of resources this player has
	 * @return int total resources
	 */
	public int getTotalResources() {
		return getNumberResourcesType("BRICK") +
		getNumberResourcesType("WOOL") +
		getNumberResourcesType("ORE") +
		getNumberResourcesType("GRAIN") +
		getNumberResourcesType("LUMBER");
	}
	
	/**
	 * Returns number of dev cards of given type or subtype
	 * @param str the card type or subtype
	 * @return the number owned by this Player
	 */
	public int getDevCardsType(String str) {
		int count = 0;
		for (DevCard dC : hand) {
			if (dC.getType().equals(str) || dC.getSubType() != null && dC.getSubType().equals(str))
				count++;
		}
		
		return count;
	}
}
