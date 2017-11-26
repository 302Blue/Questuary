package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.dynamic.Player;
import model.dynamic.Enemy;
import model.dynamic.EnemyCrab;
import model.dynamic.EnemyOsprey;
import model.fixed.Chest;
import model.fixed.Collectible;
import model.fixed.Platform;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Model {

	// *************************************************
	// Fields

	final private int startingXOffSet = 192;
	// get the screen's dimensions
	final private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final private double screenRatio = screenWidth / screenHeight;
	// boundaries
	private int xBoundary;
	private int yBoundary;
	// needed fields
	private Platform ground;
	private Platform platform1;
	private Platform p1;
	private Platform p2;
	private Platform p3;
	private Platform p4;
	private Platform p5;

	// arraylist containing the platform objects
	private ArrayList<Platform> platforms = new ArrayList<Platform>();

	int groundOffSet = 100;
	private Player player;
	// fixed gravity constant
	private int gravity = 10;
	// fields for changing the character
	private boolean changeCharacterMode = false;
	private boolean isGamePaused = false;
	private boolean isGameOver = false;
	private int changeCharacterCount = 0;
	// starting positions
	private int startingY;
	private int startingX;

	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Collectible> collectibles = new ArrayList<Collectible>(3);
	private ArrayList<Collectible> collected = new ArrayList<Collectible>();
	private ArrayList<Chest> chests = new ArrayList<Chest>(1);
	private int numCollected = -1;
	private boolean chestCreated = false;
	private String highScore = "";
	private String name = "";

	// *************************************************
	// Constructor

	/**
	 * Constructor for Model
	 */
	public Model() {

		int playerWidth = (int) (screenWidth * 0.1);
		int playerHeight = (int) (playerWidth * screenRatio);

		startingY = (int) (screenHeight - groundOffSet - playerHeight);
		startingX = playerWidth - startingXOffSet;
		// set the boundaries
		xBoundary = (int) screenWidth - playerWidth;
		yBoundary = (int) screenHeight - playerHeight;

		// create Player object
		player = new Player(startingX, startingY, ((int) (playerWidth * .75)), playerHeight, gravity);
		// create Ground object
		ground = new Platform(-500, (int) (screenHeight - groundOffSet), (int) (screenWidth * 2), groundOffSet);
		platform1 = new Platform((int) ThreadLocalRandom.current().nextInt(300, 1000),
				(int) ThreadLocalRandom.current().nextInt(765, 900), 350, 50);

		// make 5 platforms
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				p1 = new Platform((int) ThreadLocalRandom.current().nextInt(300, 400),
						(int) ThreadLocalRandom.current().nextInt(765, 900), 350, 50);
				platforms.add(p1);
			} else if (i == 1) {
				p2 = new Platform((int) (p1.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p1.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p2);
			} else if (i == 2) {
				p3 = new Platform((int) (p2.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p2.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p3);
			} else if (i == 3) {
				p4 = new Platform((int) (p3.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p3.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p4);
			} else if (i == 4) {
				p5 = new Platform((int) (p4.getX() + ThreadLocalRandom.current().nextInt(350, 500)),
						(int) (p4.getCenterY() + ThreadLocalRandom.current().nextInt(0, 300)
								- ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p5);
			}
		}

		// generate a collectible
		Random random = new Random(System.currentTimeMillis());
		int randomPlat = random.nextInt(3);
		collectibles.add(new Collectible(platforms.get(randomPlat)));
		// chests.add(new Chest(platforms.get(randomPlat)));
		System.out.println("First Collectible Created");

	}

	// *************************************************
	// Methods

	/**
	 * Moves player x and y coordinates w/ respective velocities
	 *
	 * @see Player#move()
	 */
	public void movePlayer() {
		if (!getIsGamePaused() && !getIsGameOver()) {
			player.move();
		}
	}

	public void moveEnemies() {
		if (!getIsGamePaused() && !getIsGameOver()) {
			Iterator<Enemy> enemyIter = enemies.iterator();
			while (enemyIter.hasNext()) {
				Enemy enemy = enemyIter.next();
				enemy.move();
				if (enemy.isDead() && (enemy.getY() > screenHeight)) {
					enemyIter.remove();
				}
			}
		}

	}

	/**
	 * Moves the player left
	 *
	 * @see Player#setDirection(int)
	 * @see Player#getX()
	 * @see Player#moveLeft()
	 * @see Player#setDxOff()
	 */
	public void playerMoveLeft() {
		player.setDirection(0);
		// check if player is going out of x bound
		if (player.getX() >= 0) {
			// System.out.println("Boundary invalid " + xBoundary);
			player.moveLeft();
			// System.out.println("ground" + ground.getWidth());
		}
		// if out of bound then don't increment the x
		else {
			System.out.println("Boundary invalid 0");
			System.out.println("Player is out of Boundary to Left");
			player.setDxOff();
		}
	}

	/**
	 * Moves the player left
	 *
	 * @see Player#setDirection(int)
	 * @see Player#getX()
	 * @see Player#moveRight()
	 * @see Player#setDxOff()
	 */
	public void playerMoveRight() {
		player.setDirection(1);
		// check if player is going out of x bound
		if (player.getX() <= xBoundary) {
			// System.out.println("Boundary invalid " + xBoundary);
			player.moveRight();
			// System.out.println("ground" + ground.getWidth());
		} else {
			System.out.println("Boundary invalid " + xBoundary);
			System.out.println("Player is out of Boundary to Right");
			player.setDxOff();
		}
	}

	/**
	 * Checks if the player is colliding
	 *
	 * @see Player#gravityEffect(Rectangle)
	 */
	public void gravity() {
		player.gravityEffect(ground);
	}

	// check each collision
	public void checkCollision() {
		checkCollisionPlatform();
		checkCollisionEnemy();
		checkCollisionCollectible();
		checkCollisionChest();
	}

	public void checkCollisionPlatform() {
		boolean isBottomCollide = false;
		platforms.add(ground);
		for (Platform platform : platforms) {
			if ((player.getBottomSide()).intersects(platform)) {
				player.setFalling(false);
				isBottomCollide = true;
			} else if ((player.getTopSide()).intersects(platform)) {
				player.setJumping(false);
			} else if (((player.getLeftSide()).intersects(platform)) && player.getDirection() == 0) {
				player.setDx(0);
			} else if (((player.getRightSide()).intersects(platform)) && player.getDirection() == 1) {
				player.setDx(0);
			}
		}

		if (!isBottomCollide) {
			player.setFalling(true);
		}
		platforms.remove(ground);
	}

	// if player collided with the enemy
	public void checkCollisionEnemy() {
		for (Enemy enemy : enemies) {
			if (enemy.isKillable()) {
				if ((player.getBottomSide()).intersects(enemy) && player.isAbleToAttack()) {
					player.setLocation((int) player.getX(), (int) player.getY() - 25);
					enemy.setIsDead();
					// continue;
				}
			}

			if ((!enemy.isDead()) && enemy.intersects(player) && !(enemy.getHasAttacked())) {
				enemy.damage(player);
				enemy.setHasAttacked(true);

				if (!(this.horizontalCollision(enemy))) {
					// if enemy hit left side of player
					if (enemy.intersects(player.getLeftSide())) {
						int x = ((int) player.getX()) + 100;
						int y = (int) player.getY();
						player.setLocation(x, y);
					}
					// if enemy hit right side of player
					else if (enemy.intersects(player.getRightSide())) {
						int x = ((int) player.getX()) - 100;
						int y = (int) player.getY();
						player.setLocation(x, y);
					}
				}
			} else if (!enemy.intersects(player)) {
				enemy.setHasAttacked(false);
			}
		}
	}

	private boolean horizontalCollision(Enemy enemy) {
		for (Platform platform : platforms) {
			// if player hit left side of platform and enemy is facing right
			if ((platform.getLeft()).intersects(player) && enemy.getDirection() == 1) {
				return true;
			}
			// if player hit right side of platform and enemy is facing left
			else if ((platform.getRight()).intersects(player) && (enemy.getDirection() == 0)) {
				return true;
			}
		}
		return false;
	}

	private void checkCollisionCollectible() {
		for (Iterator<Collectible> collectIter = collectibles.iterator(); collectIter.hasNext();) {
			Collectible c = collectIter.next();
			if (player.intersects(c)) {
				numCollected++;
				collected.add(new Collectible(numCollected));
				player.incrementScore(5);
				System.out.println("Score: " + player.getScore());
				// TODO: display collectible fact
				collectIter.remove();
			}
		}
	}

	private void checkCollisionChest() {
		for (Iterator<Chest> chestIter = chests.iterator(); chestIter.hasNext();) {
			Chest c = chestIter.next();
			if (player.intersects(c)) {
				for (int i = 0; i < 5; i++) {
					player.incrementScore(10);
				}
				c.setIsOpen(true);
				System.out.println("Score: " + player.getScore());
				// TODO: pause and display question/power-ups
			}
		}
	}

	/**
	 * Creates new platform once player leaves the room
	 *
	 * @see Player#getX()
	 * @see Player#setLocation(int, int)
	 */
	public void changeRoom() {
		if (player.getX() > xBoundary || player.getY() > yBoundary) {
			player.setLocation(startingX, startingY);
			createNewPlatform();
		}
	}

	/**
	 * Randomly generate a platform
	 *
	 */
	public void createNewPlatform() {
		platforms.clear();
		enemies.clear();
		collectibles.clear();
		chests.clear();

		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				p1 = new Platform((int) ThreadLocalRandom.current().nextInt(300, 400),
						(int) ThreadLocalRandom.current().nextInt(765, 900), 350, 50);
				platforms.add(p1);
			} else if (i == 1) {
				p2 = new Platform((int) (p1.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p1.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p2);
			} else if (i == 2) {
				p3 = new Platform((int) (p2.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p2.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p3);
			} else if (i == 3) {
				p4 = new Platform((int) (p3.getX() + ThreadLocalRandom.current().nextInt(350, 500)), (int) (p3.getY()
						+ ThreadLocalRandom.current().nextInt(0, 300) - ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p4);
			} else if (i == 4) {
				p5 = new Platform((int) (p4.getX() + ThreadLocalRandom.current().nextInt(350, 500)),
						(int) (p4.getCenterY() + ThreadLocalRandom.current().nextInt(0, 300)
								- ThreadLocalRandom.current().nextInt(0, 300)),
						350, 50);
				platforms.add(p5);
			}

			/*
			 * int xLoc = (int) ThreadLocalRandom.current().nextInt((int) screenWidth/8,
			 * (int) screenWidth - 200); int yLoc = (int)
			 * ThreadLocalRandom.current().nextInt((int) screenHeight/10, (int) screenHeight
			 * - 400); platform1 = new Platform(xLoc, yLoc, 350, 50);
			 * platforms.add(platform1);
			 */
		}

		// random crab & collectible generators
		Random random = new Random(System.currentTimeMillis());
		if ((numCollected % 3 == 0) && (numCollected > 0)) {
			int randomPlat = random.nextInt(4);
			chests.add(new Chest(platforms.get(randomPlat)));
			System.out.println("New Chest Created");
			chestCreated = true;
		}

		for (Platform platform : platforms) {
			int randomNum = random.nextInt(4);
			if (randomNum == 0) {
				enemies.add(new EnemyCrab(platform));
			} else if (randomNum == 1) {
				collectibles.add(new Collectible(platform));
				System.out.println("New Collectible Created");
			} else if (chestCreated) {
				collectibles.add(new Collectible(platform));
				chestCreated = false;
			}
		}

		enemies.add(new EnemyOsprey((int) screenWidth, (int) screenHeight));
	}

	/**
	 * Checks if current score is a new highscore
	 * 
	 * @return boolean - returns if there is a new highScore or not
	 * 
	 */

	public boolean isNewHighScore() {
		if (player.getScore() > Integer.parseInt(highScore.split(": ")[1])) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Updates the highScore if the current score is a new highScore
	 * 
	 */
	public void updateHighScore() {
		highScore = name + ": " + player.getScore();
		File scoreFile = new File("highscore.dat");
		if (!scoreFile.exists()) {
			try {
				scoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writeFile = null;
		BufferedWriter writer = null;
		try {
			writeFile = new FileWriter(scoreFile);
			writer = new BufferedWriter(writeFile);
			writer.write(this.highScore);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Model's main function for demonstrating game functionality
	 *
	 * @param args
	 *            - standard String array for a main function
	 */
	public static void main(String[] args) {
		System.out.println("Hello World");
		Model model = new Model();

		for (int i = 0; i < 10; i++) {
			System.out.println();
			model.incrementChangeCharacterCount();
			model.playerMoveRight();
			System.out.println("Current character is a " + model.getPlayerCharacter() + " and is moving "
					+ model.getPlayerDirectionString());

			for (int j = 0; j < 6; j++) {
				System.out.println("Player Coords: (" + model.getPlayerX() + ", " + model.getPlayerY() + ")");
				model.movePlayer();
				System.out.println("movePlayer() executed");
			}

			System.out.println();

			model.playerMoveLeft();
			System.out.println("Changing Character and Direction");
			model.incrementChangeCharacterCount();
			System.out.println("Current character is a " + model.getPlayerCharacter() + " and is facing "
					+ model.getPlayerDirectionString());

			System.out.println();

			for (int k = 0; k < 6; k++) {
				System.out.println("Player Coords: (" + model.getPlayerX() + ", " + model.getPlayerY() + ")");
				model.movePlayer();
				System.out.println("movePlayer() executed");
			}

		}
	}

	// *************************************************
	// Getters

	/**
	 * Returns the screen X-Boundary
	 *
	 * @return int - xBoundary specific to your screen size
	 */
	public int getXBoundary() {
		return xBoundary;
	}

	/**
	 * Returns the screen Y-Boundary
	 *
	 * @return int - yBoundary specific to your screen size
	 */
	public int getYBoundary() {
		return yBoundary;
	}

	/**
	 * Returns the value of the player image width
	 *
	 * @return double - Width of the player image
	 * @see Player#getWidth()
	 */
	public double getPlayerWidth() {
		return player.getWidth();
	}

	/**
	 * Returns the value of the player image height
	 *
	 * @return double - Height of the player image
	 * @see Player#getHeight()
	 */
	public double getPlayerHeight() {
		return player.getHeight();
	}

	/**
	 * Returns the value of the player X-Location
	 *
	 * @return int - Player's x coordinate
	 * @see Player#getX()
	 */
	public int getPlayerX() {
		return (int) player.getX();
	}

	/**
	 * Returns the value of the player Y-Location
	 *
	 * @return int - Player's y coordinate
	 * @see Player#getY()
	 */
	public int getPlayerY() {
		return (int) player.getY();
	}

	/**
	 * Returns a rectangle object that is the ground for the game
	 *
	 * @return Rectangle - Ground's Rectangle object
	 */

	public int getPlayerDx() {
		return player.getDx();
	}

	public int getPlayerDy() {
		return player.getDy();
	}

	public Rectangle getGround() {
		return ground;
	}

	/**
	 * Returns a platform's rectangle object for the game
	 *
	 * @return Rectangle - Platform1's rectangle object
	 */
	public Rectangle getPlatform() {
		return platform1;
	}

	// return the list of platforms
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	/**
	 * Returns a platform's platform object for the game
	 *
	 * @return Platform - Platform1's platform object
	 */
	public Platform getPlat() {
		return platform1;
	}

	/**
	 * Getter for current character mode
	 * 
	 * @return boolean - tells whether you are changing character or not
	 */
	public boolean getChangeCharacterMode() {
		return changeCharacterMode;
	}

	/**
	 * Getter for game paused state
	 * 
	 * @return boolean - tells whether game is over or not
	 */
	public boolean getIsGamePaused() {
		return isGamePaused;
	}

	/**
	 * Getter for game over state
	 * 
	 * @return boolean - tells whether game is paused or not
	 */
	public boolean getIsGameOver() {
		return isGameOver;

	}

	/**
	 * Getter for the number character that you are on
	 * 
	 * @return int - Number for which character is currently selected
	 */
	public int getChangeCharacterCount() {
		return changeCharacterCount;
	}

	/**
	 * Getter for the string character that you are on
	 * 
	 * @return String - Which character is currently selected
	 */
	public String getPlayerCharacter() {
		return player.getPlayerCharacter(changeCharacterCount);

	}

	/**
	 * Getter for the player's numerical direction
	 * 
	 * @return int - The int value for the player's direction
	 * @see Player#getDirection()
	 */
	public int getPlayerDirection() {
		return player.getDirection();
	}

	/**
	 * Getter for the player's String direction
	 * 
	 * @return int - The String value for the player's direction
	 * @see Player#getDirectionString()
	 */
	public String getPlayerDirectionString() {
		return player.getDirectionString();
	}

	/**
	 * Getter for the player's jumping state
	 * 
	 * @return boolean - The value for whether or not the player is currently
	 *         jumping
	 * @see Player#getJumping()
	 */
	public boolean isPlayerJumping() {
		return player.getJumping();
	}

	/**
	 * Getter for the player's falling state
	 * 
	 * @return boolean - The value for whether or not the player is currently
	 *         falling
	 * @see Player#getFalling()
	 */
	public boolean isPlayerFalling() {
		return player.getFalling();
	}

	// get the list of enemies
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	// get the plater's health
	public int getPlayerHealth() {
		return player.getHealth();
	}

	public ArrayList<Collectible> getCollectibles() {
		return collectibles;
	}

	public ArrayList<Collectible> getCollected() {
		return collected;
	}

	public ArrayList<Chest> getChests() {
		return chests;
	}

	/**
	 * Getter for the High Score
	 * 
	 * @return String - high score data 
	 * 	from highscore file
	 */
	public String getHighScore() {

		FileReader readFile = null;
		BufferedReader reader = null;

		try {
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			// return reader.readLine();
			return reader.readLine();

		} catch (FileNotFoundException e) {
			return "Nobody: 0";
			// e.printStackTrace();
		} catch (IOException e) {
			return "Nobody: 0";
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Getter for the High Score
	 * 
	 * @return String - Current player 
	 * 	score converted to a string 
	 */
	public String getScore() {

		return Integer.toString(player.getScore());
	}

	// *************************************************
	// Setters

	/**
	 * Sets the changeCharacterMode variable
	 * 
	 */
	public void setChangePlayerMode() {
		changeCharacterMode = !changeCharacterMode;
		setIsGamePaused();
	}

	/**
	 * Sets the isGamePaused variable
	 * 
	 */
	public void setIsGamePaused() {
		if (getChangeCharacterMode()) {
			isGamePaused = true;
		} else {
			isGamePaused = false;
		}
	}

	/**
	 * Sets the isGameOver variable
	 * 
	 */
	public void setIsGameOver(boolean value) {
		isGameOver = value;
		if (isGameOver) {
			// getHighScore();
			if (highScore == "") {
				highScore = this.getHighScore();
			}
		}
	}

	/**
	 * Turns the player's Dx variable to 0
	 * 
	 * @see Player#setDxOff()
	 */
	public void setPlayerDxOff() {
		player.setDxOff();
	}

	/**
	 * Sets the player's jumping mode
	 * 
	 * @see Player#setJumping()
	 */
	public void makePlayerJump() {
		player.setJumping(true);
	}

	/**
	 * Checks the player's health
	 * 
	 * And sets isGameOver accordingly
	 */
	public void checkIsGameOver() {
		if (player.getHealth() <= 0) {
			setIsGameOver(true);
		} else {
			setIsGameOver(false);
		}
	}

	/**
	 * Increments the changeCharacter count which is responsible for showing you
	 * what character you are
	 */
	public void incrementChangeCharacterCount() {
		changeCharacterCount++;
	}

	/**
	 * Decrements the changeCharacter count, will result in whatever character is
	 * associated with that number
	 */
	public void decrementChangeCharacterCount() {
		changeCharacterCount--;
	}

	/**
	 * Sets the name of the user
	 */
	public void setName(String name) {
		this.name = name;

	}

}
