package controller;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import view.View;
import model.Model;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Controller {

	// *************************************************
	// Fields

	Model model;
	View view;
	JFrame frame;
	Timer timer;
	ArrayList<Integer> keys = new ArrayList<Integer>();

	// *************************************************
	// Constructor

	/**
	 * Constructor for the Controller class
	 */
	public Controller() {
		// create the Model and View
		model = new Model();
		view = new View();

		// get the ground from model
		view.setGroundImage(model.getGround());

		view.setPlatformImage(model.getPlatforms());
		// update the view of the player's location
		view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(),
				model.getPlayerCharacter(), model.getPlayerHealth());

		//give view list of enemies
		view.setEnemies(model.getEnemies());
		
		// set the frame to add on key event listeners
		frame = view.getFrame();

		// add KeyListeners
		frame.addKeyListener(new ArrowKeyListener());

		timer = new Timer(60, new UpdateView());
		timer.start();

	}

	// *************************************************
	// Methods

	/**
	 * Main method to start the program
	 * 
	 * @param args
	 *            - Basic argument for a main function
	 */
	public static void main(String[] args) {
		System.out.println("GET READY TO RRRUUUUUUUMMMMBBBLLLLLEEEE!!");
		Controller Game = new Controller();
	}

	/**
	 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
	 */
	public class UpdateView implements ActionListener {
		/**
		 * Method to carry out actions everytime an event is occurs
		 * 
		 * @param arg0
		 *            - Basic argument for a the actionPerformed function
		 */
		public void actionPerformed(ActionEvent arg0) {
			// test the player on falling and jumping
			model.changeRoom();
			model.gravity();
			model.checkCollision();
			// increment the player's x and y
			model.movePlayer();
			//move the enemies
			model.moveEnemies();
			view.setPicNum();
			// update the view and draw the image
			view.setPlatformImage(model.getPlatforms());
			view.setEnemies(model.getEnemies());
			view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(),
					model.getPlayerCharacter(), model.getPlayerHealth());
		}

	}

	// *************************************************
	// KeyListener Methods

	/**
	 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
	 */
	public class ArrowKeyListener implements KeyListener {
		/**
		 * Listener method to handle whenever a key is pressed
		 * 
		 * @param e
		 *            - KeyEvent argument passed in whenever the listener catches a key
		 *            press
		 */
		public void keyPressed(KeyEvent e) {
			// add key code in arrayList if pressed and not already there
			if (!keys.contains(e.getKeyCode())) {
				keys.add(e.getKeyCode());
			}
			// if only 1 key is pressed
			if (keys.size() == 1) {
				// checks if game is not in Change player mode
				if (!model.getChangeCharacterMode()) {
					switch (keys.get(0)) {
					case (KeyEvent.VK_RIGHT):
						// if x is less than the xBoundary then increment by xVelocity
						// make player go right in model
						model.changeRoom();
						model.playerMoveRight();
						view.setAnimation(true);
						System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")"
								+ model.getPlayerDirectionString());
						break;
					case (KeyEvent.VK_LEFT):
						// make player go left in model
						model.changeRoom();
						model.playerMoveLeft();
						view.setAnimation(true);
						System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")"
								+ model.getPlayerDirectionString());
						break;
					case (KeyEvent.VK_UP):
						if (!(model.isPlayerFalling())) {
							model.makePlayerJump();
							System.out.println("Executed: makePlayerJump()");
						}
						view.setAnimation(true);
						System.out.println("Up Key Pressed");
						System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")"
								+ model.getPlayerDirectionString());
						break;
					case (KeyEvent.VK_ESCAPE):
					case (KeyEvent.VK_Q):
						// if q is press then quit
						System.exit(0);
						break;
					}
				} else {
					// game is in change player mode
					switch (keys.get(0)) {
					case (KeyEvent.VK_RIGHT):
						// increment the player selector loop
						model.incrementChangeCharacterCount();
					view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(),
							model.getPlayerCharacter(), model.getPlayerHealth());
						break;
					case (KeyEvent.VK_LEFT):
						// increment the player selector loop
						model.decrementChangeCharacterCount();
					view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(),
							model.getPlayerCharacter(), model.getPlayerHealth());
						break;
					case (KeyEvent.VK_ESCAPE):
					case (KeyEvent.VK_Q):
						// if q is press then quit
						System.exit(0);
						break;
					}
				}

			} // if more then 1 key is pressed
			else if (keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_RIGHT)) {
				// System.exit(0);
				// changes game mode to switching player
				view.changeCharacterMode();
				model.setChangePlayerMode();
			} else if (keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_RIGHT)) {
				model.changeRoom();
				model.playerMoveRight();
				if (!(model.isPlayerFalling())) {
					model.makePlayerJump();
					System.out.println("Executed: makePlayerJump()");
				}

			} else if (keys.contains(KeyEvent.VK_UP) && keys.contains(KeyEvent.VK_LEFT)) {
				model.changeRoom();
				model.playerMoveLeft();
				if (!(model.isPlayerFalling())) {
					model.makePlayerJump();
					System.out.println("Executed: makePlayerJump()");
				}

			}

		}

		/**
		 * KeyListener method for whenever a key is typed (required for ArrowKeyListener
		 * class, will be unused
		 * 
		 * @param e
		 *            - KeyEvent argument passed in whenever the listener catches a key
		 *            typed
		 */
		public void keyTyped(KeyEvent e) {
		}

		/**
		 * KeyListener method for whenever a key is released
		 * 
		 * @param e
		 *            - KeyEvent argument passed in whenever the listener catches a key
		 *            release
		 */
		public void keyReleased(KeyEvent e) {
			// removes key code from arrayList once released
			if (keys.contains(e.getKeyCode())) {
				keys.remove(keys.indexOf(e.getKeyCode()));
			}
			switch (e.getKeyCode()) {
			// when right arrow is released the dx is 0
			case (KeyEvent.VK_RIGHT):
				model.setPlayerDxOff();
				view.setAnimation(false);
				System.out.println("Right Key Released");
				break;
			case (KeyEvent.VK_LEFT):
				// make player go left in model
				model.setPlayerDxOff();
				view.setAnimation(false);
				System.out.println("Left Key Released");
				break;
			}

		}
	}

}
