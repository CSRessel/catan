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
				//TODO
				//Ask to place settlement and road
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
			
			
			// For each player execute the three phases of their turn (roll, trade, build)
			
			for (Player p : players) {
				roll();
				trade(p);
				build(p);
			}
		}
		
	}
	
	/**
	 * Rolls the die and allocates resources to players
	 */
	private void roll() {
		// RTD
		int roll = (int)(Math.random() * 6 + 1) + (int)(Math.random() * 6 + 1);
		
		// Distribute resources
		board.distributeResources(roll);
	}
	
	/**
	 * Goes through the trading phase for Player p
	 * @param p the Player initiating trading
	 */
	private void trade(Player p) {
		//TODO: input
		/*
		 * Get user input for trade phase
		 * Two cases:
		 * 	Initiate trade with another Player
		 * 	End trading
		 */
	}
	
	/**
	 * Goes through the build phase for Player p
	 * @param p the Player doing the building
	 */
	private void build(Player p) {
		
		int input = 0; //TODO: input
			/* Possible values:
			 * 1 - buy
			 * 2 - end turn
			 */
		boolean success;
		if (input == 1) {
			success = buy(p);
		}
		
		if (input == 2) {
			return;
		}
	}
	
	/**
	 * The actual buying followed by placing (if applicable) of something
	 * @param p the Player doing the buying
	 * @return whether the buying succeeded or not
	 */
	private boolean buy(Player p) {
		
		int input = 0; //TODO: input
			/* Possibe values:
			 * 1 - road
			 * 2 - settlement
			 * 3 - city
			 * 4 - dev card
			 * 5 - done
			 */
		
		switch (input) {
		case 1:
			if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("LUMBER") < 1)
				return false;
			
			// Place the Settlement
			buyObject(p, input);
			
			p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
			p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);

			break;
		case 2:
			if (p.getNumberResourcesType("BRICK") < 1 || p.getNumberResourcesType("GRAIN") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("LUMBER") < 1)
				return false;
			
			// Place the Settlement
			buyObject(p, input);
			
			p.setNumberResourcesType("BRICK", p.getNumberResourcesType("BRICK") - 1);
			p.setNumberResourcesType("LUMBER", p.getNumberResourcesType("LUMBER") - 1);
			p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);
			p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);

			break;
		case 3:
			if (p.getNumberResourcesType("GRAIN") < 2 || p.getNumberResourcesType("ORE") < 3)
				return false;
			
			// Upgrade the settlement
			buyObject(p, input);
			
			p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 2);
			p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 3);

			break;
		case 4:
			if (p.getNumberResourcesType("ORE") < 1 || p.getNumberResourcesType("WOOL") < 1 || p.getNumberResourcesType("GRAIN") < 1)
				return false;
			
			// Assign the DevCard to the Player
			buyObject(p, input);
			
			p.setNumberResourcesType("ORE", p.getNumberResourcesType("ORE") - 1);
			p.setNumberResourcesType("WOOL", p.getNumberResourcesType("WOOL") - 1);
			p.setNumberResourcesType("GRAIN", p.getNumberResourcesType("GRAIN") - 1);

			break;
		case 5:
			return true;
			
		default:
			throw new IllegalArgumentException("Invalid buy choice");
		}
		return true;
	}
	
	/**
	 * Have Player p buy a thing of type choice
	 * @param p the Player doing the buying
	 * @param choice the structure/road/card to buy
	 */
	private void buyObject(Player p, int choice) {
		
		int locInput = 0; //TODO: input
			/* Three digits
			 * xCoord is hundreds place within [1, 7]
			 * yCoord is tens place within [1, 7]
			 * orient is ones place within [0, 2] 
			 */
		int xCoord = locInput / 100;
		int yCoord = (locInput - (int)(locInput / 100)*(100))/10;
		int orient = locInput % 10;
		
		boolean goodSpot = false;
		
		if (choice == 1) {
			do {
				EdgeLocation loc = new EdgeLocation(xCoord, yCoord, orient);
				
				goodSpot = board.placeRoad(loc, p);
			} while (!goodSpot);
		}
		else if (choice == 2) {
			do {
				VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
				
				goodSpot = board.placeStructure(loc, p);
			} while (!goodSpot);
		}
		else if (choice == 3) {
			VertexLocation loc = new VertexLocation(xCoord, yCoord, orient);
			
			Structure s = board.getStructure(loc);
			Structure c = new City(s.getLocation());
			c.setOwner(s.getOwner());
			board.setStructure(loc, c);
		}
		else if (choice == 4) {
			p.addDevCard(deck.draw());
		}
	}
}