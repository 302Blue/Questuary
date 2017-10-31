package model;
import java.awt.Rectangle;

import model.sprites.fixed.Obstacle;

public class Platform extends Rectangle{
	
	
	public Platform(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
	}
	
	public Rectangle getRight() {
		return new Rectangle (x + width, y + 25, 1, 15);
	}
	
	public Rectangle getLeft() {
		return new Rectangle (x, y + 25, 1, 15);
	}
	
	
	
}
