package model.fixed;

import model.StaticObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public class Chest extends StaticObject {

	// *************************************************
	// Fields

	// field for when to open the chest
	private boolean isOpen = false;

	// *************************************************
	// Constructor

	/**
	 * Constructor for the power up chest object
	 * 
	 * @param platform
	 *            - Platform to generate chest on
	 */
	public Chest(Platform platform) {

		super.setName("chest");

		// set the dimensions
		double width = 0.05 * super.getScreenWidth();
		double height = width * super.getScreenRatio();
		super.setSize((int) width, (int) height);

		super.setLocation((int) (platform.getX() + platform.getWidth() / 2 - this.getWidth() / 2),
				(int) (platform.getY() - this.getHeight()));
	}

	// *************************************************
	// Getters

	/**
	 * Gets the variable associated with the chest being open or not
	 * 
	 * @return isOpen - boolean saying if the chest is open
	 */
	public boolean getIsOpen() {
		return isOpen;
	}

	// *************************************************
	// Setters

	/**
	 * Sets the boolean for if the chest is open
	 * 
	 * @param b
	 *            - True/False for whether chest is Opened/Closed
	 */
	public void setIsOpen(boolean b) {
		isOpen = b;
	}

}
