package board;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {

	private ArrayList<Card> cards;


	/**
	 * Constructor for the deck of the game; adds all the Cards then shuffles the deck
	 */
	public Deck() {
		cards = new ArrayList<Card>(25);
		
		//TODO
		
		Collections.shuffle(cards);
	}
	
	/**
	 * Draws the "top" Card of the deck
	 * @return Card a random Card of the deck
	 */
	public Card draw() {
		if (cards.size() > 0)
			return cards.remove(cards.size());
		else
			return null;
	}
}
