package game;

import java.awt.Color;


public class Player {
	
	private String name;
	private Color color;
	
	
	/**
	 * Constructor takes params for assignment to fields
	 * @param n is the <code>Player</code>'s name
	 * @param c is the <code>Player</code>'s color in game
	 */
	public Player(String n, Color c) {
		name = n;
		color = c;
	}
	
	/**
	 * Accessor for field name
	 * @return name of <code>Player</code>
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor for the field color
	 * @return color of <code>Player</code>
	 */
	public Color getColor() {
		return color;
	}
}
