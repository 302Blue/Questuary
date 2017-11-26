package view;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.dynamic.ResearcherImage;
import view.fixed.ChestImage;
import view.fixed.CollectibleImage;
import view.dynamic.BirdImage;
import view.dynamic.*;

import model.dynamic.*;
import model.fixed.Chest;
import model.fixed.Collectible;
import model.fixed.Platform;

/**
 * Class in charge of drawing the images
 * <p>
 * The View class extends the JPanel that is later added to JFrame. This class
 * holds JFrame frame for the JFrame object. The HashMap characterImages holds
 * all ImageObject objects. researcherImage holds an image of the researcher.
 * BirdImage holds an image of the bird. It has a boolean animate variable used
 * to increment the images. imgWidth and imgHeight determines the image
 * dimensions. PlayerX and PlayerY contains the player's coordinates, with
 * direct determining which way the player is facing. The Rectangle ground and
 * platform help draw the ground and platform.
 * 
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 *
 */
public class View extends JPanel {

	// *************************************************
	// Fields

	private JFrame frame = new JFrame();
	// HashMap used for character change
	HashMap<String, ImageObject> characterImages = new HashMap<String, ImageObject>();
	// researcher image object that will be responsible in returning the researcher
	// image
	ResearcherImage researcherImage = new ResearcherImage();
	// Bird image object responsible for bird image
	BirdImage birdImage = new BirdImage();
	// boolean determines player should animate
	boolean animate = false;
	// get the screen's dimensions
	final private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final private double screenRatio = screenWidth / screenHeight;
	// image dimensions
	private int imgWidth;
	private int imgHeight;
	// player's x and y coordinates
	int playerX = 0;
	int playerY = 0;
	// player's starting direction and character
	int direct = 1;
	String playerCharacter = "researcher";
	// ground
	Rectangle ground;
	Rectangle platform1;
	// list of platforms
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	// list of enemies
	ArrayList<Enemy> enemies;
	// list of hearts
	ArrayList<HeartImage> hearts = new ArrayList<HeartImage>(3);
	int numHearts = 3;
	// list of collectibles & chests
	ArrayList<Collectible> collectibles;
	ArrayList<Collectible> collected;
	ArrayList<Chest> chests;
	// chest open/closed
	boolean chestStatus;
	// game modes
	boolean changeCharacterMode = false;
	boolean gameOverMode = false;
	// change character menu
	Image ChangeCharacterResearcher;
	Image ChangeCharacterBird;
	Image GameOverScreen;
	final private int ChangeCharacterMenuHeight = 500;
	final private int ChangeCharacterMenuWidth = 700;
	final private int GameOverScreenHeight = (int)screenHeight - 100;
	final private int GameOverScreenWidth = (int) screenWidth- 100;
	final private int GameOverScreenXPos = 50;
	final private int GameOverScreenYPos = 25;
	public String highScore = "";
	public String score = "";

	// *************************************************
	// Constructor

	/**
	 * Constructor used to create a view object
	 * <p>
	 * This constructor puts researcher image and bird image into the characterImage
	 * hashmap. Creates a JFrame object and sets the title, dimensions, visibility
	 * of the JFrame.
	 */
	public View() {
		// grab all images
		characterImages.put("researcher", researcherImage);
		characterImages.put("bird", birdImage);
		characterImages.put("greenCrab", new CrabImage());
		characterImages.put("osprey", new OspreyImage());
		characterImages.put("collectible", new CollectibleImage());
		characterImages.put("chest", new ChestImage());

		// set up background and add view object to the frame
		try {
			File file = new File("images/estuary/Background.jpg");
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
		frame.setTitle("Questary Alpha");
		// scale frame to screen's dimensions, and set location
		setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		// make frame visible
		frame.setVisible(true);

		// make three hearts
		for (int i = 0; i < 3; i++) {
			hearts.add(new HeartImage((int) (screenWidth - ((i+1) * 50)), 0));
		}

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
		// paint character image
		if (!changeCharacterMode && !gameOverMode) {
			if (playerCharacter == "researcher") {
				g.drawImage(researcherImage.show(direct), playerX, playerY, researcherImage.getWidth(),
						researcherImage.getHeight(), this);
			} else if (playerCharacter == "bird") {
				g.drawImage(birdImage.show(direct), playerX, playerY, birdImage.getWidth(), birdImage.getHeight(),
						this);
			}
			// paint ground
			g.setColor(Color.gray);
			g.fillRect((int) ground.getX(), (int) ground.getY(), (int) ground.getWidth(), (int) ground.getHeight());

			// paint platform images
			for (Platform platform : platforms) {
				g.fillRect((int) platform.getX(), (int) platform.getY(), (int) platform.getWidth(),
						(int) platform.getHeight());
			}

			// paint enemies
			for (Enemy enemy : enemies) {
				g.setColor(Color.red);
				g.fillRect((int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(), (int) enemy.getHeight());
				ImageObject image = characterImages.get(enemy.getName());
				int direct = enemy.getDirection();
				g.drawImage(image.show(direct), (int) enemy.getX(), (int) enemy.getY(), (int) enemy.getWidth(),
						(int) enemy.getHeight(), this);
			}

			// paint hearts
			for (int i = 0; i < numHearts; i++) {
				HeartImage h = hearts.get(i);
				g.setColor(Color.red);
				g.fillOval(h.getX(), h.getY(), h.getImgWeight(), h.getImgHeight());
			}

			// paint collectibles
			for (Collectible collectible : collectibles) {
				g.setColor(Color.BLUE);
				g.fillRect((int) collectible.getX(), (int) collectible.getY(), (int) collectible.getWidth(),
						(int) collectible.getHeight());
				ImageObject image = characterImages.get(collectible.getName());
				g.drawImage(image.show(0), (int) collectible.getX(), (int) collectible.getY(),
						(int) collectible.getWidth(), (int) collectible.getHeight(), this);
			
			}
			
			//paint collected
			for (Collectible collectible : collected) {
				g.setColor(Color.BLUE);
				g.fillRect((int) collectible.getX(), (int) collectible.getY(), (int) collectible.getWidth(),
						(int) collectible.getHeight());
				ImageObject image = characterImages.get(collectible.getName());
				g.drawImage(image.show(0), (int) collectible.getX(), (int) collectible.getY(),
						(int) collectible.getWidth(), (int) collectible.getHeight(), this);
			}

			// paint chests
			for (Chest chest : chests) {
				g.setColor(Color.GREEN);
				g.fillRect((int) chest.getX(), (int) chest.getY(), (int) chest.getWidth(),
						(int) chest.getHeight());
				ImageObject image = characterImages.get(chest.getName());
				g.drawImage(image.show(0), (int) chest.getX(), (int) chest.getY(),
						(int) chest.getWidth(), (int) chest.getHeight(), this);
				chestStatus = chest.getIsOpen();
			}
			
		// if game is in change character mode
		} else if (changeCharacterMode && !gameOverMode) {
			try {
				ChangeCharacterResearcher = ImageIO
						.read(new File("images/Change Character Menu/Change_Researcher.png"));
				ChangeCharacterBird = ImageIO.read(new File("images/Change Character Menu/Change_Bird.png"));
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
			
		// if game is done
		} else if (gameOverMode) {
			// g.drawImage(researcherImage.show(direct), 500, 500, 700, 700, this);
			//g.setFont(new Font("TimesRoman", Font.PLAIN,100));
			//g.drawString("Game Over", 500, 500);
			try {
				GameOverScreen = ImageIO
						.read(new File("images/Game Over/GameOver.png"));
			} catch (IOException e) {
				System.out.println("Error with file upload");
				e.printStackTrace();
			}
			
			g.drawImage(GameOverScreen, GameOverScreenXPos,GameOverScreenYPos, GameOverScreenWidth,GameOverScreenHeight, this);
			
			g.setFont(new Font("Comic Sans MS", Font.PLAIN,85));
			g.drawString(highScore, 625, 440);
			g.drawString(score, 625, 310);
			
		}
		
		/*// old paint hearts function
		 * for(int i = 0; i < numHearts; i++) { Heart h = hearts.get(i);
		 * g.setColor(Color.red); g.fillOval(h.getX(), h.getY(), h.getImgWeight(),
		 * h.getImgHeight());
		 * 
		 * }
		 */
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
	 */
	public void updateView(int playerX, int playerY, int direct, String playerCharacter, int healthLeft) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.direct = direct;
		this.playerCharacter = playerCharacter;
		numHearts = healthLeft;
		ImageObject greenCrabImage = characterImages.get("greenCrab");
		greenCrabImage.nextImage(true);
		ImageObject ospreyImage = characterImages.get("osprey");
		ospreyImage.nextImage(true);
		ImageObject collectibleImage = characterImages.get("collectible");
		collectibleImage.nextImage(true);
		ImageObject chestImage = characterImages.get("chest");
		chestImage.nextImage(chestStatus);
		frame.repaint();
	}

	/**
	 * This method puts game view into change character mode
	 * 
	 */
	public void changeCharacterMode() {
		changeCharacterMode = !changeCharacterMode;
	}

	/**
	 * This method puts game view into game over mode
	 * 
	 */
	public void gameOverMode() {
		gameOverMode = true;
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
	/**
	 * Getter for the users name
	 * 
	 * @return String - name of the user
	 */
	public String  getName() {
		return JOptionPane.showInputDialog("You set a new highscore. What is your name?");
	}

	/**
	 * Getter for the player image's width
	 * 
	 * @return int - the image's width
	 */
	public int getImgWidth() {
		return imgWidth;
	}

	/**
	 * Getter for the player image's height
	 * 
	 * @return int - the image's height
	 */
	public int getImgHeight() {
		return imgHeight;
	}

	// *************************************************
	// Setters
	
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
	 *            - Sets the score to String score
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * Sets the View.ground to the ground passed in, to be drawn in another method
	 * 
	 * @param ground
	 *            - The ground the player will be standing on
	 */
	public void setGroundImage(Rectangle ground) {
		this.ground = ground;
	}

	/**
	 * Sets the View.platform1 to the playerform1 passed in, to be drawn by another
	 * method
	 * 
	 * @param platform1
	 *            - The platform the player will be interacting with
	 */
	/*
	 * public void setPlatformImage(Rectangle platform1) { this.platform1 =
	 * platform1; }
	 */
	public void setPlatformImage(ArrayList<Platform> platformList) {
		this.platforms = platformList;
	}

	/**
	 * After seeing if the character is a bird or researcher, it will increment the
	 * researcher or bird image. It will only increment if animate is true
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
	// setter for animation
	public void setAnimation(boolean b) {
		animate = b;
	}

	public void setEnemies(ArrayList<Enemy> e) {
		enemies = e;
	}

	public void setCollectibles(ArrayList<Collectible> collect) {
		collectibles = collect;
	}
	
	public void setCollected(ArrayList<Collectible> collect) {
		collected = collect;
	}
	
	public void setChests(ArrayList<Chest> chest) {
		chests = chest;
	}
}
