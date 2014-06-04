package game;

import java.util.ArrayList;
import java.util.Collections;
import board.*;


/**
 * The main game class of Settlers of Catan
 */
public class Game {

	private Board board;
	private ArrayList<Player> players;
	private Deck deck;

	/**
	 * Constructor for game, creates the Board.
	 * @param givenPlayers the players of the game
	 */
	public Game(ArrayList<Player> givenPlayers) {

		if (givenPlayers.size() < 3 || givenPlayers.size() > 4)
			throw new IllegalArgumentException("Game must be played with three or four players");

		ArrayList<String> names = new ArrayList<String>();
		for (Player p : givenPlayers) {
			names.add(p.getName());
		}
		for (String s : names) {
			if (Collections.frequency(names, s) > 1)
				throw new IllegalArgumentException("Players must have different names");
		}

		Collections.shuffle(givenPlayers);
		
		players = givenPlayers;
		board = new Board();
		deck = new Deck();
		
		GameRunner.setFirstPlayer();
	}

	/**
	 * Checks if one player has ten or more victory points and more points than any other player
	 * @return whether anyone has one yet
	 */
	public boolean over() {
		return winningPlayer() != null;
	}

	/**
	 * Returns the player that has won, or null if game is not finished
	 * @return winning player
	 */
	public Player winningPlayer() {

		Player maxVictoryPoints = players.get(0);
		Player secondMaxVictoryPoints = players.get(0);

		for (Player p : players) {

			if (p.getVictoryPoints() > maxVictoryPoints.getVictoryPoints()) {
				maxVictoryPoints = p;
			}
			else if (p.getVictoryPoints() > secondMaxVictoryPoints.getVictoryPoints()) {
				secondMaxVictoryPoints = p;
			}
		}

		if (maxVictoryPoints.getVictoryPoints() >= 10 && maxVictoryPoints.getVictoryPoints() > secondMaxVictoryPoints.getVictoryPoints()) {
			return maxVictoryPoints;
		}
		else
			return null;
	}

	/**
	 * Rolls the die and allocates resources to players
	 * @param p the Player rolling (in case Player wants to play dev card first)
	 * @return int the roll
	 */
	public int roll(Player p) {

		// RTD
		int roll = (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6 + 1);

		if (roll == 7) {
			return roll;
		}
		else {
			// Distribute resources
			board.distributeResources(roll);

			return roll;
		}
	}

	/**
	 * Allows the given Player to move the Robber to the given Location
	 * @param p the Player who moved the Robber
	 * @return if the chosen Location is valid and the move succeeded
	 */
	public boolean moveRobber(Player p, Location loc) {

		Location prev = board.getRobberLocation();

		if (loc.equals(prev)) {
			return false;
		}

		board.setRobberLocation(loc);
		board.getTile(loc).setRobber(true);
		board.getTile(prev).setRobber(false);

		return true;
	}

	/**
	 * Allows the given Player to take a card from any Player with a Settlement on the Tile of the given Location
	 * @param p the Player taking a card
	 * @param loc the Location of the Tile
	 */
	public void takeCard(Player p, Player choice) {

		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < choice.getNumberResourcesType("BRICK"); i++) {
			res.add("BRICK");
		}
		for (int i = 0; i < choice.getNumberResourcesType("WOOL"); i++) {
			res.add("WOOL");
		}
		for (int i = 0; i < choice.getNumberResourcesType("ORE"); i++) {
			res.add("ORE");
		}
		for (int i = 0; i < choice.getNumberResourcesType("GRAIN"); i++) {
			res.add("GRAIN");
		}
		for (int i = 0; i < choice.getNumberResourcesType("LUMBER"); i++) {
			res.add("LUMBER");
		}
		
		Collections.shuffle(res);

		if (res.size() <= 0) {
			return;
		}
		String result = res.get(0);

		choice.setNumberResourcesType(result, choice.getNumberResourcesType(result) - 1);

		p.setNumberResourcesType(result, p.getNumberResourcesType(result) + 1);
	}

	/**
	 * If any Player has more than seven cards, they choose half their cards (rounded down) to return to the bank
	 */
	public void halfCards() {

		for (Player p : players) {

			int cap = 7;
			int numbCards = p.getNumberResourcesType("BRICK") +
							p.getNumberResourcesType("WOOL") +
							p.getNumberResourcesType("ORE") +
							p.getNumberResourcesType("GRAIN") +
							p.getNumberResourcesType("LUMBER");
			int currentCards = numbCards;

			boolean done = false;

			do {
				currentCards = p.getNumberResourcesType("BRICK") +
							   p.getNumberResourcesType("WOOL") +
							   p.getNumberResourcesType("ORE") +
							   p.getNumberResourcesType("GRAIN") +
							   p.getNumberResourcesType("LUMBER");

				if (currentCards > cap) {
					int input = 0; //TODO: input
						/* Possible Values:
						 * 0 - LUMBER
						 * 1 - BRICK
						 * 2 - WOOL
						 * 3 - GRAIN
						 * 4 - ORE
						 */
					cap = numbCards / 2;

					switch (input) {
					case 0:
						p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
						break;
					case 1:
						p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
						break;
					case 2:
						p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
						break;
					case 3:
						p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
						break;
					case 4:
						p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
						break;
					}
				}
				else {
					done = true;
				}
			} while (!done);
		}
	}
	
	/**
	 * Causes the given player to take all the specified resource from all other players (for monopoly cards)
	 * @param res the resource to take
	 * @param p the player receiving all of that res
	 */
	public void takeAll(String res, Player p) {
		
		ArrayList<Player> plays = new ArrayList<Player>(players);
		plays.remove(p);
		
		for (Player player : plays) {
			int tmp = player.getNumberResourcesType(res);
			
			player.setNumberResourcesType(res, 0);
			p.setNumberResourcesType(res, p.getNumberResourcesType(res) + tmp);
		}
	}

	/**
	 * Operates trade between two players with the given resoures
	 * @param a the first Player in the trading
	 * @param b the second Player in the trading
	 * @param fromA the resources being traded from Player a to Player b
	 * @param fromB the resources being traded from Player b to Player a
	 * @return boolean whether the trade was possible
	 */
	public boolean playerTrade(Player a, Player b, ArrayList<String> fromA, ArrayList<String> fromB) {

		if (!a.hasResources(fromA) ||  !b.hasResources(fromB)) {
			return false;
		}

		for (String res : fromA) {
			a.setNumberResourcesType(res, a.getNumberResourcesType(res) - 1);
			b.setNumberResourcesType(res, b.getNumberResourcesType(res) + 1);
		}

		for (String res : fromB) {
			b.setNumberResourcesType(res, b.getNumberResourcesType(res) - 1);
			a.setNumberResourcesType(res, a.getNumberResourcesType(res) + 1);
		}

		return true;
	}

	/**
	 * Operates trading between given Player and the stock
	 * @param a the Player trading
	 * @param fromA what they are giving up
	 * @param toA what they are asking for
	 * @return int 0 = success; 1 = not enough resources; 2 = invalid ratio
	 * 
	 */
	public int npcTrade(Player a, String resourceBuying, ArrayList<String> fromA) {
		if (!a.hasResources(fromA))
			return 1;

		//TODO: check if this npc trade is valid (valid ratios, harbors, etc)
		boolean[] ports = a.getPorts();
		ArrayList<Integer> resources = new ArrayList<Integer>();
		resources.add(Collections.frequency(fromA,"BRICK"));
		resources.add(Collections.frequency(fromA,"WOOL"));
		resources.add(Collections.frequency(fromA,"ORE"));
		resources.add(Collections.frequency(fromA,"GRAIN"));
		resources.add(Collections.frequency(fromA,"LUMBER"));
		
		//int nBrick = Collections.frequency(fromA,"BRICK");
		//int toBrick = Collections.frequency(toA,"BRICK");
		//int nWool = Collections.frequency(fromA,"WOOL");
		//int toWool = Collections.frequency(toA,"WOOL");
		//int nOre = Collections.frequency(fromA,"ORE");
		//int toOre = Collections.frequency(toA,"ORE");
		//int nGrain = Collections.frequency(fromA,"GRAIN");
		//int toGrain = Collections.frequency(toA,"GRAIN");
		//int nLumber = Collections.frequency(fromA,"LUMBER");
		//int toLumber = Collections.frequency(toA,"LUMBER");
		ArrayList<String> toA = new ArrayList<String>();

		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i) == 0) {}
			else {
				if (ports[i + 1]) {
					if (resources.get(i) % 2 == 0) {
						for (int k = 0; k < resources.get(i) / 2; k++) {
							toA.add(resourceBuying);
						}
					}
					else {
						return 2;
					}
				}
				else if (ports[0]) {
					if (resources.get(i) % 3 == 0) {
						for (int k = 0; k < resources.get(i) / 3; k++) {
							toA.add(resourceBuying);
						}
					}
					else {
						return 2;
					}
				}
				else {
					if (resources.get(i) % 4 == 0) {
						for (int k = 0; k < resources.get(i) / 4; k++) {
							toA.add(resourceBuying);
						}
					}
					else {
						return 2;
					}
				}
			}
		}
		
		
		for (String res : fromA) {
			a.setNumberResourcesType(res, a.getNumberResourcesType(res) - 1);
		}
		
		//ArrayList<String> toA = new ArrayList<String>();
		//toA.add(resourceBuying);
		for (String res : toA) {
			a.setNumberResourcesType(res, a.getNumberResourcesType(res) + 1);
		}

		return 0;
	}

	/**
	 * Buys Road for given Player
	 * @param p the given Player
	 * @return 0=success, 1=insufficient resources, 2=structure limit reached
	 */
	public int buyRoad(Player p) {

		if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
			return 1;
		}

		// Check Player has not exceeded capacity for object
		if (p.getNumbRoads() >= 15) {
			return 2;
		}

		p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
		p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);

		//p.setVictoryPoints(p.getVictoryPoints() + 1);  TODO road victory points

		p.addRoadCount();
		return 0;
	}

	/**
	 * Buys Settlement for given Player
	 * @param p the given Player
	 * @return 0=success, 1=insufficient resources, 2=structure limit reached
	 */
	public int buySettlement(Player p) {

		// Check Player has sufficient resources
		if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("GRAIN") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
			return 1;
		}

		// Check Player has not exceeded capacity for object
		if (p.getNumbSettlements() >= 5) {
			return 2;
		}

		p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
		p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
		p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
		p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);

		p.setVictoryPoints(p.getVictoryPoints() + 1);
		
		p.addSettlement();
		return 0;
	}

	/**
	 * Buys City for given Player
	 * @param p the given Player
	 * @return 0=success, 1=insufficient resources, 2=structure limit reached
	 */
	public int buyCity(Player p) {

		// Check Player has sufficient resources
		if (p.getNumberResourcesType("GRAIN") < 2 || p.getNumberResourcesType("ORE") < 3) {
			return 1;
		}

		// Check Player has not exceeded capacity for object
		if (p.getNumbCities() >= 4) {
			return 2;
		}

		p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 2);
		p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 3);

		p.setVictoryPoints(p.getVictoryPoints() + 1);

		p.upCity();
		return 0;
	}

	/**
	 * Buys DevCard for given Player
	 * @param p the given Player
	 * @return 0=success, 1=insufficient resources, 2=no devcards left in deck
	 */
	public int buyDevCard(Player p) {
		
		// Check Player has sufficient resources
		if (p.getNumberResourcesType("ORE") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("GRAIN") < 1) {
			return 1;
		}
		
		p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
		p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
		p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
		
		if (deck.isEmpty()) {
			return 2;
		}
				
		return 0;
	}

	/**
	 * Places a Road for the given Player at the given EdgeLocation
	 * @param p the Player placing
	 * @param loc the EdgeLocation to place the ROad
	 * @return whether the Road can go there
	 */
	public boolean placeRoad(Player p, EdgeLocation loc) {
		return board.placeRoad(loc, p);
	}

	/**
	 * Places a Settlement for the given Player at the given VertexLocation
	 * @param p the Player placing
	 * @param loc the VertexLocation to place the Settlement
	 * @return whether the Settlement can go there
	 */
	public boolean placeSettlement(Player p, VertexLocation loc) {
		return board.placeStructure(loc, p);
	}

	/**
	 * Places a City for the given Player at the given VertexLocation
	 * @param p the Player placing
	 * @param loc the VertexLocation to place the City
	 * @return whether the City can go there
	 */
	public boolean placeCity(Player p, VertexLocation loc) {

		Structure s = board.getStructure(loc);

		if (!s.getOwner().equals(p)) {
			return false;
			//TODO: throw error about unowned settlement
		}

		Structure c = new City(s.getLocation());
		c.setOwner(s.getOwner());
		board.setStructure(loc, c);

		return true;
	}

	/**
	 * Getter for board's tiles
	 * @return tile array
	 */
	public Board getBoard(){
		return board;
	}
	
	/**
	 * Getter for the deck
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}
}
