package view.dynamic;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class OspreyImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	double width = (0.07 * super.getScreenWidth());
	double height = (0.07 * super.getScreenHeight());
	private int imgWidth = (int) (1.5 * width);
	private int imgHeight = (int) (1.5 * height);

	private int frameCount = 9;
	private int picNum = 0;

	private BufferedImage[][] pics = new BufferedImage[2][frameCount];

	// *************************************************
	// Constructor

	/**
	 * Constructor for the osprey image
	 */
	public OspreyImage() {
		super.setName("osprey");

		// load in the left facing image
		for (int i = (frameCount - 1); i >= 0; i--) {
			BufferedImage image = createImage("images/osprey/OspreyLeft(" + i + ")" + ".png");
			pics[0][i] = image;
		}

		// load in the right facing image
		for (int i = (frameCount - 1); i >= 0; i--) {
			BufferedImage image = createImage("images/osprey/OspreyRight(" + i + ")" + ".png");
			pics[1][i] = image;
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
	 *            - Which direction the osprey is facing
	 */
	public BufferedImage show(int direct) {
		return pics[direct][picNum];
	}

	/**
	 * Increments through the osprey images, creating an animation
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
