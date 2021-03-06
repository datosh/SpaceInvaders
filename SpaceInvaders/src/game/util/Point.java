package game.util;

public class Point {
	int x;
	int y;
	
	public Point() {
		this.x = 0; 
		this.y = 0;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}	
}
