import java.awt.Rectangle;
import java.awt.Toolkit;

public class Model {

	//get the screen's dimensions
	final private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private int xBoundary;
	private int yBoundary;
	private double screenRatio;
	
	//fixed gravity constant
	private int gravity = 10;
	
	private Ground ground;
	int groundOffSet = 100;
	private Player player;
	
	//************************
	//Constructor
	public Model(){
		
		//get the screen's ratio
		screenRatio = screenWidth/screenHeight;
		int playerWidth = (int) (screenWidth * 0.1);
		int playerHeight = (int) (playerWidth * screenRatio);
		
		//set the boundaries
		xBoundary = (int)screenWidth - playerWidth;
		yBoundary = (int)screenHeight - playerHeight;
		
		//create Player object
		player = new Player(100,200,playerWidth,playerHeight, gravity);
		//create Ground object
		ground = new Ground(0, (int)(screenHeight - groundOffSet),(int)(screenWidth),groundOffSet);
	}
	
	//************************
	//Methods
	
	//getters for the boundaries
	public int getXBoundary(){
		return xBoundary;
	}
	public int getYBoundary(){
		return yBoundary;
	}
	
	//getter for player dimensions
	public double getPlayerWidth(){
		return player.getWidth();
	}
	public double getPlayerHeight(){
		return player.getHeight();
	}
	
	//getter for player's coordinates
	public int getPlayerX(){
		return (int) player.getX();
	}
	public int getPlayerY(){
		return (int) player.getY();
	}
	
	//getter for ground
	public Rectangle getGround(){
		return ground;
	}
	
	//move the player
	public void movePlayer(){
		player.move();
	}
	
	//setter for player's dx
	public void playerMoveLeft(){
		player.setDirection(0);
		//check if player is going out of x bound
		if(player.getX() >= 0)
		{
			System.out.println("Boundary invalid " + xBoundary);
			player.moveLeft();
			System.out.println("ground" + ground.getWidth());
		}
		//if out of bound then don't increment the x
		else
		{
			System.out.println("Boundary invalid 0");
			System.out.println("Player is out of Boundary to Left");
			player.setDxOff();
		}
	}
	public void playerMoveRight(){
		player.setDirection(1);
		//check if player is going out of x bound
		if(player.getX() <= xBoundary)
		{
			System.out.println("Boundary invalid " + xBoundary);
			player.moveRight();
			System.out.println("ground" + ground.getWidth());
		}
		else
		{
			System.out.println("Boundary invalid " + xBoundary);
			System.out.println("Player is out of Boundary to Right");
			player.setDxOff();
		}
	}
	public void setPlayerDxOff(){
		player.setDxOff();
	}
	
	//getter for player's direction
	public int getPlayerDirection(){
		return player.getDirection();
	}
	public String getPlayerDirectionString(){
		return player.getDirectionString();
	}
	
	//getters for player falling and jumping
	public boolean isPlayerJumping(){
		return player.getJumping();
	}
	public boolean isPlayerFalling(){
		return player.getFalling();
	}
	
	//set the player to jumping mode
	public void makePlayerJump(){
		player.setJumping();
	}
	
	//checks if it is colliding
	public void gravity(){
		player.gravityEffect(ground);
	}
}
