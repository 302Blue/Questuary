package view.dynamic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class CrabImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	private int imgWidth = 80;
	private int imgHeight = 60;

	private int frameCount = 6;
	private int picNum = 0;

	private BufferedImage[] pics = new BufferedImage[frameCount];

	// *************************************************
	// Constructor

	/**
	 * Constructor for the crab image
	 */
	public CrabImage() {
		super.setName("greenCrab");

		// load in the image
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/greenCrab/Crabwalk(" + i + ")" + ".png");
			pics[i] = image;
		}

	}

	// *************************************************
	// Methods

	// The String imageFile is the input to the method, and is the file name
	/**
	 * Creates the image on screen
	 * 
	 * @param imageFile
	 * @return null or bufferedImage - bufferedImage is returned if there is no
	 *         exception with uploading the image
	 */
	private BufferedImage createImage(String imageFile) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(imageFile));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("Error with file upload");
			e.printStackTrace();
		}
		return null;
	}

	// return the image
	/**
	 * Shows the image from the array
	 * 
	 * @param direct
	 *            - Unnecessary parameter, here since super class requires
	 *            implementation with this parameter list
	 */
	public BufferedImage show(int direct) {
		// check to see if the next index exists
		return pics[picNum];
	}

	/**
	 * Increments through the crab images, creating an animation
	 * 
	 * @param canAnimate
	 *            - Unnecessary parameter, here since super class requires
	 *            implementation with this parameter list
	 */
	public void nextImage(boolean canAnimate) {
		// check to see if the next index exists
		if ((picNum + 1) < frameCount) {
			picNum++;
		} else {
			picNum = 0;
		}
	}

	// *************************************************
	// Getters

	// getter for the image width
	/**
	 * Gets the width of the image
	 */
	public int getWidth() {
		return imgWidth;
	}

	// getter for the image height
	/**
	 * Gets the height of the image
	 */
	public int getHeight() {
		return imgHeight;
	}
}
