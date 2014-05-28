package gui;




import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import board.*;
import game.*;

public class CatanBoard extends JPanel{
	
	private int state;
	private Game game;
	private int boardHeight;
	private int hexagonSide;
	private int heightMargin = 100; //TODO define
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	
	
	private Tile[][] tiles;
	private Road[][][] roads;
	private Structure[][][] structures;
	
	public CatanBoard() {
		
		ArrayList<Player> players = new ArrayList<Player>(); //TODO input players
		
		// Your temporary names are much better than mine :P
		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		game = new Game(players);
		
		tiles = game.getBoard().getTiles();
		roads = game.getBoard().getRoads();
		structures = game.getBoard().getStructures(); //TODO fix encapsulation
		
		game.getBoard().placeStructureNoRoad(new VertexLocation(3,3,0), players.get(0));
		game.getBoard().placeStructureNoRoad(new VertexLocation(4,3,1), players.get(0));
		game.getBoard().placeStructureNoRoad(new VertexLocation(5,3,0), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,0), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,1), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,2), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(3,3,0), players.get(0));
		game.getBoard().placeRoad(new EdgeLocation(3,3,1), players.get(0));
		game.getBoard().placeRoad(new EdgeLocation(3,3,2), players.get(0));
		structures[3][3][1].setType(1);
		
		setBackground(new Color(164,200,218)); //TODO draw background
		/*
		boardHeight = getHeight();
		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
		System.out.println("Boardheight: " + boardHeight);
		System.out.println("HexagonSide: " + hexagonSide);
		System.out.println("WidthMargin: " + widthMargin);
		*/
		this.addComponentListener(new ComponentListener() {

    		public void componentResized(ComponentEvent e) {			//TODO react to window resizing
    		//	System.out.println(e.getComponent().getSize());
    			boardHeight = getHeight();
    			hexagonSide = (boardHeight - 2 * heightMargin) / 8;
    			widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
    			System.out.println("Boardheight: " + boardHeight);
    			System.out.println("HexagonSide: " + hexagonSide);
    			System.out.println("WidthMargin: " + widthMargin);
    		}

    		public void componentHidden(ComponentEvent e) {}

    		public void componentMoved(ComponentEvent e) {}

    		public void componentShown(ComponentEvent e) {}
    	});
	}
	/*
	public void redraw() {
		
	}
	*/
	public void paintComponent(Graphics g) {
		
		boardHeight = getHeight();
		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
		System.out.println("Boardheight: " + boardHeight);
		System.out.println("HexagonSide: " + hexagonSide);
		System.out.println("WidthMargin: " + widthMargin);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		super.paintComponent(g2);
		
		//draw grid
		for (int x = 1; x <= 3; x++) {
			drawHex(tiles[x][1],g2);
		}
		
		for (int x = 1; x <= 4; x++) {
			drawHex(tiles[x][2],g2);
		}
		
		for (int x = 1; x <= 5; x++) {
			drawHex(tiles[x][3],g2);
		}
		
		for (int x = 2; x <= 5; x++) {
			drawHex(tiles[x][4],g2);
		}
		
		for (int x = 3; x <= 5; x++) {
			drawHex(tiles[x][5],g2);
		}
		
		//Draw roads
		for (int i = 0; i < roads.length; i++){
			for (int k = 0; k < roads[0].length; k++){
				for (int o = 0; o < roads[0][0].length; o++){
					drawRoad(roads[i][k][o], g2);
				}
			}
		}

		//Draw structures
		for (int i = 0; i < structures.length; i++){
			for (int k = 0; k < structures[0].length; k++){
				for (int o = 0; o < structures[0][0].length; o++){
					drawStructure(structures[i][k][o], g2);
				}
			}
		}
		
		System.out.println("Painted");
		//fill in hexes?
		
	}
	
	public Polygon makeHex(PxLocation center) {
		int xCenter = center.getX();
		int yCenter = center.getY();
		
		
		Polygon output = new Polygon();
		output.addPoint(xCenter + 1, yCenter + hexagonSide + 1);
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1);
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1);
		output.addPoint(xCenter + 1, yCenter - hexagonSide - 1);
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1);
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1);
		
		return output;
	}
	/*
	public Polygon makeRoad(PxLocation center, int orientation) {
		int x = center.getX();
		int y = center.getY();
		int height = hexagonSide / 15;
		Polygon output = new Polygon();
		if (orientation == 0) {
			
		}
		
		
		return output;
	}
	*/
	public void drawHex(Tile tile, Graphics2D g2) {
		int x = tile.getLocation().getXCoord();
		int y = tile.getLocation().getYCoord();
		Polygon poly = makeHex(findCenter(x,y));
		String type = tile.getType();
		switch (type) {
			case "DESERT":
				g2.setColor(Color.MAGENTA);
				break;
			case "BRICK":
				g2.setColor(Color.RED);
				break;
			case "WOOL":
				g2.setColor(Color.WHITE);
				break;
			case "LUMBER":
				g2.setColor(Color.GREEN);
				break;
			case "GRAIN":
				g2.setColor(Color.YELLOW);
				break;
			case "ORE":
				g2.setColor(Color.GRAY);
				break;
			default:
				g2.setColor(Color.BLACK);
				break;
		}
				
		//g2.setColor(Color.MAGENTA); //TODO add colors for tiles
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}
	
	public void drawRoad(Road r, Graphics2D g2) {
		Player player = r.getOwner();
		if (player == null) {
			return;
		}
		//System.out.println("Painted Road");
		
		AffineTransform transformer = new AffineTransform();
		//Polygon poly = new Polygon();
		Graphics2D g2c = (Graphics2D) g2.create();
		
		PxLocation tileCenter = findCenter(r.getLocation().getXCoord(), r.getLocation().getYCoord());
		int y = tileCenter.getY();
		int x = tileCenter.getX();
		int o = r.getLocation().getOrientation();
		int height = hexagonSide / 10;
		Rectangle rect = new Rectangle(x,y, (int) (0.8 * hexagonSide), height);
		//System.out.println(y);
		//System.out.println(x);
		//System.out.println(o);
		if (o == 0){
			transformer.rotate(Math.toRadians(150), x, y);
			transformer.translate(-(int)(0.45 * hexagonSide), (int)(0.80 * hexagonSide));
		}
		else if (o == 1) {
			transformer.rotate(Math.toRadians(30), x, y);
			transformer.translate(-(int)(0.35 * hexagonSide), -(int)(0.90 * hexagonSide));
		}
		else if (o == 2) {
			//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
			transformer.rotate(Math.toRadians(90), x, y);
			transformer.translate(-(int)(0.4 * hexagonSide),-(int)(0.92 * hexagonSide));
		}
		
		g2c.transform(transformer);
		g2c.setColor(player.getColor());
		g2c.fill(rect);
		g2c.setColor(Color.BLACK);
		g2c.draw(rect);
	}
	
	public void drawStructure(Structure s, Graphics2D g2) {
		Player player = s.getOwner();
		if (player == null) {
			return;
		}
		
		Shape shape;
		PxLocation tileCenter = findCenter(s.getLocation().getXCoord(), s.getLocation().getYCoord());
		int y = tileCenter.getY();
		int x = tileCenter.getX();
		//System.out.println(y);
		if (s.getLocation().getOrientation() == 0) {
			y -= hexagonSide;
		}
		else if (s.getLocation().getOrientation() == 1) {
			y += hexagonSide;
		}
		
		//System.out.println(y);
		if (s.getType() == 0) {
			shape = new Rectangle(x - 10, y - 10, 20, 20);      //TODO make sizes relative?
		}
		else {
			shape = new Ellipse2D.Double(x - 10, y - 10, 20, 20);
		}
		
		//System.out.println(x);
		//System.out.println(y);
		g2.setColor(player.getColor());
		g2.fill(shape);
		g2.setColor(Color.BLACK);
		g2.draw(shape);
		
	}
	
	public PxLocation findCenter(int x, int y){
		int xCenter = widthMargin + (int) (3 * hexagonSide * sqrt3div2)
				+ (int) ((x - 1) * 2 * hexagonSide * sqrt3div2)
				- (int) ((y - 1) * hexagonSide * sqrt3div2);
		int yCenter = boardHeight - (heightMargin + hexagonSide
				+ (int) ((y - 1) * hexagonSide * 1.5));
		
		return new PxLocation(xCenter,yCenter);
	}
	
	
	
	class PxLocation{
		private int x,
					y;
		public PxLocation(int xx, int yy){
			x = xx;
			y = yy;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
	}
}


