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
		for (Tile t : board.getTilesWithNumber(roll)) {
			t.giveResources();
		}
	}
	
	/**
	 * Goes through the trading phase for Player p
	 * @param p the Player initiating trading
	 */
	private void trade(Player p) {
		//TODO
		/*
		 * Get user input for trade phase
		 * Two cases:
		 * 	Initiate trade with another Player
		 * 	End trading
		 */
	}
	
	/**
	 * Goes through the build phase for Player p
	 * @param p the Player building
	 */
	private void build(Player p) {
		//TODO
		/*
		 * Get user input for build phase
		 * Three cases:
		 * 	Buy (dev cards, structures, roads, etc)
		 * 	Build (structures, roads)
		 * 	End turn
		 */
	}
}