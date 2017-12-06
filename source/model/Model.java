package model;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.dynamic.Enemy;
import model.dynamic.EnemyCrab;
import model.dynamic.EnemyOsprey;
import model.dynamic.Player;
import model.fixed.Chest;
import model.fixed.Collectible;
import model.fixed.Fact;
import model.fixed.Ground;
import model.fixed.Platform;
import model.fixed.Question;
import model.fixed.QuestionBank;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Model {

	// *************************************************
	// Fields

	// player objects
	private Player player;

	final private int startingXOffSet = 192;
	// get the screen's dimensions
	final private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final private double screenRatio = screenWidth / screenHeight;
	// boundaries
	private int xBoundary;
	private int yBoundary;
	// platform fields
	private Ground ground;
	private Platform p1;
	private Platform p2;
	private Platform p3;
	private Platform p4;
	private Platform p5;
	// offset for ground
	int groundOffSet = 100;

	// width of a platform
	double platWidth = (screenWidth / 6);
	double platHeight = (screenHeight / 16);
	// array list containing the platform objects
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	// fields for all other world objects
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Collectible> collectibles = new ArrayList<Collectible>(1);
	private ArrayList<Collectible> collected = new ArrayList<Collectible>();
	private ArrayList<Fact> facts = new ArrayList<Fact>(1);
	private ArrayList<Chest> chests = new ArrayList<Chest>(1);
	// number of collectibles collected
	private int numCollected = 0;
	// to make sure only 1 chest is created per screen
	private boolean chestCreated = false;
	// fixed gravity constant
	private int gravity = 10;
	// fields for game modes
	private boolean changeCharacterMode = false;
	private boolean isQuestionMode = false;
	private boolean isGamePaused = false;
	private boolean isGameOver = false;
	// intro Mode fields
	private boolean isIntroMode;
	int introRoomNum = 0;
	// character number
	private int changeCharacterCount = 0;
	// starting positions
	private int startingX;
	private int startingY;
	// for high score functionality
	private String highScore = "";
	private String name = "";
	private int gameTimeLeft;

	// add Question variables and QuestionMode flag
	private boolean questionMode = false;
	// Object containing the questions
	private QuestionBank QB = new QuestionBank();
	private Question question;

	// *************************************************
	// Constructor

	/**
	 * Constructor for Model
	 */
	public Model(boolean isIntroMode) {

		this.isIntroMode = isIntroMode;
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
		ground = new Ground(-500, (int) (screenHeight - groundOffSet), (int) (screenWidth * 2), groundOffSet);

		// make 5 platforms
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				p1 = new Platform(
						(int) ThreadLocalRandom.current().nextInt((int) (screenWidth / 4), (int) (screenWidth / 3)),
						(int) ThreadLocalRandom.current().nextInt((int) (screenHeight / 2), (int) (screenHeight / 1.5)),
						(int) platWidth, (int) platHeight);
				platforms.add(p1);
			} else if (i == 1) {
				p2 = new Platform(
						(int) (p1.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p1.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p2);
			} else if (i == 2) {
				p3 = new Platform(
						(int) (p2.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p2.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p3);
			} else if (i == 3) {
				p4 = new Platform(
						(int) (p3.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p3.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p4);
			} else if (i == 4) {
				p5 = new Platform(
						(int) (p4.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p4.getCenterY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p5);
			}
		}

		// generate first collectible
		Random random = new Random(System.currentTimeMillis());
		int randomPlat = random.nextInt(3);

		Collectible firstCollectible = new Collectible(platforms.get(randomPlat));
		Collectible.setHeightIter(0);
		collectibles.add(firstCollectible);
		System.out.println("First Collectible Created");

		if (isIntroMode) {
			platforms.clear();
			enemies.clear();
			collectibles.clear();
			facts.clear();
			chests.clear();
		}

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

	/**
	 * Moves enemy
	 *
	 */
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
			System.out.println("Boundary Invalid 0");
			System.out.println("Player is Out of Left Boundary");
			player.setDxOff();
		}
	}

	/**
	 * Moves the player right
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
			System.out.println("Player is Out of Right Boundary");
			player.setDxOff();
		}
	}

	/**
	 * Checks if the player is colliding
	 *
	 * @see Player#gravityEffect(Ground)
	 */
	public void gravity() {
		player.gravityEffect(ground);
	}

	// check each collision
	/**
	 * Checks for on every object
	 */
	public void checkCollision() {
		checkCollisionPlatform();
		checkCollisionEnemy();
		checkCollisionCollectible();
		checkCollisionChest();
	}

	/**
	 * Checks for a collision with a platform
	 */
	private void checkCollisionPlatform() {
		boolean isBottomCollide = false;
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

		if ((player.getBottomSide()).intersects(ground)) {
			player.setFalling(false);
			isBottomCollide = true;
		}

		if (!isBottomCollide) {
			player.setFalling(true);
		}

	}

	// if player collided with the enemy
	/**
	 * Checks if player collides an enemy
	 * 
	 * @see Enemy#isKillable()
	 * @see Enemy#isDead()
	 * 
	 */
	private void checkCollisionEnemy() {
		for (Enemy enemy : enemies) {
			if (enemy.isKillable()) {
				if ((player.getBottomSide()).intersects(enemy) && player.isAbleToAttack()) {

					player.setLocation((int) player.getX(), (int) player.getY() - 25);
					enemy.setIsDead();
					player.incrementScoreBy(5);
				}
			}

			if ((!enemy.isDead()) && enemy.intersects(player) && !(enemy.getHasAttacked())) {

				if (!isIntroMode) {
					enemy.damage(player);
				}

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
				enemy.setHasAttacked(true);

			} else if (!enemy.intersects(player)) {
				enemy.setHasAttacked(false);
			}
		}
	}

	/**
	 * Handles horizontal collisions with an enemy
	 * 
	 * @param enemy
	 *            - enemy you're colliding with
	 * @return boolean - true if collision, false if no collision
	 */
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

	/**
	 * Handles Collectible collisions with a player Generates new fact after
	 * clearing previous fact adds collectible to collected Increments score by 10
	 */
	private void checkCollisionCollectible() {
		for (Iterator<Collectible> collectIter = collectibles.iterator(); collectIter.hasNext();) {
			Collectible c = collectIter.next();
			if (player.intersects(c)) {
				if (isIntroMode) {
					// intro fact
					facts.clear();
				} else {
					// ensure that no more than one fact is displayed at a time
					facts.clear();
					// generate new fact object
					facts.add(new Fact());
				}
				// add collectible to collected
				collected.add(new Collectible(numCollected));
				// increment number collected and score
				numCollected++;
				player.incrementScoreBy(10);
				// print score
				System.out.println("Score: " + player.getScore());
				// remove collectible from screen
				collectIter.remove();
			}
		}
	}

	/**
	 * Checks if the player collides with a chest, Opens the chest and sets questionMode
	 * to true, and then generates a question
	 */
	private void checkCollisionChest() {
		for (Iterator<Chest> chestIter = chests.iterator(); chestIter.hasNext();) {
			Chest c = chestIter.next();
			if (player.intersects(c)) {
				// when player hits chest that has not been opened
				if (!c.getIsOpen()) {
					// ensure no fact is shown when player hits the chest
					facts.clear();
					player.incrementScoreBy(10);
					// go to question mode
					questionMode = true;
					this.generateQuestion();
					// picks question based on number of collected(facts)
				}
				c.setIsOpen(true);
				System.out.println("Score: " + player.getScore());
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
			introRoomNum++;
			platforms.clear();
			collectibles.clear();
			chests.clear();
			facts.clear();
			enemies.clear();
			if (!isIntroMode) {
				createNewPlatform();
			} else {
				switch (introRoomNum) {
				case 1:
					setPlatform((int) (screenWidth / 3), (int) (screenHeight / 1.5)); // use this method to place custom
																						// platforms for intro
					break;
				case 2:
					setPlatform((int) (screenWidth / 3), (int) (screenHeight / 1.5));
					collectibles.add(new Collectible(platforms.get(0)));
					setPlatform((int) (screenWidth / 1.5), (int) (screenHeight / 1.5));
					chests.add(new Chest(platforms.get(1)));
					break;
				case 3:
					setPlatform((int) (screenWidth / 3), (int) (screenHeight / 1.5));
					enemies.add(new EnemyCrab(platforms.get(0)));
					enemies.add(new EnemyOsprey((int) screenWidth, (int) screenHeight));
					break;
				}
			}
		}
	}

	/**
	 * Method to increment health
	 */
	public void incrementPlayerHealth() {
		player.incrementHealth();
	}

	/**
	 * Method to increment score
	 */
	public void incrementPlayerScoreBy(int i) {
		player.incrementScoreBy(i);
	}

	/**
	 * Generates new question
	 */
	private void generateQuestion() {
		question = QB.pickQuestion(this.numCollected);
	}

	/**
	 * Randomly generate new room
	 *
	 */
	private void createNewPlatform() {
		// clear screen in order to rewrite objects/images
		platforms.clear();
		enemies.clear();
		collectibles.clear();
		facts.clear();
		chests.clear();

		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				p1 = new Platform(
						(int) ThreadLocalRandom.current().nextInt((int) (screenWidth / 4), (int) (screenWidth / 3)),
						(int) ThreadLocalRandom.current().nextInt((int) (screenHeight / 2), (int) (screenHeight / 1.5)),
						(int) platWidth, (int) platHeight);
				platforms.add(p1);
			} else if (i == 1) {
				p2 = new Platform(
						(int) (p1.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p1.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p2);
			} else if (i == 2) {
				p3 = new Platform(
						(int) (p2.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p2.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p3);
			} else if (i == 3) {
				p4 = new Platform(
						(int) (p3.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p3.getY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p4);
			} else if (i == 4) {
				p5 = new Platform(
						(int) (p4.getX()
								+ ThreadLocalRandom.current().nextInt((int) platWidth, (int) (screenWidth / 4))),
						(int) (p4.getCenterY() + ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))
								- ThreadLocalRandom.current().nextInt(0, (int) (screenHeight / 5))),
						(int) platWidth, (int) platHeight);
				platforms.add(p5);
			}
		}

		// random crab & collectible generators
		Random random = new Random(System.currentTimeMillis());
		if ((numCollected >= 3) && (numCollected > 0)) {
			int randomPlat = random.nextInt(1);
			chests.add(new Chest(platforms.get(randomPlat)));
			System.out.println("New Chest Created");
			chestCreated = true;
		}

		for (Platform platform : platforms) {
			int randomNum = random.nextInt(4);
			if (randomNum == 0) {
				enemies.add(new EnemyCrab(platform));
				System.out.println("New Enemy Crab Created");
			} else if (randomNum == 1) {
				collectibles.add(new Collectible(platform));
				System.out.println("New Collectible Created");
			} else if (chestCreated) {
				// force new collectible creation so that chests don't keep getting created
				collectibles.add(new Collectible(platform));
				System.out.println("New Collectible Created");
				chestCreated = false;
			}
		}
		enemies.add(new EnemyOsprey((int) screenWidth, (int) screenHeight));
		System.out.println("New Enemy Osprey Created");
	}

	/**
	 * Checks if current score is a new high-score
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
		if (isNewHighScore()) {
			highScore = name + ": " + player.getScore();
		} else {
			highScore = name + ": " + highScore.split(": ")[1];
		}

		File scoreFile = new File("HighScore.dat");
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
		Model model = new Model(false);

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
	 * Returns the intro room number
	 *
	 * @return int - room number of Intro
	 */
	public int getIntroRoomNum() {
		return introRoomNum;
	}

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
	 * returns player x position
	 *
	 * @return int - x position
	 */
	public int getPlayerDx() {
		return player.getDx();
	}

	/**
	 * returns player y position
	 *
	 * @return int - y position
	 */
	public int getPlayerDy() {
		return player.getDy();
	}

	/**
	 * Getter for ground
	 *
	 * @return Ground - ground object at the bottom of the screen
	 */
	public Ground getGround() {
		return ground;
	}

	/**
	 * Getter for platforms
	 *
	 * @return ArrayList - ArrayList containing platforms
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
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
	 * Getter for question mode state
	 * 
	 * @return boolean - tells if game is in questionMode
	 */
	public boolean getIsQuestionMode() {
		return isQuestionMode;
	}

	/**
	 * Getter for question
	 * 
	 * @return Question - question
	 */
	public Question getQuestion() {
		return question;
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

	/**
	 * Getter for player's score
	 *
	 * @return int - player score
	 */
	public int getPlayerScore() {
		return player.getScore();
	}

	/**
	 * Getter for Player Health
	 *
	 * @return int - players health
	 */
	public int getPlayerHealth() {
		return player.getHealth();
	}

	/**
	 * Getter for number of collected collectibles
	 *
	 * @return int - numCollected
	 */
	public int getNumCollected() {
		return numCollected;
	}

	/**
	 * Getter for enemies
	 *
	 * @return ArrayList - ArrayList of enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<Collectible> getCollectibles() {
		return collectibles;
	}

	public ArrayList<Collectible> getCollected() {
		return collected;
	}

	public ArrayList<Fact> getFacts() {
		return facts;
	}

	public ArrayList<Chest> getChests() {
		return chests;
	}

	/**
	 * Checks for question mode
	 * 
	 * @return boolean - questionMode
	 */
	public boolean isQuestionMode() {
		return questionMode;
	}

	/**
	 * Getter for the High Score
	 * 
	 * @return String - Current player score converted to a string
	 */
	public String getScore() {
		return Integer.toString(player.getScore());
	}

	/**
	 * Getter for the High Score
	 * 
	 * @return String - high score data from highscore file
	 */
	public String getHighScore() {

		FileReader readFile = null;
		BufferedReader reader = null;

		try {
			readFile = new FileReader("HighScore.dat");
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
	 * Getter for the Game Timer
	 * 
	 * @return int - Current game time left
	 */
	public int getGameTimeLeft() {
		return gameTimeLeft;
	}

	// *************************************************
	// Setters

	/**
	 * Create a single platform in a certain location
	 * 
	 * @param x
	 *            - The x location of platform
	 * @param y
	 *            - The y location of platform
	 * 
	 */
	public void setPlatform(int x, int y) {
		p1 = new Platform(x, y, 350, 50);
		platforms.add(p1);
	}

	/**
	 * Sets the game timer
	 * 
	 * @param currentGameTime
	 *            - The current time of the game
	 * 
	 */
	public void setGameTimeLeft(int currentGameTime) {
		this.gameTimeLeft = currentGameTime;
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
	 * @see Player#setJumping(boolean)
	 */
	public void makePlayerJump() {
		player.setJumping(true);
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
		if (getChangeCharacterMode() || getIsQuestionMode()) {
			isGamePaused = true;
		} else {
			isGamePaused = false;
		}
	}

	/**
	 * Sets the isGamePaused variable
	 * 
	 * @param value
	 *            - Boolean for whether or not to pause game in Question Mode
	 * 
	 */
	public void setIsQuestionMode(boolean value) {
		isQuestionMode = value;
	}

	/**
	 * Checks for question mode
	 * 
	 * @param b
	 *            - Boolean for turning question mode on/off
	 */
	public void setQuestionMode(boolean b) {
		questionMode = b;
	}

	/**
	 * Checks the player's health and sets isGameOver accordingly
	 */
	public void checkIsGameOver() {
		if (player.getHealth() <= 0 || getGameTimeLeft() == 0 && !isIntroMode) {
			setIsGameOver(true);
		} else {
			setIsGameOver(false);
		}
	}

	/**
	 * Sets the isGameOver variable
	 * 
	 * @param value
	 *            - Boolean for whether game is over or not
	 */
	public void setIsGameOver(boolean value) {
		isGameOver = value;
		if (isGameOver) {
			if (highScore == "") {
				highScore = this.getHighScore();
			}
		}
	}

	/**
	 * Sets the name of the user
	 * 
	 * @param name
	 *            - Name of the object
	 */
	public void setName(String name) {
		this.name = name;
		FileReader readFile = null;
		BufferedReader reader = null;

		try {
			readFile = new FileReader("BadWords.txt");
			reader = new BufferedReader(readFile);
			// return reader.readLine();
			String testWord = "";
			while ((testWord = reader.readLine()) != null) {
				if ((this.name.toLowerCase()).contains(testWord)) {
					this.name = "Nobody";
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	 * Adds new word to bad words list
	 * 
	 */
	public void addBadWord() {
		String wordToAdd = (getHighScore().split(": ")[0]).toLowerCase();
		String score = getHighScore().split(": ")[1];
		if (!wordToAdd.equalsIgnoreCase("nobody")) {
			BufferedWriter writer = null;
			FileWriter writeFile = null;

			try {
				File file = new File("BadWords.txt");

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// true = append file
				writeFile = new FileWriter(file.getAbsoluteFile(), true);
				writer = new BufferedWriter(writeFile);
				writer.newLine();
				writer.write(wordToAdd);

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (writer != null)
						writer.close();

					if (writeFile != null)
						writeFile.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
		}

	}

}
