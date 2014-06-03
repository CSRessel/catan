package game;

import gui.GameWindow;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import board.DevCard;

public class GameRunner {
	
	public static Player currentPlayer;
	private static int index = 0;
	public static ArrayList<Player> players = new ArrayList<Player>();
	private static Game game;

	public static void main(String[] args) {
		
		players.add(new Player("DevMaster", Color.BLUE,8,9,10,11,12));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		players.add(new Player("Wonder Woman", Color.GREEN));
						
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameWindow tmp = new GameWindow(players);
				game = tmp.getBoard().getGame();
			}
		});
	}
	
	public static void nextPlayer() {
		currentPlayer = players.get((index + 1) % 4);
		index = (index + 1) % 4;
	}
	
	public static void prevPlayer() {
		currentPlayer = players.get((index - 1) % 4);
		index = (index - 1) % 4;
	}
}
