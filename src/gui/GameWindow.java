package gui;

import game.*;

import java.awt.Container;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GameWindow{
		
	ArrayList<Player> players = new ArrayList<Player>();
	CatanBoard board;
	private Player thisPlayer;
	
	final static int SCRSIZE = 500; //TODO specify
	
	
	private GameWindow() {
		board = new CatanBoard();
		createAndShowGUI();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
				public void run() {
				new GameWindow();
				}
				});
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Catan");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = frame.getContentPane();
		content.add(board);
		
		frame.setSize(SCRSIZE, SCRSIZE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
