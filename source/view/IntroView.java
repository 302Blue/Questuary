package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.dynamic.Enemy;
import model.fixed.Chest;
import model.fixed.Collectible;
import model.fixed.Fact;
import model.fixed.Ground;
import model.fixed.Platform;
import model.fixed.Question;
import view.dynamic.BirdImage;
import view.dynamic.CrabImage;
import view.dynamic.OspreyImage;
import view.dynamic.ResearcherImage;
import view.fixed.ChestImage;
import view.fixed.CollectibleImage;
import view.fixed.FactImage;
import view.fixed.GroundImage;
import view.fixed.HeartImage;
import view.fixed.PlatformImage;

public class IntroView extends JPanel {

	// *************************************************
	// Fields

	private JFrame frame = new JFrame();
	// HashMap used for character change
	HashMap<String, ImageObject> characterImages = new HashMap<String, ImageObject>();
	// researcher image object that will be responsible in returning the researcher
	// image
	ResearcherImage researcherImage = new ResearcherImage();
	// bird image object responsible for bird image
	BirdImage birdImage = new BirdImage();
	// boolean determines player should animate
	boolean animate = false;
	// get the screen's dimensions
	final private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final private double screenRatio = screenWidth / screenHeight;
	// player's x and y coordinates
	int playerX = 0;
	int playerY = 0;
	// player's starting direction and character
	int direct = 1;
	String playerCharacter = "researcher";
	// ground
	Ground ground;
	// for hearts display
	int health;
	// HeartImage heart = new HeartImage();
	ArrayList<HeartImage> hearts = new ArrayList<HeartImage>(3);
	// list of platforms
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	// list of enemies
	ArrayList<Enemy> enemies;
	// list of collectibles & chests
	ArrayList<Collectible> collectibles;
	ArrayList<Collectible> collected;
	ArrayList<Fact> facts;
	ArrayList<Chest> chests;
	// fact show/not show
	boolean factTime;
	// chest open/closed
	boolean chestStatus;
	// game modes
	boolean changeCharacterMode = false;
	boolean gameOverMode = false;
	// change character screen
	Image ChangeCharacterResearcher;
	Image ChangeCharacterBird;
	final private int ChangeCharacterMenuHeight = 500;
	final private int ChangeCharacterMenuWidth = 700;
	// game over screen fields
	Image GameOverScreen;
	final private int GameOverScreenHeight = (int) screenHeight - 100;
	final private int GameOverScreenWidth = (int) screenWidth - 100;
	final private int GameOverScreenXPos = 50;
	final private int GameOverScreenYPos = 25;
	// game over score positions
	final private int GameOverScoreXPos = 75;
	final private int GameOverScoreYPos = 275;
	final private int GameOverHighScoreXPos = 75;
	final private int GameOverHighScoreYPos = 400;
	// high-score fields
	public String highScore = "";
	public String score = "";
	// game time field
	private int gameTimeLeft;
	final private int GameTimeBarHeight = 35;
	final private int GameTimeBarWidth = 450;
	final private int GameTimeBarXPos = GameOverScreenWidth / 2 - GameTimeBarWidth / 2;
	final private int GameTimeBarYPos = 5;
	int dynamicTimeBar;
	// set fact location depending on time bar

	// Add question JFrame
	JFrame questionFrame;
	ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
	Box questionBox;

	//Intro room umber
	int introRoomNum;
	
	// *************************************************
	// Constructor

	/**
	 * Constructor used to create a view object
	 * <p>
	 * This constructor puts researcher image and bird image into the characterImage
	 * hashmap. Creates a JFrame object and sets the title, dimensions, visibility
	 * of the JFrame.
	 * </p>
	 */
	public IntroView() {
		// grab all images
		characterImages.put("platform", new PlatformImage());
		characterImages.put("ground", new GroundImage());
		characterImages.put("greenCrab", new CrabImage());
		characterImages.put("osprey", new OspreyImage());
		characterImages.put("collectible", new CollectibleImage());
		characterImages.put("fact", new FactImage());
		characterImages.put("chest", new ChestImage());

		// set up background and add view object to the frame
		try {
			File file = new File("images/world/Background.jpg");
			BufferedImage img = ImageIO.read(file);
			BackgroundPanel background = new BackgroundPanel(img, BackgroundPanel.SCALED, 0.50f, 0.5f);
			frame.setContentPane(background);
			background.add(this);
		} catch (IOException except) {
			System.out.println("Cannot Read Image File: " + except);
			except.printStackTrace();
		}

		// set up the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Questary Beta");
		// scale frame to screen's dimensions, and set location
		setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		// make frame visible
		frame.setVisible(true);

	}

	// *************************************************
	// Methods

	/**
	 * Draws the characters, ground, and platform on the screen
	 * 
	 * @param g
	 *            - Is a Graphics object that draws everything
	 */
	@Override
	public void paint(Graphics g) {
		// display stuff here for separate rooms
		int stringX = (int)(screenWidth * 0.1);
		int stringY = (int)(screenHeight * 0.25);
		switch (introRoomNum) {
			case 0:
				g.setColor(Color.cyan);
				g.setFont(new Font("Garamond", Font.BOLD, 50));
				g.drawString("Use LEFT & RIGHT ARROW KEY to move, UP KEY to jump", stringX, stringY);
			break;
			case 1: 
				g.setColor(Color.CYAN);
				g.setFont(new Font("Garamond", Font.BOLD, 50));
				g.drawString("Press DOWN AND RIGHT to change characters", stringX, stringY);
				g.drawString("Use the BIRD character to JUMP HIGHER", stringX + 50, stringY + 50);
			break;
			case 2: 
				g.setColor(Color.CYAN);
				g.setFont(new Font("Garamond", Font.BOLD, 50));
				g.drawString("Collect HORSESHOE CRABS to see a FACT and INCREASE your SCORE", stringX, stringY);
				g.drawString("After 3 COLLECTIBLES, go to the CHEST to ANSWER A QUESTION", stringX + 50, stringY + 50);
			break;
			case 3: 
				g.setColor(Color.CYAN);
				g.setFont(new Font("Garamond", Font.BOLD, 50));
				g.drawString("WATCH OUT for the OSPREY", stringX, stringY);
				g.drawString("JUMP on the GREEN EUROPEAN CRAB, but DON'T GET TOUCHED", stringX + 50, stringY + 50);
				g.drawString("START GAME ->", (int)(screenWidth * 0.7), (int)(screenHeight * 0.8));
			break;
					
		}
		
		if (!changeCharacterMode) {
	
			// paint character image
			if (playerCharacter == "researcher") {
				g.drawImage(researcherImage.show(direct), playerX, playerY, researcherImage.getWidth(),
						researcherImage.getHeight(), this);
			} else if (playerCharacter == "bird") {
				g.drawImage(birdImage.show(direct), playerX, playerY, birdImage.getWidth(), birdImage.getHeight(),
						this);
			}
			// paint platform images
			if(!platforms.isEmpty()) {
				for (Platform platform : platforms) {
					g.setColor(Color.GRAY);
					g.fillRect((int) platform.getX(), (int) platform.getY(), (int) platform.getWidth(),
							(int) platform.getHeight());
					ImageObject platformImg = characterImages.get(platform.getName());
					g.drawImage(platformImg.show(0), (int) platform.getX(), (int) platform.getY(),
							(int) platform.getWidth(), (int) platform.getHeight(), this);
				}
			}
			if (!enemies.isEmpty()) {
				// paint enemies
				for (Enemy enemy : enemies) {
					// g.drawRect((int)enemy.getX(), (int)enemy.getY(), (int)enemy.getWidth(),
					// (int)enemy.getHeight());
					ImageObject enemyImg = characterImages.get(enemy.getName());
					int direct = enemy.getDirection();
					int imgX = (int) enemy.getX() - ((enemyImg.getWidth() - (int) enemy.getWidth()) / 2);
					int imgY = (int) enemy.getY() - ((enemyImg.getHeight() - (int) enemy.getHeight()) / 2);
					g.drawImage(enemyImg.show(direct), imgX, imgY, enemyImg.getWidth(), enemyImg.getHeight(), this);
				}
			}
			

			// paint ground
			g.setColor(Color.GRAY);
			g.fillRect((int) ground.getX(), (int) ground.getY(), (int) ground.getWidth(), (int) ground.getHeight());
			ImageObject groundImg = characterImages.get(ground.getName());
			g.drawImage(groundImg.show(0), (int) ground.getX(), (int) ground.getY(), (int) ground.getWidth(),
					(int) ground.getHeight(), this);

			// paint collectibles
			if(!collectibles.isEmpty()) {
				for (Collectible collectible : collectibles) {
					ImageObject collectibleImg = characterImages.get(collectible.getName());
					g.drawImage(collectibleImg.show(0), (int) collectible.getX(), (int) collectible.getY(),
							(int) collectible.getWidth(), (int) collectible.getHeight(), this);
				}
			}
			

			// paint collected
			if(!collected.isEmpty()) {
				for (Collectible collectible : collected) {
					ImageObject collectedImg = characterImages.get(collectible.getName());
					g.drawImage(collectedImg.show(0), (int) collectible.getX(), (int) collectible.getY(),
							(int) collectible.getWidth(), (int) collectible.getHeight(), this);
				}
			}
			

			// paint facts
			Fact.setxLoc(GameTimeBarXPos + 25);
			Fact.setyLoc(GameTimeBarYPos + GameTimeBarHeight);
			if(!facts.isEmpty()) {
				for (Fact fact : facts) {
					ImageObject factImg = characterImages.get(fact.getName());
					g.drawImage(factImg.show(Fact.getPicIter()), (int) fact.getX(), (int) fact.getY(),
							(int) fact.getWidth(), (int) fact.getHeight(), this);
				}
			}
			

			// paint chests
			if(!chests.isEmpty()) {
				for (Chest chest : chests) {
					ImageObject chestImg = characterImages.get(chest.getName());
					g.drawImage(chestImg.show(0), (int) chest.getX(), (int) chest.getY(), (int) chest.getWidth(),
							(int) chest.getHeight(), this);
					chestStatus = chest.getIsOpen();
				}
			}
			
			// if game is in change character mode
		} else if (changeCharacterMode) {
			try {
				ChangeCharacterResearcher = ImageIO.read(new File("images/changeCharacter/Change_Researcher.png"));
				ChangeCharacterBird = ImageIO.read(new File("images/changeCharacter/Change_Bird.png"));
			} catch (IOException e) {
				System.out.println("Error with file upload");
				e.printStackTrace();
			}
			// if researcher is selected
			if (playerCharacter == "researcher") {
				g.drawImage(ChangeCharacterResearcher, (int) screenWidth / 2 - ChangeCharacterMenuWidth / 2,
						(int) screenHeight / 2 - ChangeCharacterMenuHeight / 2, ChangeCharacterMenuWidth,
						ChangeCharacterMenuHeight, this);
				// if bird is selected
			} else {
				g.drawImage(ChangeCharacterBird, (int) screenWidth / 2 - ChangeCharacterMenuWidth / 2,
						(int) screenHeight / 2 - ChangeCharacterMenuHeight / 2, ChangeCharacterMenuWidth,
						ChangeCharacterMenuHeight, this);
			}
		}
	}

	/**
	 * This method updates the View's player's coordinates, direction, and player's
	 * state. Afterwards it repaints the image.
	 * 
	 * @param playerX
	 *            - Player's x coordinate
	 * @param playerY
	 *            - Player's y coordinate
	 * @param direct
	 *            - Player's direction
	 * @param playerCharacter
	 *            - Player's state (researcher/bird)
	 * @param healthLeft
	 *            - Player's current health
	 */
	public void updateView(int playerX, int playerY, int direct, String playerCharacter, int healthLeft) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.direct = direct;
		this.playerCharacter = playerCharacter;
		health = healthLeft;
		ImageObject platformImage = characterImages.get("platform");
		platformImage.nextImage(animate);
		ImageObject groundImage = characterImages.get("ground");
		groundImage.nextImage(animate);
		ImageObject greenCrabImage = characterImages.get("greenCrab");
		greenCrabImage.nextImage(animate);
		ImageObject ospreyImage = characterImages.get("osprey");
		ospreyImage.nextImage(animate);
		ImageObject collectibleImage = characterImages.get("collectible");
		collectibleImage.nextImage(animate);
		ImageObject factImage = characterImages.get("fact");
		factImage.nextImage(factTime);
		ImageObject chestImage = characterImages.get("chest");
		chestImage.nextImage(chestStatus);
		frame.repaint();
	}

	// *************************************************
	// Question Methods

	/**
	 * Creates the question frame
	 * 
	 * @param q
	 *            - Question object being used
	 * @return questionFrame - JFrame that has the question being asked
	 */
	public JFrame createQuestionFrame(Question q) {
		questionFrame = new JFrame(q.getName());
		int frameWidth = 500;
		int frameHeight = 250;
		questionFrame.setSize(frameWidth, frameHeight);
		questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set the location of the questionFrame
		int x = (int) (screenWidth / 2) - (frameWidth / 2);
		int y = (int) (screenHeight / 2) - (frameHeight / 2);
		questionFrame.setLocation(x, y);

		// add the question label onto the frame
		JLabel questionLabel = new JLabel(q.getQuestion());
		questionLabel.setHorizontalAlignment(JLabel.LEFT);
		questionLabel.setHorizontalAlignment(JLabel.CENTER);

		questionBox = Box.createVerticalBox();
		questionBox.add(questionLabel);

		JLabel label2 = new JLabel("After a delay, use UP and DOWN key to move and RIGHT key to select");
		label2.setHorizontalAlignment(JLabel.LEFT);
		label2.setHorizontalAlignment(JLabel.CENTER);

		questionBox.add(label2);

		JRadioButton a1 = new JRadioButton(q.getA1());
		a1.setSelected(true);
		buttons.add(a1);
		questionBox.add(a1);
		JRadioButton a2 = new JRadioButton(q.getA2());
		buttons.add(a2);
		questionBox.add(a2);
		JRadioButton a3 = new JRadioButton(q.getA3());
		buttons.add(a3);
		questionBox.add(a3);

		questionBox.setVisible(true);
		questionFrame.add(questionBox);

		questionFrame.setVisible(true);
		return questionFrame;
	}

	// implemented with arrow keys to visually display the selected options
	/**
	 * Updates the question frame
	 * 
	 * @param index
	 *            - Location of the player's answer choice
	 */
	public void updateQuestion(int index) {
		JRadioButton selectedButton = buttons.get(index);
		selectedButton.setSelected(true);

		Iterator<JRadioButton> btnIter = buttons.iterator();
		while (btnIter.hasNext()) {
			JRadioButton btn = btnIter.next();
			if (!(btn.equals(selectedButton))) {
				btn.setSelected(false);
			}
		}
		(buttons.get(index)).setSelected(true);
	}

	/**
	 * Displays if the correct answer is chosen
	 * 
	 * @param q
	 *            - Question object to be used
	 */
	public void displayCorrect(Question q) {
		removeButtons();
		questionBox.setVisible(false);

		Box correctBox = Box.createVerticalBox();
		JLabel question = new JLabel(q.getQuestion());
		JLabel correctMessage = new JLabel(" CORRECT");
		correctMessage.setHorizontalAlignment(JLabel.CENTER);
		correctMessage.setFont(new Font("Serif", Font.BOLD, 30));
		JLabel healthMessage = new JLabel("Congradulations you get an extra health\n");
		JLabel continueMessage = new JLabel("Press the Right Key to continue your adventure");
		correctBox.add(question);
		correctBox.add(correctMessage);
		correctBox.add(healthMessage);
		correctBox.add(continueMessage);
		correctBox.setVisible(true);

		questionFrame.add(correctBox);

	}

	/**
	 * Displays if the wrong answer is chosen
	 * 
	 * @param q
	 *            - Question object to be used
	 */
	public void displayWrong(Question q) {
		removeButtons();
		questionBox.setVisible(false);

		Box wrongBox = Box.createVerticalBox();
		JLabel wrong = new JLabel("WRONG");
		wrong.setFont(new Font("Serif", Font.BOLD, 30));
		JLabel question = new JLabel(q.getQuestion());
		JLabel message = new JLabel("The right answer is: ");
		JLabel correctAnswer = new JLabel(q.getCorrectAnswer());
		wrongBox.add(wrong);
		wrongBox.add(question);
		wrongBox.add(message);
		wrongBox.add(correctAnswer);
		wrongBox.add(new JLabel("Press the Right Key to continue your adventure"));

		wrongBox.setVisible(true);

		questionFrame.add(wrongBox);
	}

	/**
	 * Makes the buttons on the question screen invisible after the question frame
	 * is done being used
	 */
	private void removeButtons() {
		// set each JRadioButton to invisible
		Iterator<JRadioButton> btnIter = buttons.iterator();
		while (btnIter.hasNext()) {
			JRadioButton btn = btnIter.next();
			btn.setVisible(false);
			btnIter.remove();
		}
	}

	/**
	 * Gets rid of the question frame when it is done being used
	 */
	public void TurnOffQuestionFrame() {
		questionFrame.removeAll();
		questionFrame.setVisible(false);
	}

	// *************************************************
	// Getters

	/**
	 * Getter for the frame of the View
	 * 
	 * @return JFrame - the frame in the view
	 */
	public JFrame getFrame() {
		return frame;
	}


	// *************************************************
	// Setters
	
	/**
	 * sets the intro room number
	 *
	 * @return int - room number of Intro
	 */
	public void setIntroRoomNum(int introRoomNum) {
		this.introRoomNum = introRoomNum;
	}

	/**
	 * This method sets game view into change character mode
	 * 
	 */
	public void changeCharacterMode() {
		changeCharacterMode = !changeCharacterMode;
	}

	/**
	 * This method sets game view into game over mode
	 * 
	 */
	public void gameOverMode() {
		gameOverMode = true;
	}

	/**
	 * Sets the game High Score
	 * 
	 * @param highScore
	 *            - Sets the highScore to String highScore
	 */
	public void setHighScore(String highScore) {
		this.highScore = highScore;
	}

	/**
	 * Sets the players current Score
	 * 
	 * @param score
	 *            - Sets the Score to String Score
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * Sets the current GameTime
	 * 
	 * @param gameTimeLeft
	 *            - Sets the gameTimeLeft to int gameTimeLeft
	 */
	public void setGameTime(int gameTimeLeft) {
		this.gameTimeLeft = gameTimeLeft;
	}

	/**
	 * After seeing if the character is a bird or researcher, it will increment the
	 * researcher or bird image. It will only increment if animate is true\
	 * 
	 * @see ResearcherImage#nextImage(boolean)
	 * @see BirdImage#nextImage(boolean)
	 */
	public void setPicNum() {
		if (playerCharacter == "researcher") {
			researcherImage.nextImage(animate);
		} else if (playerCharacter == "bird") {
			birdImage.nextImage(animate);
		}

	}

	/**
	 * Determines if the player should be animated or not. If animate is false then
	 * there would be not animation.
	 * 
	 * @param b
	 *            - Sets the animate value to boolean b
	 */
	public void setAnimation(boolean b) {
		animate = b;
	}

	/**
	 * Sets the View.platform1 to the playerform1 passed in, to be drawn by another
	 * method
	 * 
	 * @param platformList
	 *            - The platforms the player will be interacting with
	 */
	public void setPlatforms(ArrayList<Platform> platformList) {
		platforms = platformList;
	}

	/**
	 * Sets the View.ground to the ground passed in, to be drawn in another method
	 * 
	 * @param ground
	 *            - The ground the player will be standing on
	 */
	public void setGroundImage(Ground ground) {
		this.ground = ground;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void setCollectibles(ArrayList<Collectible> collectibles) {
		this.collectibles = collectibles;
	}

	public void setCollected(ArrayList<Collectible> collected) {
		this.collected = collected;
	}

	public void setFacts(ArrayList<Fact> facts) {
		this.facts = facts;
	}

	public void setChests(ArrayList<Chest> chests) {
		this.chests = chests;
	}
}
