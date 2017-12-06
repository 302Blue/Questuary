package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

import model.Model;
import model.fixed.Question;
import view.View;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Controller {

	// *************************************************
	// Fields
	Model model;
	View view;
	JFrame frame;
	// IntroductionView intro = new IntroductionView(frame);
	Timer timer;
	Timer gameTimer;
	ArrayList<Integer> keys = new ArrayList<Integer>();
	// private Long startGameTime;
	int gameTimeLeft = 180; // minutes in seconds

	// question state
	private boolean questionModeFlag = true;

	boolean questionMode = false;
	Question question;
	JFrame questionFrame;
	boolean isIntroMode = false;
	// end question

	// *************************************************
	// Constructor

	/**
	 * Constructor for the Controller class
	 */
	public Controller() {
		// create the Model and View
		model = new Model(isIntroMode);
		view = new View();
		// pauses the game in beginning for intro mode
		//model.setIsGamePaused();
		
		// get the ground from model
		view.setGroundImage(model.getGround());

		view.setPlatforms(model.getPlatforms());
		// update the view of the player's location
		view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(), model.getPlayerCharacter(),
				model.getPlayerHealth());

		// give view list of enemies
		view.setEnemies(model.getEnemies());

		// give view list of collectibles
		view.setCollectibles(model.getCollectibles());

		// give view list of collected
		view.setCollected(model.getCollected());

		// give view list of facts
		// view.setFacts(model.getFacts());

		// give view list of facts
		view.setFacts(model.getFacts());

		// give view list of chests
		view.setChests(model.getChests());

		// set the frame to add on key event listeners
		frame = view.getFrame();

		// add KeyListeners
		frame.addKeyListener(new ArrowKeyListener());

		timer = new Timer(45, new UpdateView());
		timer.start();
		gameTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameTimeLeft--;
				
			}
		});
		gameTimer.start();

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
		 * Method to carry out actions every time an event is occurs
		 * 
		 * @param arg0
		 *            - Basic argument for a the actionPerformed function
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (gameTimeLeft == 0) {
				gameTimer.stop();
				model.setIsGameOver(true);
			}
			if (model.getIsGamePaused()) {
				gameTimer.stop();
			} else {
				gameTimer.start();
			}
			
			if (!questionMode) {
				// add checkQuestionMode
				// if model is in question mode then create the JFrame in view with the
				// questions
				questionMode = model.isQuestionMode();
				if (questionMode) {
					// pause the game timer
					gameTimer.stop();
					question = model.getQuestion();
					questionFrame = view.createQuestionFrame(question);
					questionFrame.addKeyListener(new QuestionKeyListener());
					questionFrame.requestFocus();
				}
				else {
					gameTimer.start();
				}
				// end of QuestionMode

				view.setGameTime(gameTimeLeft);
				model.setGameTimeLeft(gameTimeLeft);
				model.changeRoom();
				model.checkCollision();
				model.gravity();
				// increment the player's x and y
				model.movePlayer();
				// move the enemies
				model.moveEnemies();
				model.checkIsGameOver();

				// update the view and draw the image
				view.setPlatforms(model.getPlatforms());
				view.setEnemies(model.getEnemies());
				view.setCollectibles(model.getCollectibles());
				view.setCollected(model.getCollected());
				view.setFacts(model.getFacts());
				view.setChests(model.getChests());

				view.setScore(model.getScore());
				view.setPicNum();

				if (model.getPlayerDx() != 0 || model.getPlayerDy() != 0) {
					view.setAnimation(true);
				} else {
					view.setAnimation(false);
				}

				view.updateView(model.getPlayerX(), model.getPlayerY(), model.getPlayerDirection(),
						model.getPlayerCharacter(), model.getPlayerHealth());
				if (model.getIsGameOver() || gameTimeLeft == 0) {
					if (!model.getIsGameOver()) {
						model.setIsGameOver(true);
					}
					// controls for game over state
					if (model.isNewHighScore()) {
						String name = view.getName();
						model.setName(name);
						model.updateHighScore();
					}else {
						model.setName(model.getHighScore().split(": ")[0]);
						model.updateHighScore();
					}
					view.setHighScore(model.getHighScore());
					
					view.gameOverMode();
				}
			} else {
				questionMode = model.isQuestionMode();
			}
		}
	}

	// *************************************************
	// KeyListener Methods

	/**
	 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
	 *         ArrowKeyListener used by all aspects of game except for questionMode
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

			if (model.getIsGameOver()) {
				switch (keys.get(0)) {

				
				case (KeyEvent.VK_ESCAPE):
				case (KeyEvent.VK_Q):
					// if q or esc pressed then quit
					System.exit(0);
					break;
				}

				if (keys.contains(KeyEvent.VK_DOWN) && keys.contains(KeyEvent.VK_RIGHT)) {
					// something is selected from game over screen
					frame.dispose();
					Controller newGame = new Controller();
				}else if(keys.contains(KeyEvent.VK_SPACE) && keys.contains(KeyEvent.VK_F)) {
					model.addBadWord();
					view.setHighScore(model.getHighScore());
					
				}
			}

			// if only 1 key is pressed
			if (keys.size() == 1) {
				// checks if game is not in Change player mode
				if (!model.getChangeCharacterMode() && !model.getIsGameOver()) {
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
						System.out.println("Up Key Pressed");
						System.out.println("(" + model.getPlayerX() + "," + model.getPlayerY() + ")"
								+ model.getPlayerDirectionString());
						break;
					case (KeyEvent.VK_ESCAPE):
					case (KeyEvent.VK_Q):
						// if q or esc pressed then quit
						System.exit(0);
						break;
					}
				} else if (model.getChangeCharacterMode() && !model.getIsGameOver()) {
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

	/**
	 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
	 *         QuestionKeyListener used only by questionMode
	 */
	public class QuestionKeyListener implements KeyListener {

		private boolean pressedRightTwice = false;

		boolean allowInput = false;
		Timer delay = new Timer(1500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allowInput = true;
				delay.stop();
			}
		});

		/**
		 * Delays key inputs in Question mode so that player doesn't accidentally submit
		 * empty answer
		 */
		public QuestionKeyListener() {
			delay.start();
		}

		/**
		 * Used to navigate the radio buttons for answer options
		 * 
		 * @param arg0
		 *            - Which key is pressed
		 */
		public void keyPressed(KeyEvent arg0) {
			if (allowInput) {
				switch (arg0.getKeyCode()) {
				case (KeyEvent.VK_UP):
					System.out.println("Question up key pressed");
					question.up();
					view.updateQuestion(question.getIndex());
					break;
				case (KeyEvent.VK_DOWN):
					System.out.println("Question down key pressed");
					question.down();
					view.updateQuestion(question.getIndex());
					break;
				case (KeyEvent.VK_RIGHT):
					System.out.println("Question right key pressed");
					if (!pressedRightTwice) {
						if (question.right()) {
							view.displayCorrect(question);
							model.incrementPlayerHealth();
							model.incrementPlayerScoreBy(25);
						} else {
							view.displayWrong(question);
						}
						pressedRightTwice = true;
					} else {
						model.setQuestionMode(false);
						view.TurnOffQuestionFrame();
						// Turn game timer back on
					}
					break;
				}
			}
		}

		public void keyReleased(KeyEvent arg0) {

		}

		public void keyTyped(KeyEvent arg0) {

		}
	}

}
