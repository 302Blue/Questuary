package view;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public abstract class ImageObject {

	// *************************************************
	// Fields

	// get screen's dimensions
	private static double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static double screenRatio = screenWidth / screenHeight;

	private String name;

	// *************************************************
	// Methods

	public abstract BufferedImage show(int direct);

	public abstract void nextImage(boolean canAnimate);

	public abstract int getWidth();

	public abstract int getHeight();

	// *************************************************
	// Getters

	/**
	 * Gets the width of the screen
	 * @return screenWidth - double that is the screen width for whatever resolution the user has
	 */
	public double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Gets the screen's height
	 * @return screenHeight - double that is the screen height for whatever resolution the user has
	 */
	public double getScreenHeight() {
		return screenHeight;
	}

	/**
	 * Gets the screen ratio
	 * @return screenRatio - double for the screen ratio based on the resolution of the screen
	 */
	public double getScreenRatio() {
		return screenRatio;
	}

	// *************************************************
	// Setters

	/**
	 * Sets the name of an image
	 * @param n
	 */
	protected void setName(String n) {
		name = n;
	}

}
