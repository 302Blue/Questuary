package view.dynamic;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ImageObject;

public class CrabImage extends ImageObject{

	// get screen's dimensions
	private double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private double screenRatio = screenWidth / screenHeight;

	// set the image's dimensions
	//private int imgWidth = (int) (screenWidth * 0.1);
	//private int imgHeight = (int) (imgWidth * screenRatio);
	private int imgWidth = 60;
	private int imgHeight = 40;
	private int frameCount = 6;
	private int picNum = 0;
	
	private BufferedImage[] pics = new BufferedImage[frameCount];
	
	public CrabImage() {
		super.setName("greenCrab");
		
		//load in the image
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/greenCrab/crabwalk(" + i + ")" + ".png");
			pics[i] = image;
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
		//check to see if the next index exists
		return pics[picNum];
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
