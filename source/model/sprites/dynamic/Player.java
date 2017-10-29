package model.sprites.dynamic;

import java.util.ArrayList;
import java.util.List;

import model.Sprites;
import model.sprites.fixed.Collectible;

public class Player extends Sprites {
	
	private int health; 
	private int score; 
	private List<Collectible> collectibles;
	private boolean invincibility; 
	
	//vars for decrementing and incrementing movement
	private int deltaX = 10;
	private int deltaY = 10;
	//0 = player facing left, 1 = player facing right
	private int direction = 1;
	private int numOfCharacter = 2;
	private int changeCharacterCount = 0;
	
	public Player(int xCoord, int yCoord) {	
		super.xCoord = xCoord;
		super.yCoord = yCoord;
		
		this.health = 3;
		this.score = 0;
		this.collectibles = new ArrayList<Collectible>(3);
		this.invincibility = false;
	}
	
	public void incrementX() {
		xCoord += deltaX;
	}
	
	public void decrementX() {
		xCoord -= deltaX;
	}
	
	public void incrementY() {
		yCoord += deltaY;
	}
	
	public void decrementY() {
		yCoord -= deltaY;
	}
	
	
	public void changeCharacter() {
		
	}
	
	public void changeHealth(int num) {
		
	}
	
	public void changeSpeed() {
		deltaX++;
		deltaY++;
	}
	
	public void incrementScore() {
		score++;
	}
	
	public void addToCollectibles(Collectible item) {
		collectibles.add(item);
	}
	
	public List<Collectible> getCollectibles() {
		return collectibles;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String getDirectionState() {
		if(getDirection() == 1) {
			return ": Facing RIGHT";
		} else {
			return ": Facing LEFT";
		}
	}
	
	public int getScore() {
		return score;
	}
	
	
	
	public void setDirection(int newDirection) {
		direction = newDirection;
	}
	
	public void setInvincibility(boolean bool) {
		this.invincibility = bool;
	}
	
	public String getPlayerCharacter(int changeCharacterCount) {
		if((changeCharacterCount % numOfCharacter) == 0) {
			return "cat";
		}else {
			return "dog";
		}
	}
	
}
