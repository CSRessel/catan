package gui;

import game.Game;
import game.GameRunner;
import game.Player;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import board.DevCard;
import lib.GraphPaperLayout;

public class SideBar extends JPanel {

	private ComponentList rollPanel 		= new ComponentList();
	private ComponentList mainPanel			= new ComponentList();
	private ComponentList buyPanel			= new ComponentList();
	private ComponentList tradePanel		= new ComponentList();
	private ComponentList errorPanel		= new ComponentList();
	private ComponentList devPanel			= new ComponentList();
	private ComponentList monopolyPanel		= new ComponentList();
	private ComponentList yearPanel1		= new ComponentList();
	private ComponentList yearPanel2		= new ComponentList();
	private ComponentList stealPanel		= new ComponentList();
	private ComponentList blankPanel		= new ComponentList();

	private KComponent currentPlayerBox;
	private final GameWindow display;
	private JComboBox<Player> playerStealBox = new JComboBox<Player>();
	private int flag = 0;
		// For tracking where we are in turn; 0 = main panel or roll, 1 = trade panel, 2 = buy panel

	public final static int INTERVAL = 20;
	private Timer timer;

	public SideBar(final GameWindow display) {

		this.display = display;
		this.setLayout(new GraphPaperLayout(new Dimension(14,24)));

		// Current player title (always in sidebar)
		//-------------------------------------------------------------------

		currentPlayerBox = new KComponent(new JLabel(""), new Rectangle(2,0,10,1));
		currentPlayerBox.getComponent().setFont(new Font("Arial", 1, 16));
		setCurrentPlayer(GameRunner.currentPlayer);
		add(currentPlayerBox.getComponent(), currentPlayerBox.getRectangle());

		// Roll panel:
		//-------------------------------------------------------------------

		JButton roll = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int roll = g.roll(GameRunner.currentPlayer);

				if (roll != 7) {
					mainPanel();
				}
				else {
					g.halfCards();

					display.getBoard().placeRobber();
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 1){

									}
									else {
										timer.stop();
										GameRunner.currentPlayer.incrementNumbKnights();
										stealPanel();
									}
								}
							});
					timer.start();
				}
				JLabel rollNumb = new JLabel("roll value: " + roll);
				rollNumb.setFont(new Font("Arial", 1, 16));
				add(rollNumb, new Rectangle(2,2,10,1));
				repaint();
				validate();
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
		mainPanel.add(new KComponent(trade, new Rectangle(3,9,8,3)));

		JButton dev = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				devPanel();
			}
		});
		dev.setText("play development card");
		mainPanel.add(new KComponent(dev, new Rectangle(3,13,8,3)));

		JButton endTurn = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.nextPlayer();
				rollPanel();
			}
		});
		endTurn.setText("end your turn");
		mainPanel.add(new KComponent(endTurn, new Rectangle (3,18,8,3)));

		// Trade panel:
		//-------------------------------------------------------------------

		JLabel tradeText = new JLabel("Trade with...");
		tradePanel.add(new KComponent(tradeText, new Rectangle(4,4,6,2)));

		// Trade with players
		JButton tradePlayer = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				//TODO
				mainPanel();
			}
		});
		tradePlayer.setText("a player");
		tradePanel.add(new KComponent(tradePlayer, new Rectangle(1,6,6,2)));

		// Trade with stock
		JButton tradeStock = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				//TODO
				mainPanel();
			}
		});
		tradeStock.setText("the stock");
		tradePanel.add(new KComponent(tradeStock, new Rectangle(7,6,6,2)));

		// Return to main panel
		JButton returnMain = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				mainPanel();
			}
		});
		returnMain.setText("return to main panel");
		tradePanel.add(new KComponent(returnMain, new Rectangle(3,11,8,2)));

		// Buy panel:
		//-------------------------------------------------------------------

		JLabel buyText = new JLabel("Buy a...");
		buyPanel.add(new KComponent(buyText, new Rectangle(4,4,6,2)));

		// Buy settlement
		JButton buySettlement = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buySettlement(GameRunner.currentPlayer);

				if (bought == 0) {
					display.getBoard().placeSettlement(1);
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 2){

									}
									else {
										buyPanel();
										timer.stop();
									}
								}
							});
					timer.start();
				}
				else if (bought == 1) {
					errorPanel("insufficient resources");
				}
				else if (bought == 2) {
					errorPanel("structure capacity reached");
				}
			}
		});
		buySettlement.setText("settlement");
		buyPanel.add(new KComponent(buySettlement, new Rectangle(1,6,6,2)));

		// Buy city
		JButton buyCity = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyCity(GameRunner.currentPlayer);

				if (bought == 0) {
					display.getBoard().placeCity(1);
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 4){

									}
									else {
										buyPanel();
										timer.stop();
									}
								}
							});
					timer.start();
				}
				else if (bought == 1) {
					errorPanel("insufficient resources");
				}
				else if (bought == 2) {
					errorPanel("structure capacity reached");
				}
			}
		});
		buyCity.setText("city");
		buyPanel.add(new KComponent(buyCity, new Rectangle(7,6,6,2)));

		// Buy road
		JButton buyRoad = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyRoad(GameRunner.currentPlayer);

				if (bought == 0) {
					display.getBoard().placeRoad(1);
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 3){

									}
									else {
										buyPanel();
										timer.stop();
									}
								}
							});
					timer.start();
				}
				else if (bought == 1) {
					errorPanel("insufficient resources");
				}
				else if (bought == 2) {
					errorPanel("structure capacity reached");
				}
			}
		});
		buyRoad.setText("road");
		buyPanel.add(new KComponent(buyRoad, new Rectangle(1,8,6,2)));

		// Buy devcard
		JButton buyCard = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyDevCard(GameRunner.currentPlayer);

				if (bought == 0) {
					DevCard dC = g.getDeck().draw();
					GameRunner.currentPlayer.addDevCard(dC);

					if (dC.getType().equals("Victory Point")) {
						GameRunner.currentPlayer.setVictoryPoints(GameRunner.currentPlayer.getVictoryPoints());
					}

					buyPanel();
				}
				else if (bought == 1) {
					errorPanel("insufficient resources");
				}
			}
		});
		buyCard.setText("dev card");
		buyPanel.add(new KComponent(buyCard, new Rectangle(7,8,6,2)));

		// Return to main panel
		buyPanel.add(new KComponent(returnMain, new Rectangle(3,12,8,2)));

		// Error panel:
		//-------------------------------------------------------------------

		JLabel message = new JLabel("");
		message.setFont(new Font("Arial", 1, 16));
		errorPanel.add(message, new Rectangle(2,4,10,1));

		JButton accept = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				switch(flag) {
				case 0: setPanel(mainPanel); break;
				case 1: setPanel(tradePanel); break;
				case 2: setPanel(buyPanel); break;
				default: setPanel(mainPanel); break;
				}
			}
		});
		accept.setText("continue");
		errorPanel.add(accept, new Rectangle (3,7,9,2));

		// Dev card panel:
		//-------------------------------------------------------------------

		JLabel devCard = new JLabel("Play a...");
		message.setFont(new Font("Arial", 1, 16));
		devPanel.add(devCard, new Rectangle(4,4,6,2));

		// Play a knight card
		JButton knight = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				if (GameRunner.currentPlayer.hasCard("Knight")) {
					GameRunner.currentPlayer.removeCard("Knight");

					display.getBoard().placeRobber();
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 1){

									}
									else {
										timer.stop();
										GameRunner.currentPlayer.incrementNumbKnights();
										//Choose player to steal from (JComboBox)
										
										stealPanel();
									}
								}
							});
					timer.start();
				}
				else {
					errorPanel("you don't own any knight cards");
				}
			}
		});
		knight.setText("knight card");
		devPanel.add(knight, new Rectangle (1,6,6,2));

		// Play a monopoly card
		JButton monopoly = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if (GameRunner.currentPlayer.hasCard("Monopoly")) {
					GameRunner.currentPlayer.removeCard("Monopoly");

					monopolyPanel();
				}
				else {
					errorPanel("you don't own any monopoly cards");
				}
			}
		});
		monopoly.setText("monopoly card");
		devPanel.add(new KComponent(monopoly, new Rectangle(7,6,6,2)));

		// Play a year of plenty card
		JButton year = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if (GameRunner.currentPlayer.hasCard("Year of plenty")) {
					GameRunner.currentPlayer.removeCard("Year of plenty");

					yearPanel1();
				}
				else {
					errorPanel("you don't own any year of plenty cards");
				}
			}
		});
		year.setText("year 'o plenty card");
		devPanel.add(new KComponent(year, new Rectangle(1,8,6,2)));

		// Play a road building card
		JButton road = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				if (GameRunner.currentPlayer.hasCard("Road building")) {
					GameRunner.currentPlayer.removeCard("Road building");

					display.getBoard().placeRoad(1);
					blankPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(display.getBoard().getState() == 3){

									}
									else {
										mainPanel();
										timer.stop();
									}
								}
							});
					timer.start();
				}
				else {
					errorPanel("you don't own any road building cards");
				}
			}
		});
		road.setText("road building card");
		devPanel.add(new KComponent(road, new Rectangle(7,8,6,2)));

		// Return to main panel
		devPanel.add(new KComponent(returnMain, new Rectangle(3,12,8,2)));

		// Steal panel:
		//-------------------------------------------------------------------
		
		playerStealBox.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				 JComboBox<Player> cb = (JComboBox)a.getSource();
			     Player playerSteal = (Player)cb.getSelectedItem();
			     display.getBoard().getGame().takeCard(GameRunner.currentPlayer, playerSteal);
			     mainPanel();
			}
		});
		stealPanel.add(playerStealBox, new Rectangle(3,6,8,2));
		
		
		// Monopoly card panel:
		//-------------------------------------------------------------------

		JButton wool = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				g.takeAll("WOOL", GameRunner.currentPlayer);
				mainPanel();
			}
		});
		wool.setText("wool");
		monopolyPanel.add(wool, new Rectangle(4,6,6,2));

		JButton grain = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				g.takeAll("GRAIN", GameRunner.currentPlayer);
				mainPanel();
			}
		});
		grain.setText("grain");
		monopolyPanel.add(new KComponent(grain, new Rectangle(4,8,6,2)));

		JButton ore = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				g.takeAll("ORE", GameRunner.currentPlayer);
				mainPanel();
			}
		});
		ore.setText("ore");
		monopolyPanel.add(new KComponent(ore, new Rectangle(4,10,6,2)));

		JButton lumber = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				g.takeAll("LUMBER", GameRunner.currentPlayer);
				mainPanel();
			}
		});
		lumber.setText("lumber");
		monopolyPanel.add(new KComponent(lumber, new Rectangle(4,12,6,2)));

		JButton brick = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				g.takeAll("BRICK", GameRunner.currentPlayer);
				mainPanel();
			}
		});
		brick.setText("brick");
		monopolyPanel.add(new KComponent(brick, new Rectangle(4,14,6,2)));

		// Year of plenty card panel1
		//-------------------------------------------------------------------

		JButton wool1 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("WOOL", GameRunner.currentPlayer.getNumberResourcesType("WOOL"));
				yearPanel2();
			}
		});
		wool1.setText("wool");
		yearPanel1.add(wool1, new Rectangle (4,6,6,2));

		JButton grain1 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("GRAIN", GameRunner.currentPlayer.getNumberResourcesType("GRAIN"));
				yearPanel2();
			}
		});
		grain1.setText("grain");
		yearPanel1.add(new KComponent(grain1, new Rectangle(4,4,6,2)));

		JButton ore1 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("GRAIN", GameRunner.currentPlayer.getNumberResourcesType("GRAIN"));
				yearPanel2();
			}
		});
		ore1.setText("ore");
		yearPanel1.add(new KComponent(ore1, new Rectangle(4,8,6,2)));

		JButton lumber1 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("ORE", GameRunner.currentPlayer.getNumberResourcesType("ORE"));
				yearPanel2();
			}
		});
		lumber1.setText("lumber");
		yearPanel1.add(new KComponent(lumber1, new Rectangle(4,10,6,2)));

		JButton brick1 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("BRICK", GameRunner.currentPlayer.getNumberResourcesType("BRICK"));
				yearPanel2();
			}
		});
		brick1.setText("brick");
		yearPanel1.add(new KComponent(brick1, new Rectangle(4,12,6,2)));

		// Year of plenty card panel2
		//-------------------------------------------------------------------

		JButton wool2 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("WOOL", GameRunner.currentPlayer.getNumberResourcesType("WOOL"));
				devPanel();
			}
		});
		wool2.setText("wool");
		yearPanel2.add(wool2, new Rectangle (4,6,6,2));

		JButton grain2 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("GRAIN", GameRunner.currentPlayer.getNumberResourcesType("GRAIN"));
				devPanel();
			}
		});
		grain2.setText("grain");
		yearPanel2.add(new KComponent(grain2, new Rectangle(4,6,6,2)));

		JButton ore2 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("ORE", GameRunner.currentPlayer.getNumberResourcesType("ORE"));
				devPanel();
			}
		});
		ore2.setText("ore");
		yearPanel2.add(new KComponent(ore2, new Rectangle(4,8,6,2)));

		JButton lumber2 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("LUMBER", GameRunner.currentPlayer.getNumberResourcesType("LUMBER"));
				devPanel();
			}
		});
		lumber2.setText("lumber");
		yearPanel2.add(new KComponent(lumber2, new Rectangle(4,10,6,2)));

		JButton brick2 = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				GameRunner.currentPlayer.setNumberResourcesType("BRICK", GameRunner.currentPlayer.getNumberResourcesType("BRICK"));
				devPanel();
			}
		});
		brick2.setText("brick");
		yearPanel2.add(new KComponent(brick2, new Rectangle(4,12,6,2)));

		//-------------------------------------------------------------------

		setPanel(rollPanel);
	}

	private void setPanel(ComponentList cL) {
		this.removeAll();
		this.add(currentPlayerBox.getComponent(), currentPlayerBox.getRectangle());

		for (int i = 0; i < cL.size(); i++) {
			this.add(cL.get(i).getComponent(), cL.get(i).getRectangle());
		}

		repaint();
		validate();
	}

	public void buyPanel() {
		setPanel(buyPanel);
		flag = 2;
	}

	public void tradePanel() {
		setPanel(tradePanel);
		flag = 1;
	}

	public void rollPanel() {
		setCurrentPlayer(GameRunner.currentPlayer);
		setPanel(rollPanel);
		flag = 0;
	}

	public void mainPanel() {
		setPanel(mainPanel);
		flag = 0;
	}

	public void errorPanel(String str) {
		((JLabel) errorPanel.get(0).getComponent()).setText(str);
		setPanel(errorPanel);
	}

	public void devPanel() {
		setPanel(devPanel);
	}

	public void monopolyPanel() {
		setPanel(monopolyPanel);
	}

	public void yearPanel1() {
		setPanel(yearPanel1);
	}

	public void yearPanel2() {
		setPanel(yearPanel2);
	}

	public void stealPanel() {
		playerStealBox.removeAllItems();
		for (Player p : display.getBoard().getGame().getBoard().getRobberAdjacentPlayers()) {
			if (p.equals(GameRunner.currentPlayer)) {
				
			}
			else {
				playerStealBox.addItem(p);
			}
		}
		if (playerStealBox.getItemCount() == 0) {
			errorPanel("No one to steal from");
		}
		else
			setPanel(stealPanel);
	}
	
	public void blankPanel() {
		setPanel(blankPanel);
	}

	public void setCurrentPlayer(Player p) {
		((JLabel) currentPlayerBox.getComponent()).setText("Player: " + p.getName());
	}
	
}
