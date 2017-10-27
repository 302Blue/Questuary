package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel {
	
	private JFrame frame;
	
	//fields related to pictures
	final int frameCount = 10;
	int picNum = 0;
	BufferedImage [][] pics;
	final static int imgWidth = 160;
	final static int imgHeight = imgWidth + 2;
	
	//player's x and y coordinates
	int playerX = 0;
	int playerY = 0;
	//player's direction
	int direction = 1;
	//ground
	Rectangle ground;
	
	//View constructor
	public View(int xBoundary, int yBoundary, String playerCharacter) {
		//load in the cat images
		pics = new BufferedImage[2][10];
		
		frame = new JFrame(); 
		
		//load in the images
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/" + playerCharacter + "/Walk (" + (i+1) + ")" + ".png
			pics[1][i] = image;
			//pics[i] = image.getSubimage(0,0,imgWidth, imgHeight);
		}

		for (int i = 0; i < frameCount; i++) {
			pics[0][i] = flip(pics[1][i]);
		}
		
		this.frame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Questuary Alpha");
		frame.setSize(new Dimension(xBoundary, yBoundary));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setVisible(true);
		
	}
	
	//update the image and repaint the image
	public void updateView(int playerX, int playerY, int direction, String playerCharacter) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.direction = direction;
		//load in the images
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/" + playerCharacter + "/Walk (" + (i + 1) + ")" + ".png");
			pics[1][i] = image;
			// pics[i] = image.getSubimage(0,0,imgWidth, imgHeight);
		}
		frame.repaint();
	}
	
	//setter for the ground
	public void setGroundImage(Rectangle ground) {
		this.ground = ground;
	}
	
	//The String imageFile is the file name
	private BufferedImage createImage(String imageFile) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(imageFile));
			return bufferedImage;
		} catch (IOException except) {
			System.out.println("Error with file upload");
			except.printStackTrace();		
		}
		return null;
	}
	
	//Override the JPanel's paint method
	@Override
	public void paint(Graphics graphic) {
		picNum = (picNum + 1) % frameCount;
		graphic.drawImage(pics[direction][picNum], playerX, playerY, imgWidth, imgHeight, this);
		graphic.fillRect((int)ground.getX(), (int)ground.getY() + imgHeight, (int)ground.getWidth(), (int)ground.getHeight());
	}
		
	private BufferedImage flip(BufferedImage image) {
		 int width = image.getWidth();
		 int height = image.getHeight();
		 
		 BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		 for(int y = 0; y < height; y++)
		 {
			 for(int lx = 0, rx = width-1; lx < width; lx++, rx--)
			 {
				 int p = image.getRGB(lx, y);
				 mimg.setRGB(rx, y, p);
			 }
		 }
		 return mimg;
	 }	
	
	//getter for the frame
	public JFrame getFrame() {
		return frame;
	}

}
