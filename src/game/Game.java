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
		for (Player p : players) {
			//Ask to place settlement and road
		}
	}
}