package model.dynamic;

import java.awt.Rectangle;
import java.util.Random;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class EnemyCrab extends Enemy {

	// *************************************************
	// Constructor

	/**
	 * Constructor for the crab enemy
	 * 
	 * @param platform
	 *            - Platform to generate the enemy on
	 */
	public EnemyCrab(Rectangle platform) {

		System.out.println("Platform Boundary: " + platform.getX() + "-" + (platform.getX() + platform.getWidth()));
		// set the name
		super.setName("greenCrab");
		// set the velocities
		super.setDy(20);
		super.setDx(4);
		// set the dimensions
		double width = 0.05 * super.getScreenWidth();
		double height = width * super.getScreenRatio();
		super.setSize((int) width, (int) height);
		// set the damage it can do
		super.setDamage(1);
		// set the limited boundaries
		super.setBoundary((int) platform.getX(), (int) (platform.getX() + platform.getWidth()));
		// randomize the direction it is facing
		Random random = new Random(System.nanoTime());
		if (random.nextBoolean()) {
			super.setLocation((int) platform.getX(), (int) (platform.getY() - height + 5));
			super.setDirection(0);
		} else {
			super.setLocation((int) (platform.getX() + platform.getWidth()), (int) (platform.getY() - height + 5));
			super.setDirection(1);
		}

		// set it to killable
		super.setKillable(true);
	}

	// *************************************************
	// Methods

	@Override
	/**
	 * Overridden move method for green crabs
	 */
	public void move() {
		System.out.println("Executed: Crab move()");
		System.out.println("Boundary: " + bound1 + "-" + bound2);
		System.out.println("Crab Coordinate: (" + super.getX() + "," + super.getY() + ")");
		// increment or decrement depending on the facing direction and boundaries
		if (!(super.isDead())) {
			if ((super.getDirection() == 1)
					&& (super.getX() + super.getDx() < (super.getBound2() - super.getWidth()))) {
				incrementX();
			} else if ((super.getDirection() == 0) && (super.getX() - super.getDx() > super.getBound1())) {
				decrementX();
			} else if ((super.getX() + super.getDx()) >= (super.getBound2() - super.getWidth())) {
				super.setDirection(0);
			} else if ((super.getX() - super.getDx()) <= super.getBound2()) {
				super.setDirection(1);
			}
		} else {
			super.setDx(0);
			if (super.getY() < super.getScreenHeight()) {
				incrementY();
			}
		}
	}

	// *************************************************
	// Methods

	/**
	 * Increments the x position value for the crab
	 */
	private void incrementX() {
		int y = (int) (super.getY());
		int x = ((int) (super.getX())) + super.getDx();
		super.setLocation(x, y);
	}

	/**
	 * Decrements the x position value for the crab
	 */
	private void decrementX() {
		int y = (int) (super.getY());
		int x = ((int) (super.getX())) - super.getDx();
		super.setLocation(x, y);
	}

	/**
	 * Increments the y position value for the crab
	 */
	private void incrementY() {
		int y = (int) (super.getY() + super.getDy());
		int x = ((int) (super.getX()));
		super.setLocation(x, y);
	}

}
