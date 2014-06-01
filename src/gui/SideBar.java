package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lib.GraphPaperLayout;

public class SideBar extends JPanel {
	
	public SideBar(final GameWindow display) {
		this.setLayout(new GraphPaperLayout(new Dimension(5,5)));
		
		JButton testTiles = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				display.getBoard().setState(1);
			}
		});
		testTiles.setText("testTiles");
		
		add(testTiles, new Rectangle(1,1,3,1));
		
		JButton testStructures = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				display.getBoard().setState(2);
			}
		});
		testStructures.setText("testStructures");
		
		add(testStructures, new Rectangle(1,2,3,1));
		
		JButton testRoads = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				display.getBoard().setState(3);
			}
		});
		testRoads.setText("testRoads");
		
		add(testRoads, new Rectangle(1,3,3,1));
	}

}
