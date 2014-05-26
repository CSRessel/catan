package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
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
	
	
	private Tile[][] tiles;
	
	public CatanBoard(){
		ArrayList<Player> players = new ArrayList<Player>(); //TODO input players
		players.add(new Player("Superman", Color.BLUE));
		players.add(new Player("Batman", Color.BLACK));
		players.add(new Player("Spiderman", Color.RED));
		game = new Game(players);
		tiles = game.getTiles();
		setBackground(Color.MAGENTA);
		boardHeight = getHeight();
		hexagonSide = (boardHeight - 2 * heightMargin) / 8;
		widthMargin = getWidth() - (int) (10 * hexagonSide * Math.sqrt(3) / 2);
	}
	
	public void redraw(){
		
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		super.paintComponent(g2);
		//draw grid
		
		
		//fill in hexes
		
	}
	
	public Polygon makeHex(int x, int y){
		
	}
}
