package view.dynamic;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import view.ImageObject;

/**
 * This class is in charge of the cat image.
 * <p>
 * When this object is created it loads in the file images of cat and
 * manipulates the image to create the desirable direction image. This class
 * will also return the next image to be drawn. Pics is a 2D array that holds
 * the right and left facing images of cat.
 * 
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 *
 */
public class CatImage extends ImageObject {

	// *************************************************
	// Fields

	// get screen's dimensions
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenRatio = screenWidth / screenHeight;

	// set the image's dimensions
	private int imgWidth = (int) (screenWidth * 0.1);
	private int imgHeight = (int) (imgWidth * screenRatio);
	private int frameCount = 4;
	private int picNum = 0;

	private BufferedImage[][] pics = new BufferedImage[2][frameCount];

	public CatImage() {
		super.setName("cat");
		// load in the images
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/cat/Walk (" + (i + 1) + ")" + ".png");
			pics[1][i] = image;
		}

		for (int i = 0; i < frameCount; i++) {
			pics[0][i] = flip(pics[1][i]);
		}
	}

	// *************************************************
	// Methods

	// The String imageFile is the input to the method, and is the file name
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

	// increment through the CatImage
	public void nextImage(boolean canAnimate) {
		if (canAnimate) {
			picNum = (picNum + 1) % frameCount;
		}
	}

	// return the image in the array
	public BufferedImage show(int direct) {
		return pics[direct][picNum];
	}

	// *************************************************
	// Getters

	// getter for the image dimensions
	public int getWidth() {
		return imgWidth;
	}

	public int getHeight() {
		return imgHeight;
	}

}
