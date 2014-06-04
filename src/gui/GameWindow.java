package gui;

import game.*;

import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import lib.GraphPaperLayout;


public class GameWindow {
		
	CatanBoard board;
	BottomBar bottom;
	SideBar side;
	public final static int INTERVAL = 20;
	
	
	final static int SCRSIZE = 1000; //TODO specify
	
	
	public GameWindow(ArrayList<Player> players) {
		board = new CatanBoard(players);
		bottom = new BottomBar();
		
		createAndShowGUI();

		Timer timer = new Timer(INTERVAL,
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						// Refresh the board
						board.repaint(); //TODO fix validate
						bottom.repaint();
					}
				});

		timer.start();
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Catan");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Dimension d = new Dimension(5,6);

		frame.setSize(SCRSIZE + 500, SCRSIZE);
		//frame.setLayout(new GraphPaperLayout(d));
		Container content = frame.getContentPane();
		content.setLayout(new GraphPaperLayout(d));
		//content.add(board);
		content.add(board,new Rectangle(0,0,4,4));
		
		side = new SideBar(this);
		content.add(side,new Rectangle(4,0,1,4));
		
		content.add(bottom,new Rectangle(0,4,5,2));
		
		
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		board.repaint();
	}
	
	public CatanBoard getBoard() {
		return board;
	}
}
