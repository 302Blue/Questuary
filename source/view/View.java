package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JPanel;
import view.dynamic.CatImage;
import view.dynamic.BirdImage;
import view.dynamic.*;

import model.dynamic.*;
import model.fixed.Platform;

/**
 * Class in charge of drawing the images
 * <p>
 * The View class extends the JPanel that is later added to JFrame. This class
 * holds JFrame frame for the JFrame object. The HashMap characterImages holds
 * all ImageObject objects. CatImage holds an image of the cat. BirdImage holds
 * an image of the bird. It has a boolean animate variable used to increment the
 * images. imgWidth and imgHeight determines the image dimensions. PlayerX and
 * PlayerY contains the player's coordinates, with direct determining which way
 * the player is facing. The Rectangle ground and platform help draw the ground
 * and platform.
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
	// Cat image object that will be responsible in returning the cat image
	CatImage catImage = new CatImage();
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
	// player's direction
	int direct = 1;
	// ground
	Rectangle ground;
	Rectangle platform1;
	//list of platforms
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	String playerCharacter = "cat";
	ArrayList<Enemy> enemies;
	ArrayList<Heart> hearts = new ArrayList<Heart>(3);
	int numHearts = 3;
	// *************************************************
	// Constructor

	/**
	 * Constructor used to create a view object
	 * <p>
	 * This constructor puts cat image and bird image into the characterImage
	 * hashmap. Creates a JFrame object and sets the title, dimensions, visibility
	 * of the JFrame.
	 */
	public View() {
		characterImages.put("cat", catImage);
		characterImages.put("bird", birdImage);
		characterImages.put("greenCrab", new CrabImage());
		characterImages.put("osprey", new OspreyImage());

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

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Questary Alpha");
		// scale frame to screen's dimensions, and set location
		setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setSize(new Dimension((int) screenWidth, (int) screenHeight));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		// make frame visible
		frame.setVisible(true);

		//make three hearts
		
		for(int i = 0; i < 3; i++)
		{
			hearts.add(new Heart(i*50,0));
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

		if (playerCharacter == "cat") {
			g.drawImage(catImage.show(direct), playerX, playerY, catImage.getWidth(), catImage.getHeight(), this);
		} else if (playerCharacter == "bird") {
			g.drawImage(birdImage.show(direct), playerX, playerY, birdImage.getWidth(), birdImage.getHeight(), this);
		}
		g.setColor(Color.blue);
		g.fillRect(playerX, playerY, imgWidth, imgHeight);
		g.setColor(Color.gray);
		g.fillRect((int) ground.getX(), (int) ground.getY(), (int) ground.getWidth(), (int) ground.getHeight());
		/*g.fillRect((int) platform1.getX(), (int) platform1.getY(), (int) platform1.getWidth(),
				(int) platform1.getHeight());*/
		
		for(Platform platform : platforms)
		{
			g.fillRect((int) platform.getX(), (int) platform.getY(), (int) platform.getWidth(),
					(int) platform.getHeight());
		}
		
		for(Enemy enemy: enemies)
		{
			g.setColor(Color.red);
			g.fillRect((int)enemy.getX(), (int)enemy.getY(), (int)enemy.getWidth(), (int)enemy.getHeight());
			ImageObject image = characterImages.get(enemy.getName());
			int direct = enemy.getDirection();
			g.drawImage(image.show(direct), (int)enemy.getX(), (int)enemy.getY(), (int)enemy.getWidth(), (int)enemy.getHeight(), this);
			//g.setColor(Color.red);
			//g.fillRect((int)enemy.getX(), (int)enemy.getY(), (int)enemy.getWidth(), (int)enemy.getHeight());
		}

		for(int i = 0; i < numHearts; i++)
		{
			Heart h = hearts.get(i);
			g.setColor(Color.red);
			g.fillOval(h.getX(), h.getY(), h.getImgWeight(), h.getImgHeight());
		}
	}

	public void paint2(Graphics g) {
		g.setColor(Color.blue);

		if (playerCharacter == "cat") {
			g.drawImage(catImage.show(direct), playerX, playerY, catImage.getWidth(), catImage.getHeight(), this);
		} else if (playerCharacter == "bird") {
			g.drawImage(birdImage.show(direct), playerX, playerY, birdImage.getWidth(), birdImage.getHeight(), this);
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
	 *            - Player's state (cat/bird)
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
		frame.repaint();
	}

	
	
	// *************************************************
	// Getters

	/**
	 * Getter for the frame of the View
	 * 
	 * @return Jframe - the frame in the view
	 */
	public JFrame getFrame() {
		return frame;
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
	/*public void setPlatformImage(Rectangle platform1) {
		this.platform1 = platform1;
	}*/

	public void setPlatformImage(ArrayList<Platform> platformList) {
		this.platforms = platformList;
	}
	
	/**
	 * After seeing if the character is a bird or cat, it will increment the cat or
	 * bird image. It will only increment if animate is true
	 */
	public void setPicNum() {
		if (playerCharacter == "cat") {
			catImage.nextImage(animate);
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
}
