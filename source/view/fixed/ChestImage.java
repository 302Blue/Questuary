package view.fixed;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class ChestImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	private int imgWidth = 32;
	private int imgHeight = 32;

	private int frameCount = 2;
	private int picNum = 0;

	private BufferedImage[] pics = new BufferedImage[frameCount];

	/**
	 * Constructor for chest image
	 */
	public ChestImage() {
		super.setName("chest");

		int xLoc = 0;
		int yLoc = 0;

		// load in the image
		BufferedImage image = createImage("images/world/Chest.png");
		for (int i = 0; i < frameCount; i++) {
			pics[i] = image.getSubimage(xLoc, yLoc, imgWidth, imgHeight);
			xLoc = xLoc + 32;
		}
	}

	// *************************************************
	// Methods

	// The String imageFile is the input to the method, and is the file name
	/**
	 * Creates image
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
		return pics[picNum];
	}

	/**
	 * Goes to the next image in the image array
	 * 
	 * @param canAnimate
	 *            - Boolean for whether or not to switch to the open image
	 */
	public void nextImage(boolean canAnimate) {
		// check to see if the next index exists
		if (canAnimate) {
			picNum = 1;
		} else {
			picNum = 0;
		}
	}

	// *************************************************
	// Getters

	// getter for the image width
	/**
	 * Gets image width
	 */
	public int getWidth() {
		return imgWidth;
	}

	// getter for the image height
	/**
	 * Gets image height
	 */
	public int getHeight() {
		return imgHeight;
	}

}
