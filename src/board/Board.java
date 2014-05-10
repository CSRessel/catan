package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board {
	
	private Tile[][] tiles;
	private Structure[][][] structures;
	private Road[][][] roads;
	
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
		tileList.add(new Tile("Desert")); 
		
		long seed = System.nanoTime();
		Collections.shuffle(tileList, new Random(seed));
		
		for (int i = 0; i < tileList.size(); i++) {
			for (int row = 1; row < 6; row ++) {
				switch (row) {
				case 1: 
					for (int col = 1; col < 4; col++){
						tiles[row][col] = tileList.get(i);
					}
					break;
				case 2: 
					for (int col = 1; col < 5; col++){
						tiles[row][col] = tileList.get(i);
					}
					break;
				case 3: 
					for (int col = 1; col < 6; col++){
						tiles[row][col] = tileList.get(i);
					}
					break;
				case 4: 
					for (int col = 2; col < 6; col++){
						tiles[row][col] = tileList.get(i);
					}
					break;
				case 5: 
					for (int col = 3; col < 6; col++){
						tiles[row][col] = tileList.get(i);
					}
					break;
				}
				
			}
		}
	}
	
}
