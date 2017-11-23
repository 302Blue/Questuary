package view;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * @author Andrew Baldwin, Matt Billone, David Chan, Akash Sharma, Vineeth Gutta
 */

public abstract class ImageObject {
	
	private String name;
	
	protected void setName(String n) {
		name = n;
	}
	
	public abstract BufferedImage show(int direct);
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void nextImage(boolean canAnimate);
	
}
