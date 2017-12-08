package testing;

import static org.junit.Assert.assertEquals;

import java.awt.Toolkit;
import java.util.ArrayList;

import org.junit.Test;

import model.*;
import model.dynamic.*;

public class ModelTest {
	
	// *************************************************
		// Fields

	Model m = new Model(false);
	
	// *************************************************
		// Methods

	/**
	 * Testing moving the player to the right
	 */
	@Test
	public void testPlayerMoveRight() {
		//starting coordinates of the player
		System.out.println("Test if player is moving right");
		int x = m.getPlayerX();
		System.out.println("Old postion: " + x);
		//set the player's dx
		m.playerMoveRight();
		m.movePlayer();
		int currentX = m.getPlayerX();
		System.out.println("New postion: " + currentX);
		int distance = currentX - x;
		System.out.println("Distance traveled: " + distance);
		System.out.println("Player dx: " + m.getPlayerDx());
		//since the velocity is preset to 20, once moveRight player moves 20 to right
		boolean testPass = (distance == m.getPlayerDx())? true : false; 
		System.out.println("Test player move right: " + testPass);
		assertEquals(distance, m.getPlayerDx());
		assertEquals(m.getPlayerDirection(), 1);
		assertEquals(m.getPlayerDirectionString(), "RIGHT");
	}
	
	
	/**
	 * Testing moving the player to the left
	 */
	@Test
	public void testPlayerMoveLeft() {
		System.out.println("\nTest if player is moving left");
		int x = m.getPlayerX();
		System.out.println("old postion: " + x);
		//move player to left
		m.playerMoveLeft();
		m.movePlayer();
		int currentX = m.getPlayerX();
		System.out.println("new postion: " + currentX);
		int distance = currentX - x;
		System.out.println("Distance traveled: " + distance);
		System.out.println("Player dx: " + m.getPlayerDx());
		//since the velocity is preset to -20 when moving left, once moveLeft player moves 20 to left
		boolean testPass = (distance == m.getPlayerDx())? true : false; 
		System.out.println("Test player move left: " + testPass);		
		assertEquals(distance, m.getPlayerDx());
		assertEquals(m.getPlayerDirection(), 0);
		assertEquals(m.getPlayerDirectionString(), "LEFT");
	}
	

	/**
	 * Testing enemy movement
	 */
	@Test
	public void testMovingEnemies() {
		System.out.println("\nTest moving enemies");
		//set one enemies
		m.setEnemies();
		ArrayList<Enemy> enemy = m.getEnemies();
		int previousX = (int)enemy.get(0).getX();
		System.out.println("Enemy previous x: " + previousX);
		//Since crabs start at the edge of the platform, we need to move it twice so that crab turns around and move
		m.moveEnemies();
		m.moveEnemies();
		enemy = m.getEnemies();
		int newX = (int)enemy.get(0).getX();
		System.out.println("Enemy new x: " + newX);
		int distance = newX - previousX;
		System.out.println("Distance traveled: " + distance);
		boolean result = (distance == 4)? true : false;
		System.out.println("Test moving Enemis: " + result);
		assertEquals(Math.abs(distance), 4);
	}
	
	/**
	 * Testing changing the character
	 */
		@Test
		public void testChangeCharacter() {
			System.out.println("\nTest player change character");
			String prvChar = m.getPlayerCharacter();
			System.out.println("Previous Character: " + prvChar);
			m.incrementChangeCharacterCount();
			System.out.println("Current Character: " + m.getPlayerCharacter());
			assertEquals("bird", m.getPlayerCharacter());
		}
		
		/**
		 * Testing the change character mode
		 */		
		@Test
		public void testChangeCharacterMode() {
			System.out.println("\nTest Change Character Mode getter");
			m.setChangePlayerMode();
			boolean result = m.getChangeCharacterMode();
			System.out.println("Is Change Character mode result: " + result);
			assertEquals(true, result);
		}
	
		/**
		 * Testing adding health
		 */
	@Test
	public void testAddHealth() {
		System.out.println("\nTest player add health");
		int prvHeath = m.getPlayerHealth();
		System.out.println("Previous Health: " + prvHeath);
		m.incrementPlayerHealth();
		System.out.println("Current Health: " + m.getPlayerHealth());
		assertEquals(prvHeath + 1, m.getPlayerHealth());
	}

	/**
	 * Testing adding score
	 */
	@Test
	public void testAddScore() {		
		System.out.println("\nTest player add score");
		int prvScore = m.getPlayerScore();
		System.out.println("Previous Score: " + prvScore);
		m.incrementPlayerScoreBy(50);
		System.out.println("Current Score: " + m.getPlayerScore());
		assertEquals(50, m.getPlayerScore());
	}
	
	/**
	 * Testing a new high score
	 */
	@Test
	public void testNewHighScore() {
		System.out.println("\nTest if it is a new High Score");
		int prvHighScore = Integer.parseInt(m.getHighScore().split(": ")[1]);
		System.out.println("Previous High Score: " + prvHighScore);
		m.incrementPlayerScoreBy(890);
		System.out.println("Current Score: " + m.getPlayerScore());
		boolean isNewHighScore = false;
		if (m.getPlayerScore() > prvHighScore) {
			isNewHighScore = true;
		}
		assertEquals(true, isNewHighScore);
	}
	
	/**
	 * Testing the set name function
	 */
	@Test
	public void testSetName() {
		System.out.println("\nTest set name function");
		m.setName("Akash");
		assertEquals("Akash", m.getPlayerName());
	}
	
	/**
	 * Testing the game over mode
	 */
	@Test
	public void testGameOver() {
		Model m1 = new Model(false);
		System.out.println("\nTest Game Over mode function");
		m1.setIsGameOver(true);
		assertEquals(true, m1.getIsGameOver());
	}
	
	/**
	 * Testing the game paused mode
	 */
	@Test
	public void testIsGamePaused() {
		Model m2 = new Model(false);
		System.out.println("\nTest is paused mode function");
		m2.setChangePlayerMode(); // this puts game in paused mode
		assertEquals(true, m2.getIsGamePaused());
	}

	/**
	 * Testing the change room function
	 */
	@Test
	public void testChangeRoom() {	
		Model intro = new Model(true);
		System.out.println("\nTest Change room");
		int prvRoomNumner = intro.getIntroRoomNum();
		intro.playerMoveRight();
		m.playerMoveRight();
		for(int i = 0; i< 400;i++) {
			intro.movePlayer();
			m.movePlayer();
			m.changeRoom();
		}
		//intro.changeRoom();
		intro.changeRoom();
		System.out.println("Previous Room Number: " + prvRoomNumner);
		System.out.println("Current Room Number: " + intro.getIntroRoomNum());
		assertEquals(1, intro.getIntroRoomNum());
	}
	
	/**
	 * Testing the get x boundary
	 */
	@Test
	public void testGetXBoundary() {
		Model j = new Model(false);
		System.out.println("\nTest x Boundary getter");
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		int playerWidth = (int) (screenWidth * 0.1);		
		
		int xBoundary = (int) ((int)screenWidth - playerWidth);
		System.out.println("X Boundary: " + xBoundary);
		System.out.println("Actually game x boundary: " + j.getXBoundary());
		boolean result = (xBoundary == j.getXBoundary())? true : false;
		System.out.println("Test getter result: " + result);
		assertEquals(xBoundary, j.getXBoundary());
		
	}
	
	/**
	 * Testing the get y boundary
	 */
	@Test
	public void testGetYBoundary() {
		Model j = new Model(false);
		System.out.println("\nTest y Boundary getter");
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		double screenRatio = screenWidth / screenHeight;
		
		int playerWidth = (int) (screenWidth * 0.1);
		int playerHeight = (int) (playerWidth * screenRatio);		
		
		int yBoundary = (int) screenHeight - playerHeight;
		System.out.println("Y Boundary: " + yBoundary);
		System.out.println("Actually game y boundary: " + j.getYBoundary());
		boolean result = (yBoundary == j.getYBoundary())? true : false;
		System.out.println("Test getter result: " + result);
		assertEquals(yBoundary, j.getYBoundary());
		
	}
	
	/**
	 * Testing the question mode
	 */
	@Test
	public void testIsQuestionMode() {
		System.out.println("\nTest Question Mode getter");
		m.setQuestionMode(true);
		boolean result = m.isQuestionMode();
		System.out.println("Is question mode result: " + result);
		assertEquals(true, result);
	}
	
	/**
	 * Testing the player falling functions
	 */
	@Test
	public void testIsPlayerFalling() {
		System.out.println("\nTest is Player falling");
		boolean result = m.isPlayerFalling();
		System.out.println("Is the player falling: " + result);
		assertEquals(true, result);
	}
	
	/**
	 * Testing the player jumping functions
	 */
	@Test
	public void testIsPlayerJumping() {
		System.out.println("\nTest is Player jumping");
		boolean result = m.isPlayerJumping();
		System.out.println("Is the player falling: " + result);
		assertEquals(false, result);
	}

}
