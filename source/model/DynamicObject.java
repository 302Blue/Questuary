import java.awt.Rectangle;

//the Rectangle has data fields for the coordinates of x and y 
public abstract class DynamicObject extends Rectangle{
	
	private int xVelocity;
	private int yVelocity;
	//a direction of 0 is left and 1 is right
	private int direction;
	
	
	
	//setters for the x and y velocities
	public void setDx(int dx){
		xVelocity = dx;
	}
	public void setDy(int dy){
		yVelocity = dy;
	}
	//setter for direction
	public void setDirection(int d)
	{
		direction = d;
	}
	
	//getters for the x and y velocities
	public int getDx(){
		return xVelocity;
	}
	public int getDY(){
		return yVelocity;
	}
	//getters for the direction
	public int getDirection(){
		return direction;
	}
	//get the string version of direction
	public String getDirectionString(){
		if(direction == 0)
		{
			return "LEFT";
		}
		else
		{
			return "RIGHT";
		}
	}
	
	//method moves the player
	public void move(){
		x += xVelocity;
		y += yVelocity;
	}
}
