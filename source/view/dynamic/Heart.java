package view.dynamic;

public class Heart {
	int imgWidth = 50;
	int imgHeight = 50;
	
	int x;
	int y;
	
	public Heart(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getImgWeight() {
		return imgWidth;
	}
	
	public int getImgHeight() {
		return imgHeight;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
