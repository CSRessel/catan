package board;

import game.Player;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Board represents the board in Settlers of Catan, and contains the grids for tiles, structures, and roads.
 */
public class Board {

	private Tile[][] tiles;
	private Structure[][][] structures;
	private Road[][][] roads;
		// Board is slanted backwards, i.e.  \##\

	
	/**
	 * Constructor for Board, creates the hexagonal grid for the tiles, with arbitrary third axis for structures and roads.
	 * Tiles randomly placed, and assigned numbers according to the Settlers of Catan rulebook, going in a spiral fashion and skipping the desert.
	 * Settlements and Roads are placed at every vertex and edge, respectively, with unassigned players.
	 */
	public Board() {
		
		tiles = new Tile[7][7];
		structures = new Structure[7][7][2];
		roads = new Road[7][7][3];

		// Create the ArrayList of all the tiles to be put in the board, with resource type defined
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER"));
		tileList.add(new Tile("BRICK")); tileList.add(new Tile("BRICK")); tileList.add(new Tile("BRICK")); 
		tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN")); 
		tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL"));
		tileList.add(new Tile("ORE")); tileList.add(new Tile("ORE")); tileList.add(new Tile("ORE"));tileList.add(new Tile("ORE"));
		tileList.add(new Tile("DESERT", true)); 

		// Create random order
		Collections.shuffle(tileList);

		// Place all the tiles in the board
		int count = 0;

		for (int row = 1; row < 6; row++) {
			switch (row) {
			case 1:
				for (int col = 1; col < 4; col++) {
					tiles[col][row] = tileList.get(count);
					tiles[col][row].setCoords(col, row);
					count++;
				}
				break;
			case 2:
				for (int col = 1; col < 5; col++) {
					tiles[col][row] = tileList.get(count);
					tiles[col][row].setCoords(col, row);
					count++;
				}
				break;
			case 3:
				for (int col = 1; col < 6; col++) {
					tiles[col][row] = tileList.get(count);
					tiles[col][row].setCoords(col, row);
					count++;
				}
				break;
			case 4:
				for (int col = 2; col < 6; col++) {
					tiles[col][row] = tileList.get(count);
					tiles[col][row].setCoords(col, row);
					count++;
				}
				break;
			case 5:
				for (int col = 3; col < 6; col++) {
					tiles[col][row] = tileList.get(count);
					tiles[col][row].setCoords(col, row);
					count++;
				}
				break;
			}
		}

		// The order of the numbers to be assigned to the tiles, followed by an int to be used as an index
		int[] numberOrder = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
		int numberTile = 0;

		// The x y pairs to proceed in a spiral
		int[] tileOrder = {3,5, 2,4, 1,3, 1,2, 1,1, 2,1, 3,1, 4,2, 5,3, 5,4, 5,5, 4,5, 3,4, 2,3, 2,2, 3,2, 4,3, 4,4, 3,3};
		
		// Assigning all values from numberOrder to the Tiles in the board, proceeding in a spiral
		for (int n = 0; n < tileOrder.length - 1; n+=2) {
			if (tiles[tileOrder[n]][tileOrder[n+1]].getType().equals("Desert")) {
			}
			else {
				tiles[tileOrder[n]][tileOrder[n+1]].setNumber(numberOrder[numberTile]);
				numberTile++;
			}
		}
		
		// Place all Structures in Board
		for (int row = 0; row < structures.length; row++) {
			for (int col = 0; col < structures[0].length; col++) {
				for (int ori = 0; ori < structures[0][0].length; ori++) {
					structures[col][row][ori] = new Settlement(col, row, ori);
				}
			}
		}
		
		// Place all the Roads in the Board
		for (int row = 0; row < roads.length; row++) {
			for (int col = 0; col < roads[0].length; col++) {
				for (int ori = 0; ori < roads[0][0].length; ori++) {
					roads[col][row][ori] = new Road(col, row, ori);
				}
			}
		}
	}
	
	/**
	 * Distributes resources to all Players with a Structure bordering Tiles with number roll
	 * @param roll the value of the Tiles that have produced
	 */
	public void distributeResources(int roll) {
		
		ArrayList<Tile> rollTiles = getTilesWithNumber(roll);
		
		for (Tile t : rollTiles) {
			
			ArrayList<Structure> rollStructures = new ArrayList<Structure>();
			
			Location loc = t.getLocation();
			
			// Add all the six structures to the ArrayList
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][0]);
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][1]);
			rollStructures.add(structures[loc.getXCoord()+1][loc.getYCoord()][1]);
			rollStructures.add(structures[loc.getXCoord()-1][loc.getYCoord()][0]);
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()+1][1]);
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()-1][0]);
			
			for (Structure s : rollStructures) {
				if (null != s.getOwner())
					s.giveResources(t.getType());
			}
		}
	}
	
	/**
	 * Searches the Board for any Tiles with the value of the param and returns an ArrayList of them
	 * @param numb the roll number to be found on the Tile
	 * @return an ArrayList of found Tiles
	 */
	private ArrayList<Tile> getTilesWithNumber(int numb) {
		
		ArrayList<Tile> rollTiles = new ArrayList<Tile>();
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; i++) {
				if (tiles[i][j].getNumber() == numb)
					rollTiles.add(tiles[i][j]);
			}
		}
		return rollTiles;
	}
	
	/**
	 * Finds and returns the Structure (always used for a Settlement) at given location
	 * @param loc the Location of the Structure
	 * @return the Structure at the Location
	 */
	public Structure getStructure(VertexLocation loc) {
		return structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()];
	}
	
	public void setStructure(VertexLocation loc, Structure s) {
		structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()] = s;
	}
	
	/**
	 * Assigns the settlement to the given player
	 * @param loc Location of settlement
	 * @param player Player placing the settlement
	 * @return Settlement placed
	 */
	public Structure placeStructureNoRestrict(VertexLocation loc, Player player) {
		
		structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
		return structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()];
		
	}
	
	/**
	 * Checks location for validity for given player, then assigns the settlement to the given player
	 * @param loc Location of settlement
	 * @param player Player placing the settlement
	 * @return boolean true if successful
	 */
	public boolean placeStructure(VertexLocation loc, Player player) {
		
		if (loc.getOrientation() == 0) {
			if (player.equals(roads[loc.getXCoord()][loc.getYCoord()][0].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][1].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() + 1][2].getOwner())) 
			{
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			if (player.equals(roads[loc.getXCoord()][loc.getYCoord() - 1 ][0].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord() - 1][1].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord() - 1][2].getOwner())) 
			{
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
			
			
		//structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
		
	}
	
	/**
	 * Checks location for validity for given player, the assigns the road to the given player
	 * @param loc Location of road
	 * @param player Player placing the road
	 * @return boolean true if successful
	 */
	public boolean placeRoad(EdgeLocation loc, Player player) {
		
		if (loc.getOrientation() == 0) {
			if (player.equals(structures[loc.getXCoord()][loc.getYCoord() + 1][1].getOwner()) ||
				player.equals(structures[loc.getXCoord()][loc.getYCoord()][0].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord()][1].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord()][2].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() + 1 ][2].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][1].getOwner())) 
			{
				roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
		
		else if (loc.getOrientation() == 1) {
			if (player.equals(structures[loc.getXCoord()][loc.getYCoord()][0].getOwner()) ||
				player.equals(structures[loc.getXCoord() + 1][loc.getYCoord() + 1][1].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][0].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() + 1][2].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][2].getOwner()) ||
				player.equals(roads[loc.getXCoord() + 1][loc.getYCoord()][0].getOwner())) 
			{
				roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			if (player.equals(structures[loc.getXCoord()][loc.getYCoord() - 1][0].getOwner()) ||
				player.equals(structures[loc.getXCoord() + 1][loc.getYCoord() + 1][1].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][1].getOwner()) ||
				player.equals(roads[loc.getXCoord() + 1][loc.getYCoord()][0].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() - 1][0].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() - 1][1].getOwner())) 
			{
				roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
					
			
		//roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
		
	}
}