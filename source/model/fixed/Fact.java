package model.fixed;

import model.StaticObject;

public class Fact extends StaticObject {

	// *************************************************
	// Fields

	// x and y locations
	private static int xLoc;
	private static int yLoc;

	// scroll to next fact
	private static int picIter;

	// *************************************************
	// Constructor

	/**
	 * Constructor for the facts
	 * 
	 * @param introMode - Whether or not the game is in introMode
	 */
	public Fact(boolean introMode) {
		super.setName("fact");

		if (introMode) {
			picIter = 0;
		} else if (picIter < 10){
			picIter++;
		} else {
			picIter = 1;
		}

		// set the dimensions
		super.setSize(400, 100);

		super.setLocation(xLoc, yLoc);
	}
	
	// *************************************************
	// Getters


	/**
	 * Gets the x location of the fact
	 * @return xLoc - x location on screen
	 */
	public static int getxLoc() {
		return xLoc;
	}

	/**
	 * Gets the y location of the fact
	 * @return yLoc - y location on screen
	 */
	public static int getyLoc() {
		return yLoc;
	}

	/**
	 * Gets the current fact to display from the array of facts
	 * @return picIter - current fact being displayed
	 */
	public static int getPicIter() {
		return picIter;
	}
	
	// *************************************************
	// Setters
	
	public static void setxLoc(int i) {
		xLoc = i;
	}
	
	public static void setyLoc(int i) {
		yLoc = i;
	}


}
