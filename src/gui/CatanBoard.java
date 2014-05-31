package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import board.*;
import game.*;


public class CatanBoard extends JPanel{
	
	private int state = 2;
		//0 = none
		//1 = choosing tile
		//2 = choosing settlement
		//3 = choosing road
	private Game game;
	private int boardHeight;
	private int hexagonSide;
	private int heightMargin = 100; //TODO define
	private int widthMargin;
	private final double sqrt3div2 = 0.86602540378;
	private final double structSize = 7.0;
							// The radius of the hitbox for settlements (or more accurately one half the width of the square)
	private final int roadSize = 20;
	
	private ArrayList<Player> players;
	private Tile[][] tiles;
	private Road[][][] roads;
	private Structure[][][] structures;
	
	private Graphics g;
	
	
	public CatanBoard() {
		
		players = new ArrayList<Player>(); //TODO input players
		
		// Your temporary names are much better than mine :P
		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		game = new Game(players);
		
		tiles = game.getBoard().getTiles();
		roads = game.getBoard().getRoads();
		structures = game.getBoard().getStructures(); //TODO fix encapsulation
		
		game.getBoard().placeStructureNoRoad(new VertexLocation(3,3,0), players.get(0));
		game.getBoard().placeStructureNoRoad(new VertexLocation(4,2,1), players.get(0));
		game.getBoard().placeStructureNoRoad(new VertexLocation(5,3,0), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,0), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,1), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(5,3,2), players.get(1));
		game.getBoard().placeRoad(new EdgeLocation(3,3,0), players.get(0));
		game.getBoard().placeRoad(new EdgeLocation(3,3,1), players.get(0));
		game.getBoard().placeRoad(new EdgeLocation(3,3,2), players.get(0));
		structures[4][2][1].setType(1);
		
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
		addMouseMotionListener((MouseMotionListener) m);
	}
	/*
	public void redraw() {
		
	}
	*/
	public void paintComponent(Graphics g) {
		this.g = g;
		
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
		
		//System.out.println("Painted");
		
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
				
		//g2.setColor(Color.MAGENTA); //TODO MAGENTA
		g2.fillPolygon(poly);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(poly);
	}
	
	public void highlightTile(Tile tile, Graphics2D g2) {
		System.out.println("Highlighted");
		int x = tile.getLocation().getXCoord();
		int y = tile.getLocation().getYCoord();
		Point p = findCenter(x,y);
		Shape shape = new Ellipse2D.Double((int)p.getX() - 25, (int)p.getY() - 25, 50, 50);
		
		g2.setColor(Color.WHITE);
		g2.fill(shape);
		g2.draw(shape);
	}
	
	public void drawRoad(Road r, boolean highlighted, Graphics2D g2) {
		Player player = r.getOwner();
		if (player == null) {
			return;
		}
		//System.out.println("Painted Road");
		
		AffineTransform transformer = new AffineTransform();
		//Polygon poly = new Polygon();
		Graphics2D g2c = (Graphics2D) g2.create();
		
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
		//TODO Add highlight
		g2c.setColor(player.getColor());
		g2c.fill(rect);
		g2c.setColor(Color.BLACK);
		g2c.draw(rect);
	}
	
	public void drawStructure(Structure s, boolean highlighted, Graphics2D g2) {
		Player player = s.getOwner();
		if (player == null) {
			return;
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
			shape = new Rectangle(x - 10, y - 10, 20, 20);      //TODO make sizes relative?
		}
		else {
			shape = new Ellipse2D.Double(x - 10, y - 10, 20, 20);
		}
		
		//System.out.println(x);
		//System.out.println(y);
		//TODO add highlight
		g2.setColor(player.getColor());
		if (player == null && highlighted) {
			g2.setColor(Color.WHITE);
		}
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
		
		// Out of bounds
		if (x < widthMargin || x > widthMargin + 2 * 5 * sqrt3div2 || y < heightMargin || y > heightMargin + 8 * hexagonSide)
			return null;
	
		// first horizontal band
		if (heightMargin + hexagonSide / 2 < y && y < heightMargin + 3 * hexagonSide / 2) {
			if (x < widthMargin + hexagonSide * 2 * sqrt3div2 || x > widthMargin + 4 * (hexagonSide * 2 * sqrt3div2))
				return null;
			yCoord = 5;
			if (widthMargin < widthMargin + 2 * hexagonSide + sqrt3div2 && y < widthMargin + 2 * (2 * hexagonSide + sqrt3div2))
				xCoord = 3;
			else if (widthMargin < widthMargin + 2 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 3 * (2 * hexagonSide + sqrt3div2))
				xCoord = 4;
			else if (widthMargin < widthMargin + 3 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 4 * (2 * hexagonSide + sqrt3div2))
				xCoord = 5;
		}
		// third horizontal band
		else if (heightMargin + 7 * hexagonSide / 2 < y && y < heightMargin + 9 * hexagonSide / 2) {
			yCoord = 3;
			if (widthMargin < y && y < widthMargin + 2 * hexagonSide + sqrt3div2)
				xCoord = 1;
			else if (widthMargin < widthMargin + 2 * hexagonSide + sqrt3div2 && y < widthMargin + 2 * (2 * hexagonSide + sqrt3div2))
				xCoord = 2;
			else if (widthMargin < widthMargin + 2 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 3 * (2 * hexagonSide + sqrt3div2))
				xCoord = 3;
			else if (widthMargin < widthMargin + 3 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 4 * (2 * hexagonSide + sqrt3div2))
				xCoord = 4;
			else if (widthMargin < widthMargin + 4 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 5 * (2 * hexagonSide + sqrt3div2))
				xCoord = 5;
		}
		// fifth horizontal band
		else if (heightMargin + 13 * hexagonSide / 2 < y && y < 15 * heightMargin + hexagonSide / 2) {
			yCoord = 1;
			if (widthMargin < widthMargin + 2 * hexagonSide + sqrt3div2 && y < widthMargin + 2 * (2 * hexagonSide + sqrt3div2))
				xCoord = 1;
			else if (widthMargin < widthMargin + 2 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 3 * (2 * hexagonSide + sqrt3div2))
				xCoord = 2;
			else if (widthMargin < widthMargin + 3 * (2 * hexagonSide + sqrt3div2) && y < widthMargin + 4 * (2 * hexagonSide + sqrt3div2))
				xCoord = 3;
			else
				return null;
		}
		

		// second horizontal band
		if (heightMargin + 2 * hexagonSide < y && y < heightMargin + 3 * hexagonSide) {
			y = 4;
			if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
				x = 0;
			else
				return null;
		}
		// fourth horizontal band
		else if (heightMargin + 5 * hexagonSide < y && y < heightMargin + 6 * hexagonSide) {
			y = 2;
			if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
				x = 0;
			else if (widthMargin + hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
				x = 0;
			else
				return null;
		}

		if (x == 0 || y == 0)
			return null;
		
		return new Location(xCoord, yCoord);
	}
	/*
	private int boardHeight;
	private int hexagonSide;
	private int heightMargin = 100; //TODO define
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
			if (heightMargin + 2 * hexagonSide - structSize < x && x < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < x && x < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < x && x < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < x && x < heightMargin + 6 * hexagonSide + structSize) {
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
			if (heightMargin - structSize < x && x < heightMargin + structSize) {
				xCoord = 4;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < x && x < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < x && x < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < x && x < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < x && x < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide - structSize < x && x < heightMargin + 7 * hexagonSide + structSize) {
				xCoord = 1;
				yCoord = 0;
				orient = 0;
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
			if (heightMargin - structSize < x && x < heightMargin + structSize) {
				xCoord = 5;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < x && x < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < x && x < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < x && x < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < x && x < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide - structSize < x && x < heightMargin + 7 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 0;
				orient = 0;
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
			if (heightMargin - structSize < x && x < heightMargin + structSize) {
				xCoord = 6;
				yCoord = 6;
				orient = 1;
			}
			else if (heightMargin + 2 * hexagonSide - structSize < x && x < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < x && x < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < x && x < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 4;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < x && x < heightMargin + 6 * hexagonSide + structSize) {
				xCoord = 3;
				yCoord = 1;
				orient = 0;
			}
			else if (heightMargin + 7 * hexagonSide - structSize < x && x < heightMargin + 7 * hexagonSide + structSize) {
				xCoord = 2;
				yCoord = 0;
				orient = 0;
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
			if (heightMargin + 2 * hexagonSide - structSize < x && x < heightMargin + 2 * hexagonSide + structSize) {
				xCoord = 6;
				yCoord = 5;
				orient = 1;
			}
			else if (heightMargin + 3 * hexagonSide - structSize < x && x < heightMargin + 3 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 3;
				orient = 0;
			}
			else if (heightMargin + 5 * hexagonSide - structSize < x && x < heightMargin + 5 * hexagonSide + structSize) {
				xCoord = 5;
				yCoord = 3;
				orient = 1;
			}
			else if (heightMargin + 6 * hexagonSide - structSize < x && x < heightMargin + 6 * hexagonSide + structSize) {
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
			switch (tag) {
			case 0:
				output = new EdgeLocation(0,0,1); break;
			case 1:
				output = new EdgeLocation(1,0,0); break;
			case 2:
				output = new EdgeLocation(1,0,1); break;
			case 3:
				output = new EdgeLocation(2,0,0); break;
			case 4:
				output = new EdgeLocation(2,0,1); break;
			case 5:
				output = new EdgeLocation(3,0,0); break;
			default:
				output = null; break;
			}
		}
		else if (y >= (int)(hexagonSide * 0.5)  && y < (int)(hexagonSide * 1.5)) {
			x -= widthMargin;
			x -= (int)(hexagonSide * sqrt3div2);
			int tag = x / 2 * (int)(hexagonSide * sqrt3div2);

		} else if (y >= (int)(hexagonSide * 1.5)  && y < (int)(hexagonSide * 2.0)) {

		} else if (y >= (int)(hexagonSide * 2.0)  && y < (int)(hexagonSide * 3.0)) {

		} else if (y >= (int)(hexagonSide * 3.0)  && y < (int)(hexagonSide * 3.5)) {

		} else if (y >= (int)(hexagonSide * 3.5)  && y < (int)(hexagonSide * 4.5)) {

		} else if (y >= (int)(hexagonSide * 4.5)  && y < (int)(hexagonSide * 5.0)) {

		} else if (y >= (int)(hexagonSide * 5.0)  && y < (int)(hexagonSide * 6.0)) {

		} else if (y >= (int)(hexagonSide * 6.0)  && y < (int)(hexagonSide * 6.5)) {

		} else if (y >= (int)(hexagonSide * 6.5)  && y < (int)(hexagonSide * 7.5)) {

		} else if (y >= (int)(hexagonSide * 7.5)  && y < (int)(hexagonSide * 8.0)) {

		} else {
			output = null;
		}

		return output;
	}
	
	class AMouseListener extends MouseAdapter implements MouseMotionListener{
		public void mouseClicked(MouseEvent e) { 
			Graphics2D g2 = (Graphics2D)g;
			System.out.println("Mouse Clicked");
			if (state == 2) {
				Point p = new Point(e.getX(), e.getY());
				if (p != null){
					VertexLocation loc = pxToStructure(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					System.out.println(loc);
					if (loc != null) {
						System.out.println(loc.getXCoord());
						System.out.println(loc.getYCoord());
						//highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2); //TODO set to structures
						structures[loc.getXCoord()][loc.getYCoord()][loc.getOrientation()].setOwner(players.get(0));
					}
				}
			}
			repaint();
		}
		/*
		public void mouseMoved(MouseEvent e) {
			//TODO highlights
			Graphics2D g2 = (Graphics2D)g;
			System.out.println("Mouse Moved");
			
			if (state == 1) {
				Point p = new Point(e.getX(), e.getY());
				if (p != null) {
					Location loc = pxToTile(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					if (loc != null) {
						highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2);
					}
				}
			}
			if (state == 2) {
				Point p = new Point(e.getX(), e.getY());
				if (p != null){
					VertexLocation loc = pxToStructure(p);
					//System.out.println("Mouse on screen");
					//System.out.println(p.getX());
					//System.out.println(p.getY());
					System.out.println(loc);
					if (loc != null) {
						System.out.println(loc.getXCoord());
						System.out.println(loc.getYCoord());
						highlightTile(tiles[loc.getXCoord()][loc.getYCoord()], g2); //TODO set to structures
					}
				}
			}
		}*/
	} 
}
