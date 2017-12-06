package model.dynamic;

import java.awt.Rectangle;

import model.DynamicObject;
import model.fixed.Ground;
import model.fixed.Platform;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Player extends DynamicObject {

	// *************************************************
	// Fields

	// health of the player
	private int health = 3;
	// the state of the player
	private String state = "researcher";
	// assuming only player can jump and fall
	private int maxDy = 15;
	private int minDy = -15;
	// for jumping
	private int maxJumpingHeight;
	// use prevY later to compare with maxJumpingHeight
	private double prevY;
	private int gravity;
	// player boundary fields
	private int widthOfBumpers = 25;
	private Rectangle leftSide;
	private Rectangle rightSide;
	private Rectangle topSide;
	private Rectangle bottomSide;
	// set player to default falling and cannot jump
	private boolean falling = true;
	private boolean jumping = false;
	// number of selectable characters
	private int numOfCharacter = 2;
	// fields for score
	private int score = 0;

	// *************************************************
	// Constructor

	/**
	 * Constructor for the Player
	 * 
	 * @param x
	 *            - X Location
	 * @param y
	 *            - Y Location
	 * @param width
	 *            - Player's width
	 * @param height
	 *            - Player's Height
	 * @param gravity
	 *            - Gravity's Location
	 */
	public Player(int x, int y, int width, int height, int gravity) {
		// setLocation in Rectangle class used to set x and y coordinate
		super.setLocation(x, y);

		// set player size
		super.setSize(width, height);

		// set max jumping height
		maxJumpingHeight = height * 3;

		// set gravity location
		this.gravity = gravity;

		// set player facing right
		super.setDirection(1);

		// initialize player's dx and dy to 0
		super.setDx(0);
		super.setDy(0);

		// set the sides
		leftSide = new Rectangle(x, y, widthOfBumpers, height);
		rightSide = new Rectangle((x + (width - widthOfBumpers)), y, widthOfBumpers, height);
		topSide = new Rectangle((x + widthOfBumpers), y, (width - 2 * widthOfBumpers), widthOfBumpers);
		bottomSide = new Rectangle((x + widthOfBumpers), (y + (height - widthOfBumpers)), (width - 2 * widthOfBumpers),
				widthOfBumpers);
	}

	// *************************************************
	// Methods

	// determines if touching the ground with appropriate actions for jumping and
	// falling

	/**
	 * Enacts gravity on the player
	 * 
	 * @param ground
	 *            - Ground object to use gravity on
	 */
	public void gravityEffect(Ground ground) {

		if (falling && !jumping) {
			playerFalling();
		}

		else if (y < (-(super.getHeight()) * (0.75))) {
			falling = true;
			jumping = false;
		}

		// if player is not touching the ground but is jumping then allow player to jump
		else if (falling && jumping) {
			System.out.println("Executed: First Stage gravityEffect()");
			// has the player reached max jumping height yet
			if ((prevY - y) < maxJumpingHeight) {
				playerJumping();
			} else {
				// if max height reached then player stops jumping/ascending
				jumping = false;
			}

		}
		// if player is touching the ground but is not jumping then allow player to jump
		else if (!falling && jumping) {
			System.out.println("Executed: Second Stage gravityEffect()");
			if ((prevY - y) < maxJumpingHeight) {
				playerJumping();
			} else {
				jumping = false;
			}

		}

		// if player is on a surface then set dy to 0
		else {
			super.setDy(0);
		}
	}

	// make the player fall
	/**
	 * Makes the player fall
	 */
	public void playerFalling() {
		System.out.println("Executed: playerFalling()");
		if (super.getDy() <= maxDy) {
			int newDy = super.getDy() + gravity;
			super.setDy(newDy);
		}
	}

	// make the player jump
	/**
	 * Makes the player jump
	 */
	public void playerJumping() {
		System.out.println("Executed: playerJumping()");
		if (super.getDy() >= minDy) {
			int newDy = super.getDy() - gravity;
			super.setDy(newDy);
		}
	}

	// collision between player and platforms
	/**
	 * Stops the player from moving forward when they hit a platform
	 * 
	 * @param plat
	 *            - Platform to test collision on
	 */
	public void playerPlatCollision(Platform plat) {
		if (this.intersects(plat.getLeft()) && this.getDirection() == 1) {
			this.setDx(0);
		} else if (this.intersects(plat.getRight()) && this.getDirection() == 0) {
			this.setDx(0);
		}
	}

	/**
	 * Moves the player with a certain velocity
	 */
	public void move() {
		int x = (int) super.getX() + super.getDx();
		int y = (int) super.getY() + super.getDy();
		super.setLocation(x, y);

		leftSide.setLocation(x, y);
		rightSide.setLocation((x + (width - widthOfBumpers)), y);
		topSide.setLocation((x + widthOfBumpers), y);
		bottomSide.setLocation((x + widthOfBumpers), (y + (height - widthOfBumpers)));
	}

	// *************************************************
	// Getters

	// getter for jumping
	/**
	 * Gets the jumping boolean
	 * 
	 * @return jumping - boolean determining whether or not the player is jumping
	 */
	public boolean getJumping() {
		return jumping;
	}

	// getter for falling
	/**
	 * Gets the falling boolean
	 * 
	 * @return falling - boolean determining whether or not the player is jumping or
	 *         not
	 */
	public boolean getFalling() {
		return falling;
	}

	/**
	 * Gets the maximum jumping height
	 * 
	 * @return maxJumpingHeight - integer that holds the maximum jumping height for
	 *         the player
	 */
	public int getMaxJumpingHeight() {
		return maxJumpingHeight;
	}

	/**
	 * Shows which character the player is playing as
	 * 
	 * @param changeCharacterCount
	 *            - Int for which character is being used
	 * @return researcher or bird - strings that corresponds with the character that
	 *         is being selected/played
	 */
	public String getPlayerCharacter(int changeCharacterCount) {
		if (changeCharacterCount % numOfCharacter == 0) {
			maxJumpingHeight = (int) (height / 1.7);
			state = "researcher";
			return "researcher";
		} else {
			maxJumpingHeight = (int) (height * 1.7);
			state = "bird";
			return "bird";
		}
	}

	// getter for health
	/**
	 * Gets the current health of the player
	 * 
	 * @return health - integer corresponding to how much health the player has
	 */
	public int getHealth() {
		return health;
	}

	// getter for score
	/**
	 * Gets the player's current score
	 * 
	 * @return score - integer corresponding to the score that the player has
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets the left hit box for the character
	 * 
	 * @return leftSide - rectangle that represents the player's left hit box
	 */
	public Rectangle getLeftSide() {
		return leftSide;
	}

	/**
	 * Gets the right hit box for the character
	 * 
	 * @return rightSide - rectangle that represents the player's right hit box
	 */
	public Rectangle getRightSide() {
		return rightSide;
	}

	/**
	 * Gets the top side hit box for the character
	 * 
	 * @return topSide - rectangle that represents the player's top hit box
	 */
	public Rectangle getTopSide() {
		return topSide;
	}

	/**
	 * Gets the bottom side hit box for the character
	 * 
	 * @return bottomSide - rectangle that represents the player's bottom hit box
	 */
	public Rectangle getBottomSide() {
		return bottomSide;
	}

	/**
	 * Determines whether or not the player can attack/kill a killable enemy
	 * 
	 * @return boolean - true if the player is falling and not jumping, false
	 *         otherwise
	 */
	public boolean isAbleToAttack() {
		return (falling && !jumping);
	}

	// *************************************************
	// Setters

	// setter for the player's dimensions

	/**
	 * Sets the players dimensions
	 * 
	 * @param width
	 *            - int to set the width
	 * @param height
	 *            - int to set the height
	 */
	public void setDimensions(int width, int height) {
		super.setSize(width, height);
	}

	// setter for turning the dx between left, right, and 0

	/**
	 * Moves the character left
	 */
	public void moveLeft() {
		if (state.equalsIgnoreCase("bird")) {
			super.setDx(-5);
		} else {
			super.setDx(-20);
		}
	}

	/**
	 * Moves the character right
	 */
	public void moveRight() {
		if (state.equalsIgnoreCase("bird")) {
			super.setDx(5);
		} else {
			super.setDx(20);
		}
	}

	/**
	 * Sets the x velocity to 0
	 */
	public void setDxOff() {
		super.setDx(0);
	}

	/**
	 * Increments the player's score
	 * 
	 * @param i
	 *            - Number incrementing the score by
	 */
	public void incrementScoreBy(int i) {
		score += i;
	}

	/**
	 * Sets the player's jumping status
	 * 
	 * @param b
	 *            - Boolean to set jumping
	 */
	public void setJumping(boolean b) {
		jumping = b;
		prevY = super.getY();
	}

	/**
	 * Sets the player's falling status
	 * 
	 * @param b
	 *            - Boolean to set the falling to
	 */
	public void setFalling(boolean b) {
		falling = b;
	}

	/**
	 * Sets the player's max jumping height
	 * 
	 * @param j
	 *            - Int for max jump height
	 */
	public void setMaxJumpingHeight(int j) {
		maxJumpingHeight = j;
	}

	/**
	 * Sets the player's current health
	 * 
	 * @param i
	 *            - Int for amount of health player has
	 */
	public void setHealth(int i) {
		health = i;
	}

	public void incrementHealth() {
		health++;
	}
}
