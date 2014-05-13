package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Board represents the board in Settlers of Catan, and contains the grids for tiles, structures, and roads.
 */
public class Board {

	private Tile[][] tiles;
	private Structure[][][] structures;
	private Road[][][] roads;

	
	/**
	 * Constructor for Board, creates the hexagonal grid for the tiles, with arbitrary third axis for structures and roads.
	 * Tiles randomly placed, and assigned numbers according to the Settlers of Catan rulebook, going in a spiral fashion and skipping the desert.
	 * Settlements and Roads are placed at every vertex and edge, respectively, with unassigned players.
	 */
	public Board() {
		
		tiles = new Tile[7][7];
		structures = new Structure[7][7][2];
		roads = new Road[7][7][3];


		ArrayList<Tile> tileList = new ArrayList<Tile>();
		tileList.add(new Tile("Lumber")); tileList.add(new Tile("Lumber")); tileList.add(new Tile("Lumber")); tileList.add(new Tile("Lumber"));
		tileList.add(new Tile("Brick")); tileList.add(new Tile("Brick")); tileList.add(new Tile("Brick")); 
		tileList.add(new Tile("Wheat")); tileList.add(new Tile("Wheat")); tileList.add(new Tile("Wheat")); tileList.add(new Tile("Wheat")); 
		tileList.add(new Tile("Sheep")); tileList.add(new Tile("Sheep")); tileList.add(new Tile("Sheep")); tileList.add(new Tile("Sheep"));
		tileList.add(new Tile("Ore")); tileList.add(new Tile("Ore")); tileList.add(new Tile("Ore"));tileList.add(new Tile("Ore"));
		tileList.add(new Tile("Desert", true)); 

		Collections.shuffle(tileList);

		for (int i = 0; i < tileList.size(); i++) {					
			for (int row = 1; row < 6; row++) {
				switch (row) {
				case 1:
					for (int col = 1; col < 4; col++) {
						tiles[row][col] = tileList.get(i);
						tiles[row][col].setCoords(col, row);
					}
					break;
				case 2:
					for (int col = 1; col < 5; col++) {
						tiles[row][col] = tileList.get(i);
						tiles[row][col].setCoords(col, row);
					}
					break;
				case 3:
					for (int col = 1; col < 6; col++) {
						tiles[row][col] = tileList.get(i);
						tiles[row][col].setCoords(col, row);
					}
					break;
				case 4:
					for (int col = 2; col < 6; col++) {
						tiles[row][col] = tileList.get(i);
						tiles[row][col].setCoords(col, row);
					}
					break;
				case 5:
					for (int col = 3; col < 6; col++) {
						tiles[row][col] = tileList.get(i);
						tiles[row][col].setCoords(col, row);
					}
					break;
				}

			}
		}

		int[] numberOrder = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
		int[] tileOrder = {3,5, 2,4, 1,3, 1,2, 1,1, 2,1, 3,1, 4,2, 5,3, 5,4, 5,5, 4,5, 3,4, 2,3, 2,2, 3,2, 4,3, 4,4, 3,3};
		int numberTile = 0;
		
		for (int n = 0; n < tileOrder.length - 1; n+=2) {
			if (tiles[tileOrder[n+1]][tileOrder[n]].getType().equals("Desert")) {
			}
			else {
				tiles[tileOrder[n+1]][tileOrder[n]].setNumber(numberOrder[numberTile]);
				numberTile++;
			}
		}
		
		for (int row = 0; row < structures.length; row++) {
			for (int col = 0; col < structures[0].length; col++) {
				for (int ori = 0; ori < structures[0][0].length; ori++) {
					structures[row][col][ori] = new Settlement(col, row, ori);
				}
			}
		}
		
		for (int row = 0; row < roads.length; row++) {
			for (int col = 0; col < roads[0].length; col++) {
				for (int ori = 0; ori < roads[0][0].length; ori++) {
					roads[row][col][ori] = new Road(col, row, ori);
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
			
			//TODO
			// Add all the six structures to the ArrayList (ex below)
			// Discuss orientation of axises with Allen
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][0]);
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][1]);
			
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
}