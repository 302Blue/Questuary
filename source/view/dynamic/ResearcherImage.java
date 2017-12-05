package view.dynamic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */
public class ResearcherImage extends ImageObject {

	// *************************************************
	// Fields

	// set the image's dimensions
	private int imgWidth = (int) (super.getScreenWidth() * 0.1);
	private int imgHeight = (int) (imgWidth * super.getScreenRatio());

	private int frameCount = 10;
	private int picNum = 0;
	private BufferedImage[] idleImage = new BufferedImage[2];

	private BufferedImage[][] pics = new BufferedImage[2][frameCount];

	// *************************************************
	// Constructor

	/**
	 * Constructor for the researcher image
	 */
	public ResearcherImage() {
		super.setName("researcher");
		// load in the images
		idleImage[0] = flip(createImage("images/researcher/Idle__000.png"));
		idleImage[1] = createImage("images/researcher/Idle__000.png");
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/researcher/Run (" + (i + 1) + ")" + ".png");
			pics[1][i] = image;
		}

		for (int i = 0; i < frameCount; i++) {
			pics[0][i] = flip(pics[1][i]);
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

	// method used to flip the image
	/**
	 * Flips the image
	 * 
	 * @param image
	 * @return mimg - flipped image
	 */
	private BufferedImage flip(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < height; y++) {
			for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
				int p = image.getRGB(lx, y);
				mimg.setRGB(rx, y, p);
			}
		}
		return mimg;
	}

	// return the image in the array
	/**
	 * Shows the image from the array
	 * 
	 * @param direct
	 *            - Which direction the researcher is facing
	 */
	public BufferedImage show(int direct) {
		if(picNum == 11) {
			picNum = 0;
			return idleImage[direct];
		}
		return pics[direct][picNum];
	}

	// increment through the ResearcherImage
	/**
	 * Increments through the researcher images, creating an animation
	 * 
	 * @param canAnimate
	 *            - Boolean for whether or not to show the next image
	 */
	public void nextImage(boolean canAnimate) {
		if (canAnimate) {
			picNum = (picNum + 1) % frameCount;
		}else {
			picNum = 11;
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
