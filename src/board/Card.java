package board;


/**
 * Card models a development (dev) card from Settler of Catan
 */
public class Card {

	private final String cardType;
	private final String subType;
	
	
	/**
	 * Creates a dev card with two params as main and sub types of the Card
	 * @param cT the main type of the Card (Knight, Progress, or Victory Point)
	 * @param sT the sub type of the Card (read comments)
	 */
	public Card(String cT, String sT) {
		cardType = cT;
		subType = sT;
	}
}
