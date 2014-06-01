package game;

import gui.GameWindow;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class GameRunner {
	
	public static Player currentPlayer;
	
	public static void main(String[] args) {
		
		final ArrayList<Player> players = new ArrayList<Player>(4);

		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		
		Game game = new Game(players);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			new GameWindow(players);
			}
			});
	}
}
