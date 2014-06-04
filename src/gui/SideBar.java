package gui;

import game.Game;
import game.GameRunner;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;


import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import board.Board;
import board.DevCard;
import board.Location;
import board.Tile;
import board.VertexLocation;
import lib.GraphPaperLayout;

public class SideBar extends JPanel {

	private ComponentList rollPanel 			= new ComponentList();
	private ComponentList mainPanel				= new ComponentList();
	private ComponentList buyPanel				= new ComponentList();
	private ComponentList tradePanel			= new ComponentList();
	private ComponentList choosePlayerPanel		= new ComponentList();
	private ComponentList errorPanel			= new ComponentList();
	private ComponentList devPanel				= new ComponentList();
	private ComponentList resPanel				= new ComponentList();
	private String resSelection;
	private boolean done = false;
	private ComponentList stealPanel			= new ComponentList();
	private ComponentList placePanel			= new ComponentList();

	private ComponentList setupPanel			= new ComponentList();
	private ComponentList inputResourcesPanel	= new ComponentList();
	private ArrayList<String> inputResources	= new ArrayList<String>();
	private ArrayList<String> offerResources	= new ArrayList<String>();
	private ArrayList<String> sellResources		= new ArrayList<String>();
	private Player tradeChoice;
	//private final ExecutorService pool;
	private boolean IRPdone = true;
	private boolean secondRound = false;
	private int count = 0;

	private KComponent currentPlayerBox;
	private final GameWindow display;
	private final Font font = new Font("Arial", 1, 16);
	private int flag = 0;
	// For tracking where we are in turn; 0 = main panel or roll, 1 = trade panel, 2 = buy panel, 3 = dev card panel

	public final static int INTERVAL = 50;
	private Timer timer;

	public SideBar(final GameWindow display) {
		//pool = Executors.newSingleThreadExecutor();

		this.display = display;
		this.setLayout(new GraphPaperLayout(new Dimension(14,24)));

		// Current player title (always in sidebar)
		//-------------------------------------------------------------------

		currentPlayerBox = new KComponent(new JLabel(""), new Rectangle(2,0,10,1));
		currentPlayerBox.getComponent().setFont(font);
		currentPlayerBox.getComponent().setForeground(Color.WHITE);
		setCurrentPlayer(GameRunner.getCurrentPlayer());
		add(currentPlayerBox.getComponent(), currentPlayerBox.getRectangle());

		// Roll panel:
		//-------------------------------------------------------------------

		JButton roll = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				if (g.over()) {
					GameRunner.setWinner(g.winningPlayer());

					winPanel();
				}

				int roll = g.roll(GameRunner.getCurrentPlayer());

				if (roll != 7) {
					mainPanel();
				}
				else {

					if (GameRunner.getNumbPlayers() == 3) {
						int remove = 0;
						if (GameRunner.getPlayer(0).getTotalResources() > 7)
							remove = GameRunner.getPlayer(0).getTotalResources() / 2;
						inputResourcesPanel(remove, GameRunner.getPlayer(0), "Remove " + remove + " resources", false);
						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if (IRPdone) {
									timer.stop();
									GameRunner.getPlayer(0).removeResources(inputResources);
									int remove = 0;
									if (GameRunner.getPlayer(1).getTotalResources() > 7)
										remove = GameRunner.getPlayer(1).getTotalResources() / 2;
									inputResourcesPanel(remove, GameRunner.getPlayer(1), "Remove " + remove + " resources", false);
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if (IRPdone) {
												timer.stop();
												GameRunner.getPlayer(1).removeResources(inputResources);
												int remove = 0;
												if (GameRunner.getPlayer(2).getTotalResources() > 7)
													remove = GameRunner.getPlayer(2).getTotalResources() / 2;
												inputResourcesPanel(remove, GameRunner.getPlayer(2), "Remove " + remove + " resources", false);
												timer = new Timer(INTERVAL,
														new ActionListener() {
													public void actionPerformed(ActionEvent evt) {
														if (IRPdone) {
															timer.stop();
															GameRunner.getPlayer(2).removeResources(inputResources);
															display.getBoard().placeRobber();
															placePanel("Move the robber...");
															timer = new Timer(INTERVAL,
																	new ActionListener() {
																public void actionPerformed(ActionEvent evt) {
																	if(display.getBoard().getState() == 1){
																	}
																	else {
																		timer.stop();
																		stealPanel();
																	}
																}
															});
															timer.start();
														}
													}
												});
												timer.start();
											}
										}
									});
									timer.start();
								}
							}
						});
						timer.start();
					}
					else {
						//System.out.println("3");
						int remove = 0;
						if (GameRunner.getPlayer(0).getTotalResources() > 7)
							remove = GameRunner.getPlayer(0).getTotalResources() / 2;
						inputResourcesPanel(remove, GameRunner.getPlayer(0), "Remove " + remove + " resources", false);
						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if (IRPdone) {
									timer.stop();
									GameRunner.getPlayer(0).removeResources(inputResources);
									int remove = 0;
									if (GameRunner.getPlayer(1).getTotalResources() > 7)
										remove = GameRunner.getPlayer(1).getTotalResources() / 2;
									inputResourcesPanel(remove, GameRunner.getPlayer(1), "Remove " + remove + " resources", false);
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if (IRPdone) {
												timer.stop();
												GameRunner.getPlayer(1).removeResources(inputResources);
												int remove = 0;
												if (GameRunner.getPlayer(2).getTotalResources() > 7)
													remove = GameRunner.getPlayer(2).getTotalResources() / 2;
												inputResourcesPanel(remove, GameRunner.getPlayer(2), "Remove " + remove + " resources", false);
												timer = new Timer(INTERVAL,
														new ActionListener() {
													public void actionPerformed(ActionEvent evt) {
														if (IRPdone) {
															timer.stop();
															GameRunner.getPlayer(2).removeResources(inputResources);
															int remove = 0;
															if (GameRunner.getPlayer(3).getTotalResources() > 7)
																remove = GameRunner.getPlayer(3).getTotalResources() / 2;
															inputResourcesPanel(remove, GameRunner.getPlayer(3), "Remove " + remove + " resources", false);
															timer = new Timer(INTERVAL,
																	new ActionListener() {
																public void actionPerformed(ActionEvent evt) {
																	if(IRPdone){
																		timer.stop();
																		GameRunner.getPlayer(3).removeResources(inputResources);
																		display.getBoard().placeRobber();
																		placePanel("Move the robber...");
																		timer = new Timer(INTERVAL,
																				new ActionListener() {
																			public void actionPerformed(ActionEvent evt) {
																				if(display.getBoard().getState() == 1){
																				}
																				else {
																					timer.stop();
																					stealPanel();
																				}
																			}
																		});
																		timer.start();
																	}
																}
															});
															timer.start();
														}
													}
												});
												timer.start();
											}
										}
									});
									timer.start();
								}
							}
						});
						timer.start();
					}
				}

				JLabel rollNumb = new JLabel("Roll value: " + roll);
				rollNumb.setFont(font);
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
				Game g = display.getBoard().getGame();
				
				// Check for largest army
				if (GameRunner.getCurrentPlayer().getNumbKnights() >= 3) {

					int currMax = GameRunner.getCurrentPlayer().getNumbKnights();
					Player largestArmy = GameRunner.getCurrentPlayer();
					Player oldLargestArmy = null;
					
					for (int i = 0; i < GameRunner.getNumbPlayers(); i++) {
						Player p = GameRunner.getPlayer(i);

						if (p.hasLargestArmy()) {
							oldLargestArmy = p;
						}

						if (p.getNumbKnights() > currMax) {
							largestArmy = p;
							currMax = p.getNumbKnights();
						}
					}
					
					if (oldLargestArmy != null && oldLargestArmy != largestArmy) {
						oldLargestArmy.setHasLargestArmy(false);
					}
					
					largestArmy.setHasLargestArmy(true);
				}

				if (g.over()) {
					GameRunner.setWinner(g.winningPlayer());

					winPanel();
				}

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
				choosePlayerPanel();
			}
		});
		tradePlayer.setText("a player");
		tradePanel.add(new KComponent(tradePlayer, new Rectangle(1,6,6,2)));

		// Trade with stock
		JButton tradeStock = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resPanel();
				timer = new Timer(INTERVAL,
						new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if(done){
							timer.stop();
							done = false;
							final String res = resSelection;
							inputResourcesPanel(-1, GameRunner.getCurrentPlayer(), "Sell resources", false);
							timer = new Timer(INTERVAL,
									new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(IRPdone){
										timer.stop();
										display.getBoard().getGame().npcTrade(GameRunner.getCurrentPlayer(), res, inputResources);
										mainPanel();
									}
								}
							});
							timer.start();
						}
					}
				});
				timer.start();
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

				int bought = g.buySettlement(GameRunner.getCurrentPlayer());

				if (bought == 0) {
					display.getBoard().placeSettlement(1);
					placePanel("Place a settlement...");
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
					errorPanel("Insufficient resources!");
				}
				else if (bought == 2) {
					errorPanel("Structure capacity reached!");
				}
			}
		});
		buySettlement.setText("settlement");
		buyPanel.add(new KComponent(buySettlement, new Rectangle(1,6,6,2)));

		// Buy city
		JButton buyCity = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyCity(GameRunner.getCurrentPlayer());

				if (bought == 0) {
					display.getBoard().placeCity(1);
					placePanel("Select a settlement...");
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
					errorPanel("Insufficient resources!");
				}
				else if (bought == 2) {
					errorPanel("Structure capacity reached!");
				}
			}
		});
		buyCity.setText("city");
		buyPanel.add(new KComponent(buyCity, new Rectangle(7,6,6,2)));

		// Buy road
		JButton buyRoad = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyRoad(GameRunner.getCurrentPlayer());

				if (bought == 0) {
					display.getBoard().placeRoad(1);
					placePanel("Place a road...");
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
					errorPanel("Insufficient resources!");
				}
				else if (bought == 2) {
					errorPanel("Structure capacity reached!");
				}
			}
		});
		buyRoad.setText("road");
		buyPanel.add(new KComponent(buyRoad, new Rectangle(1,8,6,2)));

		// Buy devcard
		JButton buyCard = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				int bought = g.buyDevCard(GameRunner.getCurrentPlayer());

				if (bought == 0) {
					DevCard dC = g.getDeck().draw();
					GameRunner.getCurrentPlayer().addDevCard(dC);

					buyPanel();
				}
				else if (bought == 1) {
					errorPanel("Insufficient resources!");
				}
				else if (bought == 2) {
					errorPanel("No more dev cards in deck!");
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
		message.setFont(font);
		errorPanel.add(message, new Rectangle(2,4,10,1));

		JButton accept = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				switch(flag) {
				case 0: setPanel(mainPanel); break;
				case 1: setPanel(tradePanel); break;
				case 2: setPanel(buyPanel); break;
				case 3: setPanel(devPanel); break;
				default: setPanel(mainPanel); break;
				}
			}
		});
		accept.setText("continue");
		errorPanel.add(accept, new Rectangle (3,7,9,2));

		// Dev card panel:
		//-------------------------------------------------------------------

		JLabel devCard = new JLabel("Play a...");
		message.setFont(font);
		devPanel.add(devCard, new Rectangle(4,4,6,2));

		// Play a knight card
		JButton knight = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();

				if (GameRunner.getCurrentPlayer().hasCard("Knight")) {
					GameRunner.getCurrentPlayer().removeCard("Knight");
					GameRunner.getCurrentPlayer().incrementNumbKnights();

					display.getBoard().placeRobber();
					placePanel("Move the robber...");
					timer = new Timer(INTERVAL,
							new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if(display.getBoard().getState() == 1) {
							}
							else {
								timer.stop();
								GameRunner.getCurrentPlayer().incrementNumbKnights();
								//Choose player to steal from (JComboBox)

								stealPanel();
							}
						}
					});
					timer.start();
				}
				else {
					errorPanel("You don't own this card!");
				}
			}
		});
		knight.setText("knight");
		devPanel.add(knight, new Rectangle (1,6,6,2));

		// Play a monopoly card
		JButton monopoly = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if (GameRunner.getCurrentPlayer().hasCard("Monopoly")) {
					GameRunner.getCurrentPlayer().removeCard("Monopoly");

					resPanel();
					timer = new Timer(INTERVAL,
							new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if(done){
								timer.stop();
								done = false;

								Game g = display.getBoard().getGame();
								g.takeAll(resSelection, GameRunner.getCurrentPlayer());

								mainPanel();
							}
						}
					});
					timer.start();
					
				}
				else {
					errorPanel("You don't own this card!");
				}
			}
		});
		monopoly.setText("monopoly");
		devPanel.add(new KComponent(monopoly, new Rectangle(7,6,6,2)));

		// Play a year of plenty card
		JButton year = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				if (GameRunner.getCurrentPlayer().hasCard("Year of plenty")) {
					GameRunner.getCurrentPlayer().removeCard("Year of plenty");
					inputResourcesPanel(2, GameRunner.getCurrentPlayer(), "Choose 2 resources", true);
					timer = new Timer(INTERVAL,
							new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if(IRPdone){
								timer.stop();
								GameRunner.getCurrentPlayer().addResources(inputResources);
								mainPanel();
							}
						}
					});
					timer.start();
				}
				else {
					errorPanel("You don't own this card!");
				}
			}
		});
		year.setText("year 'o plenty");
		devPanel.add(new KComponent(year, new Rectangle(1,8,6,2)));

		// Play a road building card
		JButton road = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				Game g = display.getBoard().getGame();
				if (GameRunner.getCurrentPlayer().hasCard("Road building")) {
					GameRunner.getCurrentPlayer().removeCard("Road building");

					display.getBoard().placeRoad(2);
					placePanel("Place 2 roads...");

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
					errorPanel("You don't own this card!");
				}
			}
		});
		road.setText("road building");
		devPanel.add(new KComponent(road, new Rectangle(7,8,6,2)));

		// Return to main panel
		devPanel.add(new KComponent(returnMain, new Rectangle(3,12,8,2)));

		// Steal panel:
		//-------------------------------------------------------------------

		JComboBox<Player> playerStealBox = new JComboBox<Player>();
		playerStealBox.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				JComboBox<Player> cb = (JComboBox)a.getSource();
				Player playerSteal = (Player)cb.getSelectedItem();
				display.getBoard().getGame().takeCard(GameRunner.getCurrentPlayer(), playerSteal);
				mainPanel();
			}
		});

		stealPanel.add(playerStealBox, new Rectangle(3,6,8,2));

		// Choose Trade Partner Panel:
		//-------------------------------------------------------------------

		JComboBox<Player> playerTradeBox = new JComboBox<Player>();
		playerTradeBox.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				//System.out.println("action!");
				JComboBox<Player> cb = (JComboBox)a.getSource();
				tradeChoice = (Player)cb.getSelectedItem();
				//System.out.println(tradeChoice);
				inputResourcesPanel(-1, GameRunner.getCurrentPlayer(), "Offer resources", false);
				timer = new Timer(INTERVAL,
						new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if(IRPdone){
							timer.stop();
							offerResources = inputResources;
							inputResourcesPanel(-1, tradeChoice, "Sell resources", false);
							timer = new Timer(INTERVAL,
									new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									if(IRPdone){
										timer.stop();
										sellResources = inputResources;
										display.getBoard().getGame().playerTrade(GameRunner.getCurrentPlayer(), tradeChoice, offerResources, sellResources);
										mainPanel();
									}
								}
							});
							timer.start();
						}
					}
				});
				timer.start();
			}
		});

		choosePlayerPanel.add(playerTradeBox, new Rectangle(3,6,8,2));

		// Resource selection panel:
		//-------------------------------------------------------------------

		JButton wool = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resSelection = "WOOL";
				done = true;
			}
		});
		wool.setText("wool");
		resPanel.add(wool, new Rectangle(4,6,6,2));

		JButton grain = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resSelection = "GRAIN";
				done = true;
			}
		});
		grain.setText("grain");
		resPanel.add(grain, new Rectangle(4,8,6,2));

		JButton lumber = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resSelection = "LUMBER";
				done = true;
			}
		});
		lumber.setText("lumber");
		resPanel.add(lumber, new Rectangle(4,10,6,2));

		JButton ore = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resSelection = "ORE";
				done = true;
			}
		});
		ore.setText("ore");
		resPanel.add(ore, new Rectangle(4,12,6,2));

		JButton brick = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				resSelection = "BRICK";
				done = true;
			}
		});
		brick.setText("brick");
		resPanel.add(brick, new Rectangle(4,14,6,2));

		// Place panel
		//-------------------------------------------------------------------

		JLabel mess = new JLabel();
		mess.setFont(font);
		placePanel.add(mess, new Rectangle(2,8,10,4));

		// Setup panel
		//-------------------------------------------------------------------

		final JLabel start = new JLabel("Your setup, " + GameRunner.getCurrentPlayer());
		start.setFont(font);
		setupPanel.add(new KComponent(start, new Rectangle(2,3,10,2)));

		JButton begin = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				//System.out.println(count);
				if (!secondRound) {

					if (count == GameRunner.getNumbPlayers() - 1) {

						secondRound = true;
						count++;
						//Place capitol commandblock
						display.getBoard().placeSettlementNoRoad(1);
						placePanel("Place first settlement..");

						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if(display.getBoard().getState() == 5){

								}
								else {
									timer.stop();
									//Place Road commandblock
									display.getBoard().placeRoad(1);
									placePanel("Place a road...");
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if(display.getBoard().getState() == 3){

											}
											else {
												timer.stop();
												setCurrentPlayer(GameRunner.getCurrentPlayer());
												start.setText("Place a settlement, " + GameRunner.getCurrentPlayer());
												setupPanel();
											}
										}
									});
									timer.start();
									//
								}
							}
						});
						timer.start();
						//
					}
					else {
						count++;
						//Place SettlementNoRoad commandblock
						display.getBoard().placeSettlementNoRoad(1);
						placePanel("Place first settlement...");
						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if(display.getBoard().getState() == 5){

								}
								else {
									timer.stop();
									//Place Road commandblock
									display.getBoard().placeRoad(1);
									placePanel("Place a road...");
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if(display.getBoard().getState() == 3){

											}
											else {
												timer.stop();
												GameRunner.nextPlayer();
												setCurrentPlayer(GameRunner.getCurrentPlayer());
												start.setText("Place a settlement, " + GameRunner.getCurrentPlayer());
												setupPanel();
											}
										}
									});
									timer.start();
									//
								}
							}
						});
						timer.start();
						//
					}
				}
				else {

					if (count == 1) {
						//Place capitol commandblock
						display.getBoard().placeCapitol();
						placePanel("Place your capitol...");
						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if(display.getBoard().getState() == 5){

								}
								else {
									timer.stop();
									//Place Road commandblock
									display.getBoard().placeRoad(1);
									placePanel("Place a road...");
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if(display.getBoard().getState() == 3){

											}
											else {
												timer.stop();
												//Collections.shuffle(GameRunner.players);
												setCurrentPlayer(GameRunner.getCurrentPlayer());
												rollPanel();
											}
										}
									});
									timer.start();
									//
								}
							}
						});
						timer.start();
						//
					}
					else {
						count--;
						//Place capitol commandblock
						display.getBoard().placeCapitol();
						placePanel("Place your capitol...");
						timer = new Timer(INTERVAL,
								new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								if(display.getBoard().getState() == 5){

								}
								else {
									timer.stop();
									//Place Road commandblock
									display.getBoard().placeRoad(1);
									placePanel("Place a road...");
									timer = new Timer(INTERVAL,
											new ActionListener() {
										public void actionPerformed(ActionEvent evt) {
											if(display.getBoard().getState() == 3){

											}
											else {
												timer.stop();
												GameRunner.prevPlayer();
												setCurrentPlayer(GameRunner.getCurrentPlayer());
												start.setText("Place your capitol, " + GameRunner.getCurrentPlayer());
												setupPanel();
											}
										}
									});
									timer.start();
									//
								}
							}
						});
						timer.start();
						//
					}
				}
			}
		});
		begin.setText("place");
		setupPanel.add(new KComponent(begin, new Rectangle(4,6,6,3)));

		// Input Resources Panel
		//-------------------------------------------------------------------

		JComboBox<Integer> brickCombo = new JComboBox<Integer>();
		brickCombo.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});

		inputResourcesPanel.add(brickCombo, new Rectangle(2,6,3,1));

		JComboBox<Integer> woolCombo = new JComboBox<Integer>();
		woolCombo.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});

		inputResourcesPanel.add(woolCombo, new Rectangle(6,6,3,1));

		JComboBox<Integer> oreCombo = new JComboBox<Integer>();
		oreCombo.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});

		inputResourcesPanel.add(oreCombo, new Rectangle(10,6,3,1));

		JComboBox<Integer> grainCombo = new JComboBox<Integer>();
		grainCombo.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});

		inputResourcesPanel.add(grainCombo, new Rectangle(4,10,3,1));

		JComboBox<Integer> lumberCombo = new JComboBox<Integer>();
		lumberCombo.setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
			}
		});

		inputResourcesPanel.add(lumberCombo, new Rectangle(8,10,3,1));

		JLabel playerLabel= new JLabel("Player: ");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(playerLabel, new Rectangle(2,3,15,1)));

		JButton submitResources = new JButton();
		submitResources.setText("Submit");
		inputResourcesPanel.add(submitResources, new Rectangle(3,15,9,2));

		JLabel brickLabel = new JLabel("Brick");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(brickLabel, new Rectangle(2,5,4,1)));

		JLabel woolLabel = new JLabel("Wool");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(woolLabel, new Rectangle(6,5,4,1)));

		JLabel oreLabel = new JLabel("Ore");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(oreLabel, new Rectangle(10,5,4,1)));

		JLabel grainLabel = new JLabel("Grain");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(grainLabel, new Rectangle(4,9,4,1)));

		JLabel lumberLabel = new JLabel("Lumber");
		start.setFont(font);
		inputResourcesPanel.add(new KComponent(lumberLabel, new Rectangle(8,9,4,1)));
		
		//inputResourcesPanel(1, GameRunner.getCurrentPlayer(), "test");

		//-------------------------------------------------------------------

		setupPanel();
//		mainPanel();
//		if (!GameRunner.getCurrentPlayer().getName().equals("DevMaster"))
//			GameRunner.nextPlayer();
//		if (!GameRunner.getCurrentPlayer().getName().equals("DevMaster"))
//			GameRunner.nextPlayer();
//		if (!GameRunner.getCurrentPlayer().getName().equals("DevMaster"))
//			GameRunner.nextPlayer();
//		devPanel();
//		rollPanel();
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
		setCurrentPlayer(GameRunner.getCurrentPlayer());
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
		flag = 3;
	}

	public void resPanel() {
		setPanel(resPanel);
	}

	public void stealPanel() {
		//JComboBox<Player> newBox = new JComboBox<Player>();
		AbstractAction action = (AbstractAction) ((JComboBox<Player>) stealPanel.get(0).getComponent()).getAction();
		((JComboBox<Player>) stealPanel.get(0).getComponent()).setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
			}
		});
		((JComboBox<Player>) stealPanel.get(0).getComponent()).removeAllItems();
		for (int i = 0; i < display.getBoard().getGame().getBoard().getRobberAdjacentPlayers().size(); i++) {
			if (display.getBoard().getGame().getBoard().getRobberAdjacentPlayers().get(i).equals(GameRunner.getCurrentPlayer())) {
			}
			else {
				((JComboBox<Player>) stealPanel.get(0).getComponent()).addItem(display.getBoard().getGame().getBoard().getRobberAdjacentPlayers().get(i));
			}
		}

		((JComboBox<Player>) stealPanel.get(0).getComponent()).setAction(action);
		//System.out.println(((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemCount());
		//System.out.println(((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemAt(0));

		if (((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemCount() <= 0) {
			//System.out.println("IF");
			errorPanel("No one to steal from");
		}
		else {
			//System.out.println("ELSE");
			setPanel(stealPanel);
		}
	}
	
	public void choosePlayerPanel() {
		AbstractAction action = (AbstractAction) ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).getAction();
		// Remove action, so that removeAllItems() does not trigger an event
		((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				
			}
		});
		// Depopulate combo box
		((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).removeAllItems();
		// Populate combo box
		for (int i = 0; i < GameRunner.getNumbPlayers(); i++) {
			if (GameRunner.getPlayer(i).equals(GameRunner.getCurrentPlayer())) {
			}
			else {
				((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).addItem(GameRunner.getPlayer(i));
			}
		}
		// Reset action
		((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).setAction(action);
		setPanel(choosePlayerPanel);
	}

	public void placePanel(String str) {
		((JLabel) placePanel.get(0).getComponent()).setText(str);
		setPanel(placePanel);
	}

	/**
	 * 
	 * 
	 * @param n Number of resources to be selected, -1 for any
	 * @param p the player inputting resources
	 * @param str to display on submit button
	 * @return ArrayList<String> of resources selected
	 */
	public void inputResourcesPanel(final int n, final Player p, String str, final boolean YOP) {
		//final ArrayList<String> output = new ArrayList<String>();
		IRPdone = false;

		// Depopulates / Repopulates the combo boxes
		for (int i = 0; i < 5; i++) {
			((JComboBox<Integer>) inputResourcesPanel.get(i).getComponent()).removeAllItems();
		}
		if (YOP) {
			for (int r = 0; r <= 2; r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= 2; r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= 2; r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= 2; r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= 2; r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).addItem(new Integer(r));
			}
		}
		else {
			for (int r = 0; r <= p.getNumberResourcesType("BRICK"); r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= p.getNumberResourcesType("WOOL"); r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= p.getNumberResourcesType("ORE"); r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= p.getNumberResourcesType("GRAIN"); r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).addItem(new Integer(r));
			}
			for (int r = 0; r <= p.getNumberResourcesType("LUMBER"); r++) {
				((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).addItem(new Integer(r));
			}
		}

		//Sets player
		((JLabel) inputResourcesPanel.get(5).getComponent()).setText("Player: " + p.getName());

		// Sets action according to flag and resourceCount
		((JButton) inputResourcesPanel.get(6).getComponent()).setAction(new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				int sum = 0;
				for (int i = 0; i < 5; i++) {
					sum += ((JComboBox<Integer>) inputResourcesPanel.get(i).getComponent()).getSelectedIndex();
				}
				//System.out.println(sum);
				if (n != -1) {
					if (sum != n) {
						return;
					}
				}
				ArrayList<String> output2 = new ArrayList<String>();

				for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).getSelectedIndex(); i++) {
					output2.add("BRICK");
				}
				for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).getSelectedIndex(); i++) {
					output2.add("WOOL");
				}
				for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).getSelectedIndex(); i++) {
					output2.add("ORE");
				}
				for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).getSelectedIndex(); i++) {
					output2.add("GRAIN");
				}
				for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).getSelectedIndex(); i++) {
					output2.add("LUMBER");
				}

				inputResources = output2;
				//System.out.println("Arrived");
				IRPdone = true;
				/*
				switch (flag) {
				case 0:
					System.out.println(inputResources);
					System.out.println("blargh");
					p.removeResources(inputResources);
					break;
				case 1:
					break;
				}
				 */

			}
		});

		// Sets submit button text
		((JButton) inputResourcesPanel.get(6).getComponent()).setText(str);

		setPanel(inputResourcesPanel);
		/*
		timer = new Timer(INTERVAL,
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if(continue){
							return output;
						}
					}
				});
		timer.start();
		 */

	}

	public void setCurrentPlayer(Player p) {
		JLabel label = (JLabel) currentPlayerBox.getComponent();
		label.setText("Player: " + p.getName());
		label.setOpaque(true);
		label.setBackground(GameRunner.getCurrentPlayer().getColor());
	}

	public void setupPanel() {
		setPanel(setupPanel);
	}

	public void winPanel() {
		this.removeAll();

		JLabel win = new JLabel(GameRunner.getWinner().getName() + " wins!");
		win.setFont(new Font("Arial", 1, 24));
		this.add(win, new Rectangle(2,4,10,5));
	}
}
