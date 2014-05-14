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
		
		Collections.shuffle(givenPlayers);
		
		players = givenPlayers;
		board = new Board();
		deck = new Deck();
	}
	
	/**
	 * Method to start the game
	 */
	public void start() {
		setup();
		play();
	}
	
	/**
	 * Runs the setup phase of the game
	 */
	private void setup() {
		
		for (int i = 0; i <= 1; i++) {
			for (Player p : players) {
				//TODO: ask to place settlement and road
			}
		}
		
		Collections.reverse(players);
	}
	
	/**
	 * Loops through the players, executing each phase of their turn
	 * Ends when one player has ten or more victory points and more points than any other player
	 */
	private void play() {
		
		boolean gameOver = false;
		
		while (!gameOver) {
			
			// Check if the game is over
			
			int maxVictoryPoints = 0;
			int secondMaxVictoryPoints = 0;
			
			for (Player p : players) {
				int victoryPoints = p.getVictoryPoints();
				
				if (victoryPoints > maxVictoryPoints) {
					maxVictoryPoints = victoryPoints;
				}
				else if (victoryPoints > secondMaxVictoryPoints) {
					secondMaxVictoryPoints = victoryPoints;
				}
			}
			
			if (maxVictoryPoints >= 10 && maxVictoryPoints > secondMaxVictoryPoints) {
				gameOver = true;
			}
			
			
			// For each player execute the phases of their turn (roll, trade, build)
			
			for (Player p : players) {
				roll(p);
				tradeAndBuild(p);
			}
		}
		
	}
	
	/**
	 * Rolls the die and allocates resources to players
	 * @param p the Player rolling (in case Player wants to play dev card first)
	 */
	private void roll(Player p) {
		
		int input = 1;
			/* Possible values:
			 * 0 - play dev card
			 * 1 - continue
			 */
		do {
			input = 1; //TODO: input here
			if (input == 0) {
				playDevCard(p);
			}
		} while (input != 1);
		
		// RTD
		int roll = (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6 + 1);
		
		// Distribute resources
		board.distributeResources(roll);
	}
	
	/**
	 * Allows the provided Player to play a dev card
	 * @param p
	 */
	private void playDevCard(Player p) {
		
		ArrayList<DevCard> cards = p.getHand();
		
		int input = 0; //TODO: input
		
		if (input >= 0 && input < cards.size()) {
			DevCard dC = cards.remove(input);
			//TODO: dev card functionality here
		}
		else {
			//TODO: throw error about invalid dev card selection
		}
	}
	
	/**
	 * Goes through the build phase for Player p
	 * @param p the Player doing the building
	 */
	private void tradeAndBuild(Player p) {
		
		int input = 3;
			/* Possible values:
			 * 0 - trade
			 * 1 - buy
			 * 2 - play dev card
			 * 3 - end turn
			 */
		
		do {
			input = 3; //TODO: input

			if (input == 0) {
				trade(p);
			}
			else if (input == 1) {
				buy(p);
			}
			else if (input == 2) {
				playDevCard(p);
			}
			
		} while (input != 3);
	}
	
	/**
	 * Have given Player trade other Players
	 * @param p the Player initiating trading
	 */
	private void trade(Player p) {
		int input = 0; //TODO: input
			/* Possible values:
			 * 0 - trade player 0
			 * 1 - trade player 1
			 * 2 - trade player 2 (if 4 player game)
			 * 3 - done trading
			 */
		
		//TODO: functionality
	}
	
	/**
	 * The actual buying followed by placing (if applicable) of something
	 * This method handles the most checks (excluding object placement) and changes values of resources and victory points
	 * @param p the Player doing the buying
	 * @return whether the buying succeeded or not
	 */
	private void buy(Player p) {
		
		int input = 5; //TODO: input
			/* Possibe values:
			 * 1 - road
			 * 2 - settlement
			 * 3 - city
			 * 4 - dev card
			 * 5 - done
			 */
		
		do {
			input = 5; //TODO: input
			switch (input) {
			case 1:
				if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
					//TODO: throw error about not enough resources
				}
				
				int numbRoads = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 3; k++) {
							if (board.getRoad(new EdgeLocation(i, j, k)).getOwner().equals(p))
								numbRoads++;
						}
					}
				}
				if (numbRoads >= 15) {
					//TODO: throw error about too many of object owned already
				}
				
				// Place the Settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
				p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
				
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 2:
				if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("GRAIN") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("LUMBER") < 1) {
					//TODO: throw error about not enough resources
				}
				
				int numbSettlements = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 2; k++) {
							if (board.getStructure(new VertexLocation(i, j, k)).getType() == 0 && board.getStructure(new VertexLocation(i, j, k)).getOwner().equals(p))
								numbSettlements++;
						}
					}
				}
				if (numbSettlements >= 5) {
					//TODO: throw error about too many of object owned already
				}
				
				
				// Place the Settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
				p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
				p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
	
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 3:
				if (p.getNumberResourcesType("GRAIN") < 2 || p.getNumberResourcesType("ORE") < 3) {
					//TODO: throw error about not enough resources
				}
				
				int numbCities = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						for (int k = 0; k < 2; k++) {
							if (board.getStructure(new VertexLocation(i, j, k)).getType() == 1 && board.getStructure(new VertexLocation(i, j, k)).getOwner().equals(p))
								numbCities++;
						}
					}
				}
				if (numbCities >= 4) {
					//TODO: throw error about too many of object owned already
				}
				
				// Upgrade the settlement
				buyObject(p, input);
				
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 2);
				p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 3);
	
				p.setVictoryPoints(p.getVictoryPoints() + 1);
	
				break;
			case 4:
				if (p.getNumberResourcesType("ORE") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("GRAIN") < 1) {
					//TODO: throw error about not enough resources
				}
				
				// Assign the DevCard to the Player
				buyObject(p, input);
				
				p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
				p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
				p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
	
				break;
			}
		} while (input != 5);
	}
	
	/**
	 * Have Player p buy a thing of type choice
	 * This method mostly handles calling the actual placement of an object
	 * @param p the Player doing the buying
	 * @param choice the structure/road/card to buy
	 */
	private void buyObject(Player p, int choice) {
		
		if (choice == 1 || choice == 2 || choice == 3) {

			boolean goodSpot = true;
			
			if (choice == 1) {
				do {
					if (!goodSpot) {
						//TODO: throw error invalid placement
					}
					
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 2] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
					
					EdgeLocation loc = new EdgeLocation(xCoord, yCoord, orient);
					goodSpot = board.placeRoad(loc, p);
				} while (!goodSpot);
			}
			else if (choice == 2) {
				do {
					if (!goodSpot) {
						//TODO: throw error invalid placement
					}
					
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 2] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
					
					VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
					goodSpot = board.placeStructure(loc, p);
				} while (!goodSpot);
			}
			else if (choice == 3) {
				do {
					int locInput = 0; //TODO: input
						/* Three digits
						 * xCoord is hundreds place within [1, 7]
						 * yCoord is tens place within [1, 7]
						 * orient is ones place within [0, 2] 
						 */
					int xCoord = locInput / 100;
					int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
					int orient = locInput % 10;
										
					VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
					
					Structure s = board.getStructure(loc);
					
					if (!s.getOwner().equals(p)) {
						//TODO: throw error about unowned settlement
					}
					else {
						goodSpot = true;
					}
	
					Structure c = new City(s.getLocation());
					c.setOwner(s.getOwner());
					board.setStructure(loc, c);
				} while (!goodSpot);
			}
		}
		else if (choice == 4) {
			p.addDevCard(deck.draw());
		}
	}
}