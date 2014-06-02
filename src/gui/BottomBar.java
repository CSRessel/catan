package gui;

import game.GameRunner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import lib.GraphPaperLayout;

public class BottomBar extends JPanel{
	
	private KComponent brick, wool, ore, grain, lumber;
	
	public final static int INTERVAL = 20;
	private Timer timer;
	
	public BottomBar() {
		setBackground(Color.CYAN);
		
		setLayout(new GraphPaperLayout(new Dimension(5,5)));
		
		brick = new KComponent(new JLabel(""), new Rectangle(0,0,1,1));
		brick.getComponent().setFont(new Font("Arial", 1, 16));
		
		add(brick.getComponent(), brick.getRectangle());
		
		wool = new KComponent(new JLabel(""), new Rectangle(0,1,1,1));
		wool.getComponent().setFont(new Font("Arial", 1, 16));
		
		add(wool.getComponent(), wool.getRectangle());
		
		ore = new KComponent(new JLabel(""), new Rectangle(0,2,1,1));
		ore.getComponent().setFont(new Font("Arial", 1, 16));
		
		add(ore.getComponent(), ore.getRectangle());
		
		grain = new KComponent(new JLabel(""), new Rectangle(0,3,1,1));
		grain.getComponent().setFont(new Font("Arial", 1, 16));
		
		add(grain.getComponent(), grain.getRectangle());
		
		lumber = new KComponent(new JLabel(""), new Rectangle(0,4,1,1));
		lumber.getComponent().setFont(new Font("Arial", 1, 16));
		
		add(lumber.getComponent(), lumber.getRectangle());
		
		timer = new Timer(INTERVAL,
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						((JLabel)brick.getComponent()).setText("Brick: " + GameRunner.currentPlayer.getNumberResourcesType("BRICK"));
						((JLabel)wool.getComponent()).setText("Wool: " + GameRunner.currentPlayer.getNumberResourcesType("WOOL"));
						((JLabel)ore.getComponent()).setText("Ore: " + GameRunner.currentPlayer.getNumberResourcesType("ORE"));
						((JLabel)grain.getComponent()).setText("Grain: " + GameRunner.currentPlayer.getNumberResourcesType("GRAIN"));
						((JLabel)lumber.getComponent()).setText("Lumber: " + GameRunner.currentPlayer.getNumberResourcesType("LUMBER"));
					}
				});
		timer.start();
		
	}
	
	/*
	public void update(Graphics g) {
		((JLabel)brick.getComponent()).setText("Brick: " + GameRunner.currentPlayer.getNumberResourcesType("BRICK"));
		((JLabel)wool.getComponent()).setText("Wool: " + GameRunner.currentPlayer.getNumberResourcesType("WOOL"));
		((JLabel)ore.getComponent()).setText("Ore: " + GameRunner.currentPlayer.getNumberResourcesType("ORE"));
		((JLabel)grain.getComponent()).setText("Grain: " + GameRunner.currentPlayer.getNumberResourcesType("GRAIN"));
		((JLabel)lumber.getComponent()).setText("Lumber: " + GameRunner.currentPlayer.getNumberResourcesType("LUMBER"));
		
		add(brick.getComponent(), brick.getRectangle());
		add(wool.getComponent(), wool.getRectangle());
		add(ore.getComponent(), ore.getRectangle());
		add(grain.getComponent(), grain.getRectangle());
		add(lumber.getComponent(), lumber.getRectangle());
		
	}
	*/
	
	
}
