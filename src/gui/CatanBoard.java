package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import board.*;
import game.*;


public class CatanBoard extends JPanel{

	private int state = 0;
		//0 = none
		//1 = choosing tile
		//2 = choosing settlement
		//3 = choosing road
		//4 = choosing city
		//5 = choosing setup settlements
	private Game game;
	private int boardHeight;
	private int hexagonSide;
	private int heightMargin = 100;
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	private final int structSize = 12;
	private final int roadSize = 20;

	private ArrayList<Player> players;
	private Tile[][] tiles;
	private Road[][][] roads;
	private Structure[][][] structures;

	//private Graphics g;

	private int index;
	private boolean capitol = false;
	
	
	
	public CatanBoard(ArrayList<Player> players) {

		game = new Game(players);

		this.players = players;
		tiles = game.getBoard().getTiles();
		roads = game.getBoard().getRoads();
		structures = game.getBoard().getStructures();

//		game.getBoard().placeStructureNoRoad(new VertexLocation(3,3,0), players.get(0));
//		game.getBoard().placeStructureNoRoad(new VertexLocation(3,3,1), players.get(3));
//		game.getBoard().placeStructureNoRoad(new VertexLocation(4,2,1), players.get(1));
//		game.getBoard().placeStructureNoRoad(new VertexLocation(5,3,0), players.get(2));
//		game.getBoard().placeStructureNoRoad(new VertexLocation(1,1,0), players.get(3));
//		game.getBoard().placeRoad(new EdgeLocation(5,3,0), players.get(1));
//		game.getBoard().placeRoad(new EdgeLocation(5,3,1), players.get(1));
//		game.getBoard().placeRoad(new EdgeLocation(5,3,2), players.get(1));
//		game.getBoard().placeRoad(new EdgeLocation(3,3,0), players.get(0));
//		game.getBoard().placeRoad(new EdgeLocation(3,3,1), players.get(0));
//		game.getBoard().placeRoad(new EdgeLocation(3,3,2), players.get(0));
//		structures[4][2][1].setType(1);

		setBackground(new Color(164,200,218)); //TODO add background

//		boardHeight = getHeight();
//		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
//		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
//		System.out.println("Boardheight: " + boardHeight);
//		System.out.println("HexagonSide: " + hexagonSide);
//		System.out.println("WidthMargin: " + widthMargin);

		this.addComponentListener(new ComponentListener() {

    		public void componentResized(ComponentEvent e) {
    		//	System.out.println(e.getComponent().getSize());
    			boardHeight = getHeight();
    			hexagonSide = (boardHeight - 2 * heightMargin) / 8;
    			widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
    			//System.out.println("Boardheight: " + boardHeight);
    			//System.out.println("HexagonSide: " + hexagonSide);
    			//System.out.println("WidthMargin: " + widthMargin);
    		}

    		public void componentHidden(ComponentEvent e) {}

    		public void componentMoved(ComponentEvent e) {}

    		public void componentShown(ComponentEvent e) {}
    	});

		MouseListener m = new AMouseListener();
		addMouseListener(m);
		//addMouseMotionListener((MouseMotionListener) m);
	}

	public void paintComponent(Graphics g) {

		boardHeight = getHeight();
		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
		widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
		//System.out.println("Boardheight: " + boardHeight);
		//System.out.println("HexagonSide: " + hexagonSide);
		//System.out.println("WidthMargin: " + widthMargin);

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

		//draw numbers
		for (int x = 1; x <= 3; x++) {
			drawNumber(tiles[x][1],g2);
		}

		for (int x = 1; x <= 4; x++) {
			drawNumber(tiles[x][2],g2);
		}

		for (int x = 1; x <= 5; x++) {
			drawNumber(tiles[x][3],g2);
		}

		for (int x = 2; x <= 5; x++) {
			drawNumber(tiles[x][4],g2);
		}

		for (int x = 3; x <= 5; x++) {
			drawNumber(tiles[x][5],g2);
		}

		//draw robber
		for (int x = 1; x <= 3; x++) {
			drawRobber(tiles[x][1],g2);
		}

		for (int x = 1; x <= 4; x++) {
			drawRobber(tiles[x][2],g2);
		}

		for (int x = 1; x <= 5; x++) {
			drawRobber(tiles[x][3],g2);
		}

		for (int x = 2; x <= 5; x++) {
			drawRobber(tiles[x][4],g2);
		}

		for (int x = 3; x <= 5; x++) {
			drawRobber(tiles[x][5],g2);
		}

		//Draw roads
		for (int i = 0; i < roads.length; i++){
			for (int k = 0; k < roads[0].length; k++){
				for (int o = 0; o < roads[0][0].length; o++){
					drawRoad(roads[i][k][o], false, g2);
				}
			}
		}

		//Draw structures
		for (int i = 0; i < structures.length; i++){
			for (int k = 0; k < structures[0].length; k++){
				for (int o = 0; o < structures[0][0].length; o++){
					drawStructure(structures[i][k][o], false, g2);
				}
			}
		}

		//Highlights
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p,this);

		if (state == 1) {
			Location loc = pxToTile(p);
			//System.out.println("Mouse on screen");
			//System.out.println(p.getX());
			//System.out.println(p.getY());
			if (loc != null) {
				highlightTile(loc, g2);

			}
		}
		else if (state == 2 || state == 4 || state == 5) {
			VertexLocation loc = pxToStructure(p);
			//System.out.println("Blah");
			//System.out.println(p.getX());
			//System.out.println(p.getY());
			//System.out.println(loc);
			if (loc != null) {
				//System.out.println(loc.getXCoord());
				//System.out.println(loc.getYCoord());
				//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
				drawStructure(structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()], true, g2);
			}
		}
		else if (state == 3) {
			EdgeLocation loc = pxToRoad(p);
			//System.out.println("Blah");
			//System.out.println(p.getX());
			//System.out.println(p.getY());
			//System.out.println(loc);
			if (loc != null) {
				//System.out.println(loc.getXCoord());
				//System.out.println(loc.getYCoord());
				//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
				drawRoad(roads[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()], true, g2);
			}
		}
		//System.out.println("Painted");
		
		labelPorts(g2);
	}

	public void setState(int newState) {
		state = newState;
	}
	
	public int getState() {
		return state;
	}

	public void labelPorts(Graphics2D g2) {
		Graphics2D g2c = (Graphics2D) g2.create();

		//Sheep 2:1
		AffineTransform transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 4.2), heightMargin - (int)(hexagonSide * 0.2));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		AffineTransform rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("Sheep 2:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//General 3:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 7.0), heightMargin + (int)(hexagonSide * 1.5));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("General 3:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//General 3:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 8.8), heightMargin + (int)(hexagonSide * 3.2));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(90));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("General 3:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//Brick 2:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 7.0), heightMargin + (int)(hexagonSide * 7.0));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(-30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("Brick 2:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//Lumber 2:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 4.2), heightMargin + (int)(hexagonSide * 8.5));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(-30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("Lumber 2:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//General 3:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 1.3), heightMargin + (int)(hexagonSide * 7.8));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("General 3:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//Grain 2:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 0.7), heightMargin + (int)(hexagonSide * 6.5));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(270));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("Grain 2:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//Ore 2:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 0.7), heightMargin + (int)(hexagonSide * 3.0));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(270));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("Ore 2:1",0, 0);
		g2c = (Graphics2D) g2.create();

		//General 3:1
		transformer = new AffineTransform();
		transformer.translate(widthMargin + (int)(hexagonSide * 1.2), heightMargin + (int)(hexagonSide * 0.7));
		//transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
		g2c.transform(transformer);
		rotate = new AffineTransform();
		rotate.rotate(Math.toRadians(-30));
		g2c.transform(rotate);
		g2c.setColor(Color.BLACK);
		g2c.drawString("General 3:1",0, 0);
		g2c = (Graphics2D) g2.create();

	}

	public Polygon makeHex(Point center) {
		int xCenter = (int) center.getX();
		int yCenter = (int) center.getY();


		Polygon output = new Polygon();
		output.addPoint(xCenter + 1, yCenter + hexagonSide + 1);
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1);
		output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1);
		output.addPoint(xCenter + 1, yCenter - hexagonSide - 1);
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1);
		output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1);

		return output;
	}

	public void drawHex(Tile tile, Graphics2D g2) {
		int x = tile.getLocation().getXCoord();
		int y = tile.getLocation().getYCoord();
		Polygon poly = makeHex(findCenter(x,y));
		String type = tile.getType();
		switch (type) {
			case "DESERT":
				g2.setColor(new Color(0xFF, 0xFF, 0xA9));
				break;
			case "BRICK":
				g2.setColor(new Color(0xAD, 0x33, 0x33));
				break;
			case "WOOL":
				g2.setColor(Color.GREEN);
				break;
			case "LUMBER":
				g2.setColor(new Color(0x99, 0x66, 0x33));
				break;
			case "GRAIN":
				g2.setColor(Color.YELLOW);
				break;
			case "ORE":
				g2.setColor(Color.LIGHT_GRAY);
				break;
			default:
				g2.setColor(Color.WHITE);
				break;
		}
		
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}

	public void highlightTile(Location loc, Graphics2D g2) {
		//System.out.println("Highlighted");
		int x = loc.getXCoord();
		int y = loc.getYCoord();
		if (tiles[x][y].hasRobber()) {
			return;
		}
		Point p = findCenter(x,y);
		Shape shape = new Ellipse2D.Double((int)p.getX() - 25, (int)p.getY() - 25, 50, 50);

		g2.setColor(Color.WHITE);
		g2.fill(shape);
		g2.draw(shape);
	}
	
	public void drawNumber(Tile tile, Graphics2D g2) {
		if (tile.getNumber() == 0) {
			return;
		}
		int x = tile.getLocation().getXCoord();
		int y = tile.getLocation().getYCoord();
		Point p = findCenter(x,y);
		
		g2.setColor(Color.BLACK);
		if (tile.getNumber() == 6 || tile.getNumber() == 8)
			g2.setColor(Color.RED);
		//System.out.println("" + tile.getNumber());
		g2.drawString("" + tile.getNumber(), (int)p.getX() - 5, (int)p.getY() + 5);
	}
	
	public void drawRobber(Tile tile, Graphics2D g2) {
		if (!tile.hasRobber()) {
			return;
		}
		int x = tile.getLocation().getXCoord();
		int y = tile.getLocation().getYCoord();
		Point p = findCenter(x,y);
		
		Shape shape = new Ellipse2D.Double((int)p.getX() - 25, (int)p.getY() - 25, 50, 50);

		g2.setColor(Color.MAGENTA);
		g2.fill(shape);
		g2.draw(shape);
	}

	public void drawRoad(Road r, boolean highlighted, Graphics2D g2) {
		Player player = r.getOwner();
		Graphics2D g2c = (Graphics2D) g2.create();
		if (player == null) {
			if (highlighted)
				g2c.setColor(Color.WHITE);
			else
			 return;
		}
		else {
			if (highlighted)
				return;
			else
				g2c.setColor(player.getColor());
		}
		//System.out.println("Painted Road");

		AffineTransform transformer = new AffineTransform();
		//Polygon poly = new Polygon();

		Point tileCenter = findCenter(r.getLocation().getXCoord(), r.getLocation().getYCoord());
		int y = (int) tileCenter.getY();
		int x = (int) tileCenter.getX();
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
		g2c.fill(rect);
		g2c.setColor(Color.BLACK);
		g2c.draw(rect);
	}

	public void drawStructure(Structure s, boolean highlighted, Graphics2D g2) {
		Player player = s.getOwner();
		if (state == 2 || state == 5) {
			if (player == null) {
				if (highlighted)
					g2.setColor(Color.WHITE);
				else
					return;
			}
			else {
				if (highlighted)
					return;
				else
					g2.setColor(player.getColor());
			}
		}
		else if (state == 4) {
			if (player == null)
				return;
			else if (player == GameRunner.getCurrentPlayer()) {
				if (highlighted){
					if (s.getType() == 0)
						g2.setColor(Color.WHITE);
					else
						g2.setColor(player.getColor());
				}
				else
					g2.setColor(player.getColor());
			}
			else
				g2.setColor(player.getColor());
		}
		else {
			if (player == null)
				return;
			else
				g2.setColor(player.getColor());
		}
		
		Shape shape;
		Point tileCenter = findCenter(s.getLocation().getXCoord(), s.getLocation().getYCoord());
		int y = (int) tileCenter.getY();
		int x = (int) tileCenter.getX();
		//System.out.println(y);
		if (s.getLocation().getOrientation() == 0) {
			y -= hexagonSide;
		}
		else if (s.getLocation().getOrientation() == 1) {
			y += hexagonSide;
		}

		//System.out.println(y);
		if (s.getType() == 0) {
			shape = new Rectangle(x - structSize, y - structSize, 2*structSize, 2*structSize);
		}
		else {
			shape = new Ellipse2D.Double(x - structSize, y - structSize, 2*structSize, 2*structSize);
		}

		//System.out.println(x);
		//System.out.println(y);

		g2.fill(shape);
		g2.setColor(Color.BLACK);
		g2.draw(shape);

	}

	public Point findCenter(int x, int y){
		int xCenter = widthMargin + (int) (3 * hexagonSide * sqrt3div2)
				+ (int) ((x - 1) * 2 * hexagonSide * sqrt3div2)
				- (int) ((y - 1) * hexagonSide * sqrt3div2);
		int yCenter = boardHeight - (heightMargin + hexagonSide
				+ (int) ((y - 1) * hexagonSide * 1.5));

		return new Point(xCenter,yCenter);
	}

	public Location pxToTile(Point p) {
		double x = p.getX();
		double y = p.getY();

		int xCoord = 0,
			yCoord = 0;
//
//		// Out of bounds
//		if (x < widthMargin || x > widthMargin + 2 * 5 * sqrt3div2 || y < heightMargin || y > heightMargin + 8 * hexagonSide) {
//			System.out.println("this null");
//			return null;
//		}
			
		// first horizontal band
		if (heightMargin + hexagonSide / 2 < y && y < heightMargin + 3 * hexagonSide / 2) {
			if (x < widthMargin + hexagonSide * 2 * sqrt3div2 || x > widthMargin + 4 * hexagonSide * 2 * sqrt3div2) {
				//System.out.println("no this null");
				return null;
			}
			yCoord = 5;
			if (x > widthMargin + 2 * hexagonSide * sqrt3div2 && x < widthMargin + 2 * (2 * hexagonSide * sqrt3div2))
				xCoord = 3;
			else if (x > widthMargin + 2 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 3 * (2 * hexagonSide * sqrt3div2))
				xCoord = 4;
			else if (x > widthMargin + 3 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 4 * (2 * hexagonSide * sqrt3div2))
				xCoord = 5;
		}
		// third horizontal band
		else if (heightMargin + 7 * hexagonSide / 2 < y && y < heightMargin + 9 * hexagonSide / 2) {
			yCoord = 3;
			if (widthMargin < x && x < widthMargin + 2 * hexagonSide * sqrt3div2)
				xCoord = 1;
			else if (x > widthMargin + 2 * hexagonSide * sqrt3div2 && x < widthMargin + 4 * hexagonSide * sqrt3div2)
				xCoord = 2;
			else if (x > widthMargin + 4 * hexagonSide * sqrt3div2 && x < widthMargin + 6 * hexagonSide * sqrt3div2)
				xCoord = 3;
			else if (x > widthMargin + 6 * hexagonSide * sqrt3div2 && x < widthMargin + 8 * hexagonSide * sqrt3div2)
				xCoord = 4;
			else if (x > widthMargin + 8 * hexagonSide * sqrt3div2 && x < widthMargin + 10 * hexagonSide * sqrt3div2)
				xCoord = 5;
		}
		// fifth horizontal band
		else if (heightMargin + 13 * hexagonSide / 2 < y && y < heightMargin + 15 * hexagonSide / 2) {
			yCoord = 1;
			if (x > widthMargin + 2 * hexagonSide * sqrt3div2 && x < widthMargin + 2 * (2 * hexagonSide * sqrt3div2))
				xCoord = 1;
			else if (x > widthMargin + 2 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 3 * (2 * hexagonSide * sqrt3div2))
				xCoord = 2;
			else if (x > widthMargin + 3 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 4 * (2 * hexagonSide * sqrt3div2))
				xCoord = 3;
			else {
				//System.out.println("You guys got it all wrong, it's this null");
				return null;
			}
		}


		// second horizontal band
		if (heightMargin + 2 * hexagonSide < y && y < heightMargin + 3 * hexagonSide) {
			yCoord = 4;
			if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
				xCoord = 2;
			else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
				xCoord = 3;
			else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
				xCoord = 4;
			else if (widthMargin + hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
				xCoord = 5;
			else
				return null;
		}
		// fourth horizontal band
		else if (heightMargin + 5 * hexagonSide < y && y < heightMargin + 6 * hexagonSide) {
			yCoord = 2;
			if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
				xCoord = 1;
			else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
				xCoord = 2;
			else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
				xCoord = 3;
			else if (widthMargin + hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
				xCoord = 4;
			else
				return null;
		}

		if (xCoord == 0 || yCoord == 0) {
			//System.out.println("I AM THE TRUE NULL");
			return null;
		}

		return new Location(xCoord, yCoord);
	}
	/*
	private int boardHeight;
	private int hexagonSide;
	private int heightMargin = 100;
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	 */

	public VertexLocation pxToStructure(Point p) {
		double x = p.getX();
		double y = p.getY();

		int xCoord = 0,
			yCoord = 0,
			orient = 1;

		// Columns have if else preceding down each structure in the column

		// first column
		if (widthMargin - structSize < x && x < widthMargin + structSize) {
			if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 1;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 0;
				yCoord = 2;
				orient = 0;
			}
		}
		// second column
		else if (widthMargin + sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + sqrt3div2 * hexagonSide + structSize) {
			//System.out.println("Column 2");
			if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 5;
				orient = 1;
				System.out.println("2, 5, 1");
			}
			else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 0;
				yCoord = 1;
				orient = 0;
			}
		}
		// third column
		if (widthMargin + 2 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 2 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
				xCoord = 2;
				yCoord = 4;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 2;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 1;
				yCoord = 2;
				orient = 0;
			}
			else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
				xCoord = 1;
				yCoord = 2;
				orient = 1;
			}
			else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
				xCoord = 0;
				yCoord = 0;
				orient = 0;
			}
		}
		// fourth column
		if (widthMargin + 3 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 3 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin - structSize < y && y < heightMargin + structSize) {
				xCoord = 3;
				yCoord = 5;
				orient = 0;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 1;
				orient = 1;
			}
		}
		// fifth column
		if (widthMargin + 4 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 4 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
				xCoord = 4;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 4;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 2;
				yCoord = 2;
				orient = 0;
			}
			else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
				xCoord = 2;
				yCoord = 2;
				orient = 1;
			}
			else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
				xCoord = 1;
				yCoord = 0;
				orient = 0;
			}
		}
		// sixth column
		if (widthMargin + 5 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 5 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin - structSize < y && y < heightMargin + structSize) {
				xCoord = 4;
				yCoord = 5;
				orient = 0;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 1;
				orient = 1;
			}
		}
		// seventh column
		if (widthMargin + 6 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 6 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
				xCoord = 5;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
				xCoord = 4;
				yCoord = 4;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 4;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 2;
				orient = 0;
			}
			else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 2;
				orient = 1;
			}
			else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
				xCoord = 2;
				yCoord = 0;
				orient = 0;
			}
		}
		// eighth column
		if (widthMargin + 7 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 7 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin - structSize < y && y < heightMargin + structSize) {
				xCoord = 5;
				yCoord = 5;
				orient = 0;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 1;
				orient = 1;
			}
		}
		// ninth column
		if (widthMargin + 8 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 8 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
				xCoord = 6;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
				xCoord = 5;
				yCoord = 4;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 5;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 4;
				yCoord = 2;
				orient = 0;
			}
			else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
				xCoord = 4;
				yCoord = 2;
				orient = 1;
			}
			else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
				xCoord = 3;
				yCoord = 0;
				orient = 0;
			}
		}
		// tenth column
		else if (widthMargin + 9 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 9 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 6;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 1;
				orient = 0;
			}
		}
		// eleventh column
		if (widthMargin + 10 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 10 * sqrt3div2 * hexagonSide + structSize) {
			if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
				xCoord = 6;
				yCoord = 4;
				orient = 1;
			}
			else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
				xCoord = 5;
				yCoord = 2;
				orient = 0;
			}
		}


		if (xCoord == 0 && yCoord == 0 && orient == 1) {
			return null;
		}

		return new VertexLocation(xCoord, yCoord, orient);
	}

	public EdgeLocation pxToRoad(Point p) {
		int x = (int) p.getX();
		int y = boardHeight - heightMargin - (int) p.getY();
		EdgeLocation output = null;

		if (y >= 0 && y < (int)(hexagonSide * 0.5)) {
			x -= widthMargin;
			x -= 2 * (int)(hexagonSide * sqrt3div2);
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 5) {
				output = null;
			}
			else {
				output = new EdgeLocation((tag + 1) / 2, 0, (tag + 1) % 2);
			}
		}
		else if (y >= (int)(hexagonSide * 0.5)  && y < (int)(hexagonSide * 1.5)) {
			x -= widthMargin;
			x -= (int)(hexagonSide * sqrt3div2);
			int tag = x / (2 * (int)(hexagonSide * sqrt3div2));
			//System.out.println(tag);
			if (tag < 0 || tag > 3) {
				output = null;
			}
			else {
				if (x >= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) - roadSize &&
						x <= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) + roadSize) {
					output = new EdgeLocation(tag, 1, 2);
				}
				else {
					output = null;
				}
			}
		}
		else if (y >= (int)(hexagonSide * 1.5)  && y < (int)(hexagonSide * 2.0)) {
			x -= widthMargin;
			x -= (int)(hexagonSide * sqrt3div2);
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 7) {
				output = null;
			}
			else {
				output = new EdgeLocation((tag + 1) / 2, 1, (tag + 1) % 2);
			}
		}
		else if (y >= (int)(hexagonSide * 2.0)  && y < (int)(hexagonSide * 3.0)) {
			x -= widthMargin;
			int tag = x / (2 * (int)(hexagonSide * sqrt3div2));
			if (tag < 0 || tag > 4) {
				output = null;
			}
			else {
				if (x >= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) - roadSize &&
						x <= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) + roadSize) {
					output = new EdgeLocation(tag, 2, 2);
				}
				else {
					output = null;
				}
			}
		}
		else if (y >= (int)(hexagonSide * 3.0)  && y < (int)(hexagonSide * 3.5)) {
			x -= widthMargin;
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 9) {
				output = null;
			}
			else {
				output = new EdgeLocation((tag + 1) / 2, 2, (tag + 1) % 2);
			}
		}
		else if (y >= (int)(hexagonSide * 3.5)  && y < (int)(hexagonSide * 4.5)) {
			x -= widthMargin;
			x += (int)(hexagonSide * sqrt3div2);
			int tag = x / (2 * (int)(hexagonSide * sqrt3div2));
			if (tag < 0 || tag > 5) {
				output = null;
			}
			else {
				if (x >= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) - roadSize &&
						x <= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) + roadSize) {
					output = new EdgeLocation(tag, 3, 2);
				}
				else {
					output = null;
				}
			}
		}
		else if (y >= (int)(hexagonSide * 4.5)  && y < (int)(hexagonSide * 5.0)) {
			x -= widthMargin;
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 9) {
				output = null;
			}
			else {
				output = new EdgeLocation(tag / 2 + 1, 3, tag % 2);
			}
		}
		else if (y >= (int)(hexagonSide * 5.0)  && y < (int)(hexagonSide * 6.0)) {
			x -= widthMargin;
			int tag = x / (2 * (int)(hexagonSide * sqrt3div2));
			if (tag < 0 || tag > 4) {
				output = null;
			}
			else {
				if (x >= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) - roadSize &&
						x <= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) + roadSize) {
					output = new EdgeLocation(tag + 1, 4, 2);
				}
				else {
					output = null;
				}
			}
		}
		else if (y >= (int)(hexagonSide * 6.0)  && y < (int)(hexagonSide * 6.5)) {
			x -= widthMargin;
			x -= (int)(hexagonSide * sqrt3div2);
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 7) {
				output = null;
			}
			else {
				output = new EdgeLocation(tag / 2 + 2, 4, tag % 2);
			}
		}
		else if (y >= (int)(hexagonSide * 6.5)  && y < (int)(hexagonSide * 7.5)) {
			x -= widthMargin;
			x -= (int)(hexagonSide * sqrt3div2);
			int tag = x / (2 * (int)(hexagonSide * sqrt3div2));
			if (tag < 0 || tag > 3) {
				output = null;
			}
			else {
				if (x >= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) - roadSize &&
						x <= (((tag * 2) + 1) * (int)(hexagonSide * sqrt3div2)) + roadSize) {
					output = new EdgeLocation(tag + 2, 5, 2);
				}
				else {
					output = null;
				}
			}
		}
		else if (y >= (int)(hexagonSide * 7.5)  && y < (int)(hexagonSide * 8.0)) {
			x -= widthMargin;
			x -= 2 * (int)(hexagonSide * sqrt3div2);
			int tag = x / (int)(hexagonSide * sqrt3div2);
			if (tag < 0 || tag > 5) {
				output = null;
			}
			else {
				output = new EdgeLocation(tag / 2 + 3, 5, tag % 2);
			}
		}
		else {
			output = null;
		}

		return output;
	}

	class AMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			//System.out.println("Mouse Clicked");

			Point p = new Point(e.getX(), e.getY());
			if (state == 1) {
				if (p != null){
					Location loc = pxToTile(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					//System.out.println(loc);
					if (loc != null) {
						//System.out.println(loc.getXCoord());
						//System.out.println(loc.getYCoord());
						//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
						//tiles[loc.getXCoord()][loc.getYCoord()]
						if (game.getBoard().moveRobber(loc)) {
							index--;
						}
						if (index == 0) {
							state = 0;
						}
					}
				}
			}
			else if (state == 2) {
				if (p != null){
					VertexLocation loc = pxToStructure(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					//System.out.println(loc);
					if (loc != null) {
						//System.out.println(loc.getXCoord());
						//System.out.println(loc.getYCoord());
						//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
						if (game.getBoard().placeStructure(loc, GameRunner.getCurrentPlayer())) {
							index--;
						}
						if (index == 0) {
							state = 0;
						}
					}
				}
			}
			else if (state == 3) {
				if (p != null){
					EdgeLocation loc = pxToRoad(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					//System.out.println(loc);
					if (loc != null) {
						//System.out.println(loc.getXCoord());
						//System.out.println(loc.getYCoord());
						//System.out.println(loc.getOrientation());
						//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
						if (game.getBoard().placeRoad(loc, GameRunner.getCurrentPlayer())) {
							index--;
						}
						if (index == 0) {
							state = 0;
						}
					}
				}
			}
			else if (state == 4) {
				if (p != null){
					VertexLocation loc = pxToStructure(p);
					if (loc != null) {
						if (game.getBoard().placeCity(loc, GameRunner.getCurrentPlayer())) {
							index--;
						}
						if (index == 0) {
							state = 0;
						}
					}
				}
			}
			else if (state == 5) {
				if (p != null){
					VertexLocation loc = pxToStructure(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					//System.out.println(loc);
					if (loc != null) {
						//System.out.println(loc.getXCoord());
						//System.out.println(loc.getYCoord());
						//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
						if (game.getBoard().placeStructureNoRoad(loc, GameRunner.getCurrentPlayer())) {
							index--;
							if (capitol) {
								ArrayList<Tile> tiles = getGame().getBoard().getAdjacentTilesStructure(loc);
								for (Tile t : tiles){
									if (t != null) {
										GameRunner.getCurrentPlayer().giveResourceType(t.getType());
									}
								}
							}
							//System.out.println(index);
						}
						if (index == 0) {
							state = 0;
							//System.out.println("state to 0");
						}
					}
				}
			}
			repaint();
		}
	}
	
	public Game getGame() {
		return game;
	}
	
	/**
	 * Puts CatanBoard in placing settlement state
	 * @param s how many to be placed
	 */
	public void placeSettlement(int s) {
		index = s;
		state = 2;
	}
	
	public void placeRoad(int s) {
		index = s;
		state = 3;
	}
	
	public void placeCity(int s) {
		index = s;
		state = 4;
	}
	
	public void placeRobber() {
		index = 1;
		state = 1;
	}
	
	/**
	 * Puts CatanBoard in placing settlement state in setup
	 * @param s how many to be placed
	 */
	public void placeSettlementNoRoad(int s) {
		index = s;
		state = 5;
	}

	/**
	 * Puts CatanBoard in placing settlement state in setup
	 * @param s how many to be placed
	 */
	public void placeCapitol() {
		index = 1;
		state = 5;
		capitol = true;
	}
}
