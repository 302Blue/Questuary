package model.dynamic;

import java.awt.Rectangle;
import java.util.Random;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class EnemyCrab extends Enemy {

	//constructor
	public EnemyCrab(Rectangle platform) {
		System.out.println("Platform boundary: " + platform.getX() + "-" + (platform.getX()+platform.getWidth()) );
		//Set the name
		super.setName("greenCrab");
		//set the velocities
		super.setDy(20);
		super.setDx(4);
		//set the dimensions
		double width = 0.05 * super.getScreenWidth();
		double height = width * super.getScreenRatio();
		super.setSize((int)width, (int)height);
		//set the damage it can do
		super.setDamage(1);
		//set the limited boundaries
		super.setBoundary((int)platform.getX(), (int)(platform.getX() + platform.getWidth()));
		//randomize the direction it is facing
		Random random = new Random(System.nanoTime());
		if(random.nextBoolean())
		{
			super.setLocation((int)platform.getX(), (int)(platform.getY() - height + 5));
			super.setDirection(0);
		}
		else
		{
			super.setLocation((int)(platform.getX() + platform.getWidth()), (int)(platform.getY() - height + 5));
			super.setDirection(1);
		}
		
		//set it to killable
		super.setKillable(true);
	}
	
	//Methods
	@Override
	public void move() {
		System.out.println("Crab move called");
		System.out.println("Boundary: " + bound1 + "-" + bound2);
		System.out.println("Crab Coordinate: (" + super.getX() + "," + super.getY() + ")");
		//increment or decrement depending on the facing direction and boundaries
		if(!(super.isDead()))
		{
			if((super.getDirection() == 1) && (super.getX() + super.getDx() < (super.getBound2() - super.getWidth())))
			{
				incrementX();
			}
			else if((super.getDirection() == 0) && (super.getX() - super.getDx() > super.getBound1()))
			{
				decrementX();
			}
			else if((super.getX() + super.getDx()) >= (super.getBound2() - super.getWidth()))
			{
				super.setDirection(0);
			}
			else if((super.getX() - super.getDx()) <= super.getBound2())
			{
				super.setDirection(1);
			}
		}
		else
		{
			super.setDx(0);
			if(super.getY() < super.getScreenHeight())
			{
				incrementY();
			}
		}
	}
	
	private void incrementX() {
		int y = (int)(super.getY());
		int x = ((int)(super.getX())) + super.getDx();
		super.setLocation(x,y);
	}
	
	private void decrementX() {
		int y = (int)(super.getY());
		int x = ((int)(super.getX())) - super.getDx();
		super.setLocation(x,y);
	}
	
	private void incrementY() {
		int y = (int)(super.getY() + super.getDy());
		int x = ((int)(super.getX()));
		super.setLocation(x,y);
	}
	
}
