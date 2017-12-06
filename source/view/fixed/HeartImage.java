package view.fixed;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class HeartImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	private int imgWidth = 48;
	private int imgHeight = 48;

	int x;
	int y;

	private int frameCount = 1;
	private int picNum = 0;

	private BufferedImage[] pics = new BufferedImage[frameCount];

	// *************************************************
	// Constructor

	/**
	 * Heart image constructor
	 * 
	 * @param x
	 *            - X Location
	 * @param y
	 *            - Y Location
	 */
	public HeartImage(int x, int y) {
		this.x = x;
		this.y = y;

		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/world/Heart.png");
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
	 * @return bufferedImage - image created if exception is not thrown
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

	// increment through the image
	/**
	 * Increment through image array
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

	/**
	 * Gets x coordinate
	 * 
	 * @return x - int for x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets y coordinate
	 * 
	 * @return y - int for y coordinate
	 */
	public int getY() {
		return y;
	}

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
