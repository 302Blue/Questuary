package model.dynamic;

import java.awt.Rectangle;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class EnemyOsprey extends Enemy {
	public EnemyOsprey(int xbound,int ybound) {
		System.out.println("Platform boundary: " + xbound + "-" + ybound );
		super.setName("osprey");
		super.setLocation(300, 0);
		super.setDy(8);
		super.setDx(4);
		//set the dimensions
		double width = 0.1 * super.getScreenWidth();
		double height = width * super.getScreenRatio();
		super.setSize((int)width, (int)height);
		super.setDamage(1);
		super.setBoundary(xbound,ybound);
		super.setDirection(1);
	}
	
	@Override
	public void move() {
		System.out.println("Osprey move called");
		System.out.println("Boundary: " + bound1 + "-" + bound2);
		System.out.println("Osprey Coordinate: (" + super.getX() + "," + super.getY() + ")");
		if((super.getX() + super.getDx()) > getBound1())
		{
			super.setDx(-4);
			super.setDirection(0);
		}
		else if((super.getX() - super.getDx()) < 0)
		{
			super.setDx(4);
			super.setDirection(1);
		}
		else if((super.getY() + super.getDy()) > getBound2())
		{
			super.setDy(-8);
		}
		else if ((super.getY() - super.getDy()) < 0)
		{
			super.setDy(8);
		}
		
		int x = (int)super.getX() + super.getDx();
		int y = (int)super.getY() + super.getDy();
		super.setLocation(x, y);
	}
}
