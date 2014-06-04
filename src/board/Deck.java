package board;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class models the deck of dev cards from Settlers of Catan
 */
public class Deck {

	private ArrayList<DevCard> cards;


	/**
	 * Constructor for the deck of the game; adds all the Cards then shuffles the deck
	 */
	public Deck() {
		cards = new ArrayList<DevCard>(25);
		
		for (int i = 0; i < 14; i++)
			cards.add(new DevCard("Knight"));
		cards.add(new DevCard("Progress", "Road building"));
		cards.add(new DevCard("Progress", "Road building"));
		cards.add(new DevCard("Progress", "Year of plenty"));
		cards.add(new DevCard("Progress", "Year of plenty"));
		cards.add(new DevCard("Progress", "Monopoly"));
		cards.add(new DevCard("Progress", "Monopoly"));
		for (int i = 0; i < 5; i++)
			cards.add(new DevCard("Victory Point"));
		
		
		Collections.shuffle(cards);
	}
	
	/**
	 * Draws the "top" Card of the deck
	 * @return Card a random Card of the deck
	 */
	public DevCard draw() {
		if (cards.size() > 0) {
			return cards.remove(cards.size() - 1);
		}
		else
			return null;
	}
	
	/**
	 * Checks whether there are still cards in the deck
	 * @return whether the cards have run out
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}
}
