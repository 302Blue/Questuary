import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel{
	
	private JFrame frame;
	//fields related to pictures
	final int frameCount = 10;
	int picNum = 0;
	BufferedImage[][] pics;
	
	//boolean determines player should animate
	boolean animate = false;
	
	double ratio = screenWidth/screenHeight; 
	int imgWidth;
	int imgHeight;
	
	public static int getImgwidth() {
		return imgWidth;
	}

	//player's x and y coordinates
	int playerX = 0;
	int playerY = 0;
  
	//player's direction
	int direct = 1;
  
	//ground 
	Rectangle ground;
	
	public View(int xBoundary, int yBoundary, String playerCharacter) {
		//load in the cat images
		pics = new BufferedImage[2][10];
		
		frame = new JFrame();
		
		//load in the images
		for (int i = 0; i < frameCount; i++) {
			BufferedImage image = createImage("images/" + playerCharacter + "/Walk (" + (i+1) + ")" + ".png");
			pics[1][i] = image;
		}
		
		for (int i = 0; i < frameCount; i++) {
			pics[0][i] = flip(pics[1][i]);
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Questary Alpha");
		frame.setSize(new Dimension(,(int)screenHeight));
		setSize(new Dimension(model.getScreenHeight(), model.getScreenWidth()));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(this);
		frame.setVisible(true);
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
		 for(int y = 0; y < height; y++) {
			 for(int lx = 0, rx = width-1; lx < width; lx++, rx--)
			 {
				 int p = image.getRGB(lx, y);
				 mimg.setRGB(rx, y, p);
			 }
		 }
		 return mimg;
	 }
  
  	/* //Override the JPanel's paint method
	 @Override
	 public void paint(Graphics g) {
		 g.setColor(Color.blue);
		 g.fillRect(playerX, playerY,imgWidth,imgHeight);
		 
		 g.drawImage(pics[direct][picNum], playerX, playerY, imgWidth , imgHeight,this);

		 g.setColor(Color.gray);
		 g.fillRect((int)ground.getX(), (int)ground.getY(), (int)ground.getWidth(), (int)ground.getHeight());
	 } */
  
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
	  
  
	 //getter for the frame
	 public JFrame getFrame() {
		 return frame;
	 }
	 
	//setter for the ground
	public void setGroundImage(Rectangle ground) {
		this.ground = ground;
	}
	
	//setter for picNum
	public void setPicNum() {
		if(animate) {
			picNum = (picNum + 1) % frameCount;
		}
	}
	//setter for animation
	public void setAnimation(boolean b) {
		animate = b;
	}
  
}
