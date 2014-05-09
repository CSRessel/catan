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
		
		for (int i = 0; i < 14; i++)
			cards.add(new Card("Knight"));
		cards.add(new Card("Progress", "Road Building"));
		cards.add(new Card("Progress", "Road Building"));
		cards.add(new Card("Progress", "Year of plenty"));
		cards.add(new Card("Progress", "Year of plenty"));
		cards.add(new Card("Progress", "Monopoly"));
		cards.add(new Card("Progress", "Monopoly"));
		for (int i = 0; i < 5; i++)
			cards.add(new Card("Victory Point"));
		
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
