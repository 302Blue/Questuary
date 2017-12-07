package view.fixed;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class GroundImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	private int imgWidth = (int) (super.getScreenWidth() * 2);
	private int imgHeight = 100;

	private int frameCount = 1;
	private int picNum = 0;

	private BufferedImage[] pics = new BufferedImage[frameCount];

	/**
	 * Constructor for the image of the ground
	 */
	public GroundImage() {
		super.setName("ground");

		// load in the image
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/platforms/Beach_2000x100.png");
			pics[i] = image;
		}

	}

	// *************************************************
	// Methods

	// The String imageFile is the input to the method, and is the file name
	/**
	 * Creates image
	 * 
	 * @param imageFile
	 * @return bufferedImage - image returned if no exception thrown
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
	 * Shows the image
	 * 
	 * @param direct
	 *            - Unnecessary parameter, here since super class requires
	 *            implementation with this parameter list
	 */
	public BufferedImage show(int direct) {
		return pics[picNum];
	}

	/**
	 * Increments to next image in the image array
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
