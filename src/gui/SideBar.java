package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.GraphPaperLayout;

public class SideBar extends JPanel {
	
	private ComponentList rollPanel 	= new ComponentList();
	private ComponentList mainPanel		= new ComponentList();
	private ComponentList buyPanel		= new ComponentList();
	private ComponentList tradePanel	= new ComponentList();
	
	public SideBar(final GameWindow display) {
		this.setLayout(new GraphPaperLayout(new Dimension(14,24)));
		
		// Current player title (always in sidebar)
		//-------------------------------------------------------------------
		
		JLabel currPlayer = new JLabel("Current Player: " + "TODO");
		currPlayer.setFont(new Font("Arial", 1, 16));
		add(currPlayer, new Rectangle(2,0,10,3));
		
		// Roll panel:
		//-------------------------------------------------------------------
		
		JButton roll = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
				mainPanel();
			}
		});
		roll.setText("roll the dice");
		rollPanel.add(new KComponent(roll, new Rectangle(3,5,8,3)));
		
		// Main panel:
		//-------------------------------------------------------------------
		
		JButton buy = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
				buyPanel();
			}
		});
		buy.setText("buy");
		mainPanel.add(new KComponent(buy, new Rectangle(3,5,8,3)));
		
		JButton trade = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
				tradePanel();
			}
		});
		trade.setText("trade");
		mainPanel.add(new KComponent(trade, new Rectangle(3,10,8,3)));
		
		// Trade panel:
		//-------------------------------------------------------------------

		JLabel tradeText = new JLabel("Trade with...");
		tradePanel.add(new KComponent(tradeText, new Rectangle(4,8,6,2)));
		
		// Trade with players
		JButton tradePlayer = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
				mainPanel();
			}
		});
		tradePlayer.setText("a player");
		tradePanel.add(new KComponent(tradePlayer, new Rectangle(1,10,6,2)));
		
		// Trade with stock
		JButton tradeStock = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
				mainPanel();
			}
		});
		tradeStock.setText("the stock");
		tradePanel.add(new KComponent(tradeStock, new Rectangle(7,10,6,2)));
		
		// Buy panel:
		//-------------------------------------------------------------------
		
		JLabel buyText = new JLabel("Buy a...");
		buyPanel.add(new KComponent(buyText, new Rectangle(4,13,6,2)));
		
		// Buy settlement
		JButton buySettlement = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				mainPanel();
			}
		});
		buySettlement.setText("settlement");
		buyPanel.add(new KComponent(buySettlement, new Rectangle(1,15,6,2)));
		
		// Buy city
		JButton buyCity = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				mainPanel();
			}
		});
		buyCity.setText("city");
		buyPanel.add(new KComponent(buyCity, new Rectangle(7,15,6,2)));
		
		// Buy road
		JButton buyRoad = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				mainPanel();
			}
		});
		buyRoad.setText("road");
		buyPanel.add(new KComponent(buyRoad, new Rectangle(1,17,6,2)));
		
		// Buy devcard
		JButton buyCard = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				mainPanel();
			}
		});
		buyCard.setText("dev card");
		buyPanel.add(new KComponent(buyCard, new Rectangle(7,17,6,2)));
		
		//-------------------------------------------------------------------

		setPanel(rollPanel);
	}
	
	private void setPanel(ComponentList cL) {
		
		for (int i = 1; i < this.getComponentCount(); i++) {
			this.remove(i);
			System.out.println("removed comp");
		}
		
		for (int i = 0; i < cL.size(); i++) {
			this.add(cL.get(i).getComponent(), cL.get(i).getRectangle());
			System.out.println("added comp");
		}
		
		repaint();
		validate();
	}
	
	public void buyPanel() {
		setPanel(buyPanel);
	}
	
	public void tradePanel() {
		setPanel(tradePanel);
	}
	
	public void rollPanel() {
		setPanel(rollPanel);
	}
	
	public void mainPanel() {
		setPanel(mainPanel);
	}
}
