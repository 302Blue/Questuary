import java.awt.Rectangle;

public class Player extends DynamicObject{

	//Assuming only player can jump and fall
	private int maxDy = 15;
	private int minDy = -15;
			
	private int maxJumpingHeight;
	//use prevY later to compare with maxJumpingHeight
	private double prevY;
	private int gravity;
	
	//set player to default falling and cannot jump
	private boolean falling = true;
	private boolean jumping = false;
	
	
	//*************************************************
	//Constructor
	public Player(int x, int y, int width, int height,int gravity){
		//setLocation in Rectangle class used to set x and y coordinate
		super.setLocation(x,y);
		
		//set player size
		super.setSize(width, height);
		
		//set max jumping height
		maxJumpingHeight = height;
		
		//set gravity location
		this.gravity = gravity;
		
		//set player facing right
		super.setDirection(1);
		
		//initialize player's dx and dy to 0
		super.setDx(0);
		super.setDy(0);
	}
	
	//*******************************************
	//Methods
	
	//getter for jumping
	public boolean getJumping(){
		return jumping;
	}
	//getter for falling
	public boolean getFalling(){
		return falling;
	}
	
	//setter for the player's dimensions
	public void setDimensions(int width,int height)
	{
		super.setSize(width, height);
	}
	
	//setter for turning the dx between left, right, and 0
	public void moveLeft(){
		super.setDx(-20);
	}
	public void moveRight(){
		super.setDx(20);
	}
	public void setDxOff(){
		super.setDx(0);
	}
	
	//setter for jumping
	public void setJumping(){
		jumping = true;
		prevY = super.getY();
	}
	
	//setter for maxJumpingHeight
	public void setMaxJumpingHeight(int j){
		maxJumpingHeight = j;
	}
	
	//determines if touching the ground with appropriate actions for jumping and falling
	public void gravityEffect(Rectangle ground){
		//determine if the player is falling
		falling = !(this.intersects(ground));
		
		//if player is not touching the ground and is not jumping then the player is falling
		if(falling && !jumping)
		{
			playerFalling();
		}
		//if player is not touching the ground but is jumping then allow player to jump
		else if (falling && jumping)
		{
			//has the player reached max jumping height yet
			if((prevY - y) < maxJumpingHeight)
			{
				System.out.println("in first case gravity effect");
				playerJumping();
			}
			else
			{
				System.out.println("finish first case gravity effect");
				//if max height reached then player stops jumping/ascending
				jumping = false;
			}
		}
		//if player is touching the ground but is not jumping then allow player to jump
		else if (!falling && jumping)
		{
			if((prevY - y) < maxJumpingHeight)
			{
				System.out.println("in second case gravity effect");
				playerJumping();
			}
			else
			{
				System.out.println("finish second case gravity effect");
				jumping = false;
			}
		}
		//if player is on a surface then set dy to 0
		else
		{
			super.setDy(0);
		}
	}
	
	//make the player fall
	public void playerFalling(){
		if(super.getDY() <= maxDy)
		{
			int newDy = super.getDY() + gravity;
			super.setDy(newDy);
		}
	}
	
	//make the player jump
	public void playerJumping(){

		System.out.println("in playerJumping");
		if(super.getDY() >= minDy)
		{
			int newDy = super.getDY() - gravity;
			super.setDy(newDy);
		}

		System.out.println("finish playerJumping");
	}
}
