package game;

import java.util.ArrayList;
import board.*;

public class Game {
	
	private Board board;
	private ArrayList<Player> players;
	
/**
 * Constructor for game, creates the Board. 
 * @param givenPlayers
 */
	
	public Game(ArrayList<Player> givenPlayers) {
		players = givenPlayers;
		board = new Board();
		
	}

}
