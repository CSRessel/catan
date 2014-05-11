package game;

import java.awt.Color;
import java.util.ArrayList;

public class GameRunner {
	
	public static void main(String[] args) {
		
		Player a = new Player("Allen", Color.MAGENTA);
		Player b = new Player("Bob", Color.CYAN);
		Player c = new Player("Cliff", Color.YELLOW);
		Player d = new Player("Denise", Color.GREEN);
		
		ArrayList<Player> players = new ArrayList<Player>(4);
		
		players.add(a);
		players.add(b);
		players.add(c);
		players.add(d);
		
		Game game = new Game(players);
		
		game.start();
	}
}
