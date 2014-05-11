package board;


/**
 * Card models a dev card from Settler of Catan
 */
public class DevCard {

	private final String cardType;
		/*
		 * Main card types:
		 *   Knight (AKA Soldier) (x14)
		 *   Progress (x6)
		 *   Victory Point (x5)
		 */
	private final String subType;
		/*
		 * Sub type is:
		 *   Knight:
		 *     No sub type. Description:
		 *     Immediately move the robber to a different location.
		 *     Steal one resource card (randomly) from one player w/ settlement or city adjacent to robber (if multiple players, you choose).
		 *     If that player has no cards, you get nothing. You may ask how many cards a player has.
		 * 
		 *   Progress:
		 *     Road Building (place two roads) (x2)
		 *     Year of Plenty (take any two resource cards from supply stacks, unusable that turn) (x2)
		 *     Monopoly (take all cards of one name resource from every other player's hand) (x2)
		 *   Victory Point:
		 *     No sub type. Description:
		 *     Worth one victory point (to be revealed at end of game).
		 */
	
	/**
	 * Creates a dev card with one param as main type of Card
	 * @param cT the main type of the Card (Knight, Progress, or Victory Point)
	 */
	public DevCard(String cT) {
		cardType = cT;
		subType = null;
	}
	
	/**
	 * Creates a dev card with two params as main and sub types of the Card
	 * @param cT the main type of the Card (Knight, Progress, or Victory Point)
	 * @param sT the sub type of the Card (read comments)
	 */
	public DevCard(String cT, String sT) {
		cardType = cT;
		subType = sT;
	}
	
	/**
	 * Getter for the type of the Card
	 * @return the main type of the Card (Knight, Progress, or Victory Point)
	 */
	public String getType() {
		return cardType;
	}
	
	/**
	 * Getter for the sub type for Cards of main type "Progress"
	 * @return the sub type of the Card ("Road Building", "Year of Plenty", or "Monopoly" for cardType="Progress", null otherwise)
	 */
	public String getSubType() {
		return subType;
	}
}
