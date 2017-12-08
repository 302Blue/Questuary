package model;

import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public abstract class StaticObject extends Rectangle {

	// *************************************************
	// Fields
	
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenRatio = screenHeight / screenWidth;
	
	String name;

	// *************************************************
	// Getters

	/**
	 * Gets the width of the screen
	 * @return screenWidth - width of the user's screen
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Gets the screens height
	 * @return screenHeight - height of the user's screen
	 */
	protected double getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Gets the ratio between screen and image size based on resolution
	 * @return screenRatio - ratio for screen and image sizes based on the user's screen resolution
	 */
	protected double getScreenRatio() {
		return screenRatio;
	}

	/**
	 * Gets the name of the object
	 * @return name - name of the object
	 */
	public String getName() {
		return name;
	}

	// *************************************************
	// Setters

	/**
	 * Sets the name of the object 
	 * @param n - name for the object
	 */
	protected void setName(String n) {
		name = n;
	}

}
