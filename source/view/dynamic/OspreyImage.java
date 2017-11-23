package view.dynamic;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

public class OspreyImage extends ImageObject{

	// get screen's dimensions
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenRatio = screenWidth / screenHeight;

	// set the image's dimensions
	//private int imgWidth = (int) (screenWidth * 0.1);
	//private int imgHeight = (int) (imgWidth * screenRatio);
	private int imgWidth = 120;
	private int imgHeight = 100;
	private int frameCount = 9;
	private int picNum = 0;
	
	private BufferedImage[][] pics = new BufferedImage[2][frameCount];
	
	public OspreyImage() {
		super.setName("osprey");
		
		//load in the left facing image
		for (int i = (frameCount - 1); i >= 0; i--) {
			BufferedImage image = createImage("images/osprey/OspreyLeft(" + i + ")" + ".png");
			pics[0][i] = image;
		}
		
		//load in the right facing image
		for (int i = (frameCount - 1); i >= 0; i--) {
			BufferedImage image = createImage("images/osprey/OspreyRight(" + i + ")" + ".png");
			pics[1][i] = image;
		}
	}
	
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
	
	//return the image
	public BufferedImage show(int direct) {
		return pics[direct][picNum];
	}
	
	//setter
	public void nextImage(boolean canAnimate) {
		//check to see if the next index exists
		if((picNum + 1) < frameCount)
		{
			picNum++;
		}
		else
		{
			picNum = 0;
		}
	}
	//getters
	public int getWidth() {
		return imgWidth;
	}
	
	public int getHeight() {
		return imgHeight;
	}
}
