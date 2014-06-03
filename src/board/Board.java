package board;

import game.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


/**
 * Board represents the board in Settlers of Catan, and contains the grids for tiles, structures, and roads.
 */
public class Board {

	private Tile[][] tiles;
	private Structure[][][] structures;
	private Road[][][] roads;
	private Location robberLoc;
		// Board is slanted backwards, i.e.  \##\
	private Road endpoint = null; // For DPS
	private VertexLocation startside;


	/**
	 * Constructor for Board, creates the hexagonal grid for the tiles, with arbitrary third axis for structures and roads.
	 * Tiles randomly placed, and assigned numbers according to the Settlers of Catan rulebook, going in a spiral fashion and skipping the desert.
	 * Settlements and Roads are placed at every vertex and edge, respectively, with unassigned players.
	 */
	public Board() {

		tiles = new Tile[7][7];
		structures = new Structure[7][7][2];
		roads = new Road[7][7][3];
		Tile desert = new Tile("DESERT", true);

		// Create the ArrayList of all the tiles to be put in the board, with resource type defined
		ArrayList<Tile> tileList = new ArrayList<Tile>();
		tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER")); tileList.add(new Tile("LUMBER"));
		tileList.add(new Tile("BRICK")); tileList.add(new Tile("BRICK")); tileList.add(new Tile("BRICK"));
		tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN")); tileList.add(new Tile("GRAIN"));
		tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL")); tileList.add(new Tile("WOOL"));
		tileList.add(new Tile("ORE")); tileList.add(new Tile("ORE")); tileList.add(new Tile("ORE"));
		tileList.add(desert);

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

			robberLoc = desert.getLocation();
		}

		// The order of the numbers to be assigned to the tiles, followed by an int to be used as an index
		int[] numberOrder = {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
		int numberTile = 0;

		// The x y pairs to proceed in a spiral
		int[] tileOrder = {3,5, 2,4, 1,3, 1,2, 1,1, 2,1, 3,1, 4,2, 5,3, 5,4, 5,5, 4,5, 3,4, 2,3, 2,2, 3,2, 4,3, 4,4, 3,3};

		// Assigning all values from numberOrder to the Tiles in the board, proceeding in a spiral
		for (int n = 0; n < tileOrder.length - 1; n+=2) {
			if (numberTile == 18){
				break;
			}
			
			if (tiles[tileOrder[n]][tileOrder[n+1]].getType().equals("DESERT")) {
			}
			else {
				tiles[tileOrder[n]][tileOrder[n+1]].setNumber(numberOrder[numberTile]);
				numberTile++;
			}
		}

		// Place all the empty Tiles in Board
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (tiles[i][j] == null)
					tiles[i][j] = new Tile(i, j, 0, null);
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
			if (t.hasRobber() || t.getType().equals("DESERT")) {
				continue;
			}

			ArrayList<Structure> rollStructures = new ArrayList<Structure>();

			Location loc = t.getLocation();

			// Add all the six structures to the ArrayList
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][0]);
			rollStructures.add(structures[loc.getXCoord()][loc.getYCoord()][1]);
			rollStructures.add(structures[loc.getXCoord()+1][loc.getYCoord()+1][1]);
			rollStructures.add(structures[loc.getXCoord()-1][loc.getYCoord()-1][0]);
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

		for (int i = 1; i < tiles.length; i++) {
			for (int j = 1; j < tiles[i].length; j++) {
				if (tiles[i][j].getNumber() == numb)
					rollTiles.add(tiles[i][j]);
			}
		}
		return rollTiles;
	}

	/**
	 * Getter for the Structure at the given location
	 * @param loc the location to retrieve from
	 * @return the Structure from that place
	 */
	public Structure getStructure(VertexLocation loc) {
		return structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()];
	}

	/**
	 * Setter for the Structure at given location
	 * @param loc the location to change
	 * @param s the Structure to set it to
	 */
	public void setStructure(VertexLocation loc, Structure s) {
		structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()] = s;
	}

	/**
	 * Getter for the Road at the given location
	 * @param loc the location to retrieve from
	 * @return the Road from that place
	 */
	public Road getRoad(EdgeLocation loc) {
		return roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()];
	}

	/**
	 * Assigns the settlement to the given player, even without a road
	 * @param loc Location of settlement
	 * @param player Player placing the settlement
	 * @return boolean true if successful
	 */
	public boolean placeStructureNoRoad(VertexLocation loc, Player player) {

		if (structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].getOwner() != null) { //Vertex is already occupied
			return false;
		}
		
		if (loc.getOrientation() == 0) {
			if (structures[loc.getXCoord()][loc.getYCoord()+1][1].getOwner() == null &&
				structures[loc.getXCoord()+1][loc.getYCoord()+1][1].getOwner() == null &&
				!(loc.getYCoord() + 2 <= 6 && !(structures[loc.getXCoord()+1][loc.getYCoord()+2][1].getOwner() == null)))
			{
				if (checkPort(loc) != -1)
					player.addPort(checkPort(loc));
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (structures[loc.getXCoord()][loc.getYCoord()-1][0].getOwner() == null &&
				structures[loc.getXCoord()-1][loc.getYCoord()-1][0].getOwner() == null &&
				!(loc.getYCoord() - 2 >=0 && !(structures[loc.getXCoord()-1][loc.getYCoord()-2][0].getOwner() == null)))
			{
				if (checkPort(loc) != -1)
					player.addPort(checkPort(loc));
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Checks location for validity for given player, then assigns the settlement to the given player
	 * @param loc Location of settlement
	 * @param player Player placing the settlement
	 * @return boolean true if successful
	 */
	public boolean placeStructure(VertexLocation loc, Player player) {

		if (structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].getOwner() != null) { //Vertex is already occupied
			return false;
		}
		
		if (loc.getOrientation() == 0) {
			if ((player.equals(roads[loc.getXCoord()][loc.getYCoord()][0].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord()][1].getOwner()) ||
				player.equals(roads[loc.getXCoord()][loc.getYCoord() + 1][2].getOwner()))
				&&
				(structures[loc.getXCoord()][loc.getYCoord()+1][1].getOwner() == null &&
				structures[loc.getXCoord()+1][loc.getYCoord()+1][1].getOwner() == null &&
				!(loc.getYCoord() + 2 <=6 && !(structures[loc.getXCoord()+1][loc.getYCoord()+2][1].getOwner() == null))))
			{
				if (checkPort(loc) != -1)
					player.addPort(checkPort(loc));
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(player);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if ((player.equals(roads[loc.getXCoord()][loc.getYCoord() - 1 ][0].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord() - 1][1].getOwner()) ||
				player.equals(roads[loc.getXCoord() - 1][loc.getYCoord() - 1][2].getOwner()))
				&&
				(structures[loc.getXCoord()][loc.getYCoord()-1][0].getOwner() == null &&
				structures[loc.getXCoord()-1][loc.getYCoord()-1][0].getOwner() == null &&
				!(loc.getYCoord() - 2 >=0 && !(structures[loc.getXCoord()-1][loc.getYCoord()-2][0].getOwner() == null))))
			{
				if (checkPort(loc) != -1)
					player.addPort(checkPort(loc));
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
		
		if (roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].getOwner() != null) { //Vertex is already occupied
			return false;
		}

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

	/**
	 * Checks location for validity for given player, then upgrades settlement into city
	 * @param loc Location of road
	 * @param player Player placing the road
	 * @return boolean true if successful
	 */
	public boolean placeCity(VertexLocation loc, Player player) {
		if (player.equals(structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].getOwner()) &&
				structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].getType() == 0) {
			structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setType(1);
			return true;
		}
		else
			return false;
	}
	/**
	 * Getter for the Location of the Robber
	 * @return loc the current Location of the Robber in this board
	 */
	public Location getRobberLocation() {
		return robberLoc;
	}

	/**
	 * Setter for the Location of the Robber
	 * @param loc the new Location of Robber
	 */
	public void setRobberLocation(Location loc) {
		robberLoc = loc;
	}
	
	/**
	 * Checks location for validity, then moves the robber to that location
	 * @param loc location to move to
	 * @return Location the robber moved to
	 */
	
	public boolean moveRobber(Location loc) {
		Location current = getRobberLocation();
		if (loc.getXCoord() == current.getXCoord() &&
				loc.getYCoord() == current.getYCoord()) {
			return false;
		}
		else{
			tiles[current.getXCoord()][current.getYCoord()].setRobber(false);
			setRobberLocation(loc);
			tiles[loc.getXCoord()][loc.getYCoord()].setRobber(true);
			return true;
		}
	}

	/**
	 * Gets all players with structures adjacent to the current robber location
	 * @return ArrayList of adjacent players
	 */
	public ArrayList<Player> getRobberAdjacentPlayers() {
		Location loc = getRobberLocation();
		ArrayList<Structure> temp = new ArrayList<Structure>();
		ArrayList<Player> players = new ArrayList<Player>();
		temp.add(structures[loc.getXCoord()][loc.getYCoord()][0]);
		temp.add(structures[loc.getXCoord() + 1][loc.getYCoord() + 1][1]);
		temp.add(structures[loc.getXCoord()][loc.getYCoord() - 1][0]);
		temp.add(structures[loc.getXCoord()][loc.getYCoord()][1]);
		temp.add(structures[loc.getXCoord() - 1][loc.getYCoord() - 1][0]);
		temp.add(structures[loc.getXCoord()][loc.getYCoord() + 1][1]);
		
		for (Structure s : temp) {
			if (s.getOwner() != null) {
				if (Collections.frequency(players, s.getOwner()) < 1) {
					players.add(s.getOwner());
				}
			}
		}
		
		return players;
	}

	/**
	 * Getter for the Tile at the given Location
	 * @param loc the Location to retrieve from
	 * @return the Tile there
	 */
	public Tile getTile(Location loc) {
		return tiles[loc.getXCoord()][loc.getYCoord()];
	}

	/**
	 * Gives the tiles adjacent to the given VertexLocation
	 * @param VertexLocation location being checked
	 * @return ArrayList<Tile> list of adjacent tiles
	 */
	public ArrayList<Tile> getAdjacentTilesStructure(VertexLocation loc) {
		ArrayList<Tile> output = new ArrayList<Tile>();
		if (loc.getOrientation() == 0) {
			Tile a = tiles[loc.getXCoord()][loc.getYCoord()];
			if (a.getType() != null)
				output.add(a);
			Tile b = tiles[loc.getXCoord()][loc.getYCoord() + 1];
			if (b.getType() != null)
				output.add(b);
			Tile c = tiles[loc.getXCoord() + 1][loc.getYCoord() + 1];
			if (c.getType() != null)
				output.add(c);
		}	
		else {
			Tile a = tiles[loc.getXCoord()][loc.getYCoord()];
			if (a.getType() != null)
				output.add(a);
			Tile b = tiles[loc.getXCoord()][loc.getYCoord() - 1];
			if (b.getType() != null)
				output.add(b);
			Tile c = tiles[loc.getXCoord() - 1][loc.getYCoord() - 1];
			if (c.getType() != null)
				output.add(c);
		}
		return output;
	}

	/**
	 * Finds the length of the longest chain of roads of the given player
	 * @param player player's roads to be analyzed
	 * @return int length of the longest chain of roads
	 */
	public int findLongestRoad(Player p) { //TODO test
		ArrayList<Road> roadList = (ArrayList<Road>) p.getRoads().clone();
		int maxCount = 1;

		while (roadList.size() > 0) {
			ArrayList<Road> connectedRoads = new ArrayList<Road>();
			connectedRoads.add(roadList.remove(0));

			for (int i = 0; i <= connectedRoads.size(); i++) {
				ArrayList<Road> adjacentRoads = findAdjacentRoads(connectedRoads.get(i).getLocation());

				for (int k = 0; k <= adjacentRoads.size(); k++) {
					int index = roadList.indexOf(adjacentRoads.get(k));
					if (index >= 0) {
						connectedRoads.add(roadList.remove(index));
					}
				}
			}

			if (endpoint == null) {
				endpoint = connectedRoads.get(0);
				if (endpoint.getLocation().getOrientation() == 0 || endpoint.getLocation().getOrientation() == 1) {
					startside = structures[endpoint.getLocation().getXCoord()][endpoint.getLocation().getYCoord()][0].getLocation();
				}
				else {
					startside = structures[endpoint.getLocation().getXCoord() + 1][endpoint.getLocation().getYCoord() + 1][1].getLocation();
				}
			}

			Stack<Road> s = new Stack();
			Stack<VertexLocation> entrysides = new Stack();
			s.push(endpoint);

			entrysides.push(startside);
			int count = 1;
			while (s.empty() == false) {
				s.peek().visit();
				ArrayList<Road> children = findAdjacentRoadsDFS(s.peek(),entrysides.peek());
				for (int i = 0; i < children.size(); i++) {
					if (children.get(i).isVisited()) {
						children.remove(i);
						i--;
					}
				}
				if (children.size() <= 0) {
					s.pop();
					entrysides.pop();
					if (count >= maxCount)
						maxCount = count;
					count--;
				}
				else {
					count++;
					entrysides.push(roadConnectsToOther(s.peek(),children.get(0)));
					s.push(children.get(0));
				}
			}

			for (int i = 0; i < connectedRoads.size();i++) {  //Reset boolean visited
				connectedRoads.get(i).resetVisited();
			}
		}

		endpoint = null; //Reset endpoint
		startside = null;
		return maxCount;
	}

	/**
	 * Finds all adjacent and connected roads by longest road standards to the given location
	 * Prerequisite: Given location has a road that has an owner.
	 * @param loc location of road
	 * @return ArrayList<Road> of connected roads
	 */
	private ArrayList<Road> findAdjacentRoads(EdgeLocation loc) {
		Road r = roads[loc.getXCoord()][loc.getYCoord()][0];
		ArrayList<Road> output = new ArrayList<Road>();
		Player p = r.getOwner();
		int x = loc.getXCoord();
		int y = loc.getYCoord();
		int o = loc.getOrientation();

		if (o == 0) {
			if (p.equals(structures[x][y + 1][1].getOwner()) || structures[x][y + 1][1].getOwner() == null) {
				if (!p.equals(roads[x - 1][y][1].getOwner()) && !p.equals(roads[x - 1][y][2].getOwner())) {
					startside = structures[x][y + 1][1].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x - 1][y][1].getOwner())) {
						output.add(roads[x - 1][y][1]);
					}
					if (p.equals(roads[x - 1][y][2].getOwner())) {
						output.add(roads[x - 1][y][2]);
					}
				}
			}
			if (p.equals(structures[x][y][0].getOwner()) || structures[x][y][0].getOwner() == null) {
				if (!p.equals(roads[x][y + 1][2].getOwner()) && !p.equals(roads[x][y][1].getOwner())) {
					startside = structures[x][y][0].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x][y + 1][2].getOwner())) {
						output.add(roads[x][y + 1][2]);
					}
					if (p.equals(roads[x][y][1].getOwner())) {
						output.add(roads[x][y][1]);
					}
				}
			}
		}
		else if (o == 1) {
			if (p.equals(structures[x + 1][y + 1][1].getOwner()) || structures[x + 1][y + 1][1].getOwner() == null) {
				if (!p.equals(roads[x + 1][y][0].getOwner()) && !p.equals(roads[x][y][2].getOwner())) {
					startside = structures[x + 1][y + 1][1].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x + 1][y][0].getOwner())) {
						output.add(roads[x + 1][y][0]);
					}
					if (p.equals(roads[x][y][2].getOwner())) {
						output.add(roads[x][y][2]);
					}
				}
			}
			if (p.equals(structures[x][y][0].getOwner()) || structures[x][y][0].getOwner() == null) {
				if (!p.equals(roads[x][y + 1][2].getOwner()) && !p.equals(roads[x][y][0].getOwner())) {
					startside = structures[x][y][0].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x][y + 1][2].getOwner())) {
						output.add(roads[x][y + 1][2]);
					}
					if (p.equals(roads[x][y][0].getOwner())) {
						output.add(roads[x][y][0]);
					}
				}
			}
		}
		else {
			if (p.equals(structures[x + 1][y + 1][1].getOwner()) || structures[x + 1][y + 1][1].getOwner() == null) {
				if (!p.equals(roads[x + 1][y][0].getOwner()) && !p.equals(roads[x][y][1].getOwner())) {
					startside = structures[x + 1][y + 1][1].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x + 1][y][0].getOwner())) {
						output.add(roads[x + 1][y][0]);
					}
					if (p.equals(roads[x][y][1].getOwner())) {
						output.add(roads[x][y][1]);
					}
				}
			}
			if (p.equals(structures[x][y - 1][0].getOwner()) || structures[x][y - 1][0].getOwner() == null) {
				if (!p.equals(roads[x][y - 1][1].getOwner()) && !p.equals(roads[x][y - 1][0].getOwner())) {
					startside = structures[x][y - 1][0].getLocation();
					endpoint = r;
				}
				else {
					if (p.equals(roads[x][y - 1][1].getOwner())) {
						output.add(roads[x][y - 1][1]);
					}
					if (p.equals(roads[x][y - 1][0].getOwner())) {
						output.add(roads[x][y - 1][0]);
					}
				}
			}
		}

		return output;
	}

	/**
	 * Finds all adjacent and connected roads by longest road standards to the given location on the opposite side of the entry side
	 * Prerequisite: Given location has a road that has an owner.
	 * @param loc location of road
	 * @return ArrayList<Road> of connected roads
	 */
	private ArrayList<Road> findAdjacentRoadsDFS(Road r, VertexLocation entryside) {
		ArrayList<Road> check = new ArrayList<Road>();
		Structure s = structures[entryside.getXCoord()][entryside.getYCoord()][entryside.getOrientation()];
		Player p = r.getOwner();
		int x = r.getLocation().getXCoord();
		int y = r.getLocation().getYCoord();
		int o = r.getLocation().getOrientation();

		if (o == 0) {
			if (entryside.getOrientation() == 0 && (p.equals(s.getOwner()) || s.getOwner() == null)) {
				check.add(roads[x - 1][y][2]);
				check.add(roads[x - 1][y][1]);
			}
			else if (p.equals(s.getOwner()) || s.getOwner() == null) {
				check.add(roads[x][y][1]);
				check.add(roads[x][y + 1][2]);
			}
		}
		else if (o == 1) {
			if (entryside.getOrientation() == 0 && (p.equals(s.getOwner()) || s.getOwner() == null)) {
				check.add(roads[x][y][2]);
				check.add(roads[x + 1][y][0]);
			}
			else if (p.equals(s.getOwner()) || s.getOwner() == null) {
				check.add(roads[x][y][0]);
				check.add(roads[x][y + 1][2]);
			}
		}
		else if (o == 2) {
			if (entryside.getOrientation() == 0 && (p.equals(s.getOwner()) || s.getOwner() == null)) {
				check.add(roads[x + 1][y][0]);
				check.add(roads[x][y][1]);
			}
			else if (p.equals(s.getOwner()) || s.getOwner() == null) {
				check.add(roads[x][y - 1][1]);
				check.add(roads[x][y - 1][0]);
			}
		}

		for (int i = 0; i < check.size(); i++){
			if (p.equals(check.get(i).getOwner()));
			else{
				check.remove(i);
				i--;
			}
		}
		return check;
	}

	 /**
	 * Find the settlement between two connected roads
	 * Prerequisite: two roads are connected
	 * @param r orginal road
	 * @param other checked road
	 * @return VertexLocation in between
	 */
	private VertexLocation roadConnectsToOther(Road r, Road other) {
		int ro = r.getLocation().getOrientation();
		int rx = r.getLocation().getXCoord();
		int ry = r.getLocation().getYCoord();
		int oo = other.getLocation().getOrientation();
		int ox = other.getLocation().getXCoord();
		int oy = other.getLocation().getYCoord();

		if (ro == 0) {
			if (oo == 1) {
				if (rx == ox) {
					return structures[rx][ry][0].getLocation();
				}
				else {
					return structures[rx][ry + 1][1].getLocation();
				}
			}
			else {
				if (ry + 1 == oy) {
					return structures[rx][ry][0].getLocation();
				}
				else {
					return structures[rx][ry + 1][1].getLocation();
				}
			}
		}
		else if (ro == 1) {
			if (oo == 0) {
				if (rx == ox) {
					return structures[rx][ry][0].getLocation();
				}
				else {
					return structures[rx + 1][ry + 1][1].getLocation();
				}
			}
			else {
				if (ry + 1 == oy) {
					return structures[rx][ry][0].getLocation();
				}
				else {
					return structures[rx - 1][ry][1].getLocation();
				}
			}
		}
		else {
			if (oo == 0) {
				if (rx == ox) {
					return structures[rx][ry - 1][0].getLocation();
				}
				else {
					return structures[rx + 1][ry + 1][1].getLocation();
				}
			}
			else {
				if (ry == oy) {
					return structures[rx + 1][ry + 1][1].getLocation();
				}
				else {
					return structures[rx][ry - 1][0].getLocation();
				}
			}
		}

	}
	
	/**
	 * Checks if given VertexLocation is a port, and returns the portTag is it is.
	 * @param loc
	 * @return int portTag if port, -1 if not
	 * 				  0 = general
					  1 = brick
					  2 = wool
					  3 = ore
					  4 = grain
					  5 = lumber
	 */
	private int checkPort(VertexLocation loc) {
		int x = loc.getXCoord();
		int y = loc.getYCoord();
		int o = loc.getOrientation();
		
		if ((x == 4 && y == 1 && o == 0) ||
				(x == 4 && y == 2 && o == 1)) {
			return 1;
		}
		else if ((x == 4 && y == 5 && o == 0) ||
				(x == 5 && y == 6 && o == 1)) {
			return 2; 
		}
		else if ((x == 1 && y == 3 && o == 0) ||
				(x == 2 && y == 5 && o == 1)) {
			return 3; 
		}
		else if ((x == 0 && y == 1 && o == 0) ||
				(x == 1 && y == 3 && o == 1)) {
			return 4; 
		}
		else if ((x == 2 && y == 0 && o == 0) ||
				(x == 2 && y == 1 && o == 1)) {
			return 5; 
		}
		else if ((x == 0 && y == 0 && o == 0) ||
				(x == 1 && y == 1 && o == 1) ||
				(x == 5 && y == 2 && o == 0) ||
				(x == 6 && y == 4 && o == 1) ||
				(x == 5 && y == 4 && o == 0) ||
				(x == 6 && y == 5 && o == 1) ||
				(x == 3 && y == 5 && o == 0) ||
				(x == 3 && y == 6 && o == 1)) {
			return 0; 
		}
		else {
			return -1;
		}
	}

	/**
	 * Getter for tiles array
	 * @return tiles array
	 */
	public Tile[][] getTiles(){
		return tiles;
	}
	
	/**
	 * Getter for structures array
	 * @return structures array
	 */
	public Structure[][][] getStructures(){
		return structures;
	}
	
	/**
	 * Getter for roads array
	 * @return roads array
	 */
	public Road[][][] getRoads(){
		return roads;
	}
}
