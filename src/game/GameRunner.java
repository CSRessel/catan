package game;

import gui.GameWindow;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class GameRunner {
	
	public static Player currentPlayer;
	private static int index = 0;
	public static ArrayList<Player> players = new ArrayList<Player>();
	private static Game game;

	public static void main(String[] args) {
		
		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		players.add(new Player("Wonder Woman", Color.GREEN));
						
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameWindow tmp = new GameWindow(players);
				game = tmp.getBoard().getGame();
			}
		});
		
		//TODO setup phase here
	}
	
	public static void nextPlayer() {
		currentPlayer = players.get((index + 1) % 4);
		index = (index + 1) % 4;
	}
}
