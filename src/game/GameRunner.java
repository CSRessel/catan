package game;

import gui.GameWindow;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class GameRunner {
	
	public static Player currentPlayer;
	public static boolean turnDone = false;
	
	public static void main(String[] args) {
		
		final ArrayList<Player> players = new ArrayList<Player>(4);

		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		players.add(new Player("Wonder Woman", Color.WHITE));
		
		Game game = new Game(players);
		
		currentPlayer = players.get(0);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameWindow(players);
			}
		});
		
		//TODO setup phase here
		
		while (!game.over()) {
			for (Player p : players) {
				turnDone = false;
				currentPlayer = p;
				while (!turnDone) {}
			}
		}
		
		//TODO response to game win
		Player winner = game.winningPlayer();
		
	}
}
