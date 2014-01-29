package game.util;

public class ColliderBox extends Collider {
	private int x, y;
	private int width, height;
	
	public ColliderBox() {
		x = 0; 
		y = 0;
		width = 0;
		height = 0;
	}
	
	public ColliderBox(ColliderBox cb) {
		this.x = cb.getX();
		this.y = cb.getY();
		this.width = cb.getWidth();
		this.height = cb.getHeith();
	}
	
	public ColliderBox(Point point, int width, int height) {
		this.x = point.getX();
		this.y = point.getY();
		this.width = width;
		this.height = height;
	}
	
	public ColliderBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeith() {
		return height;
	}
	
	public void move(int deltaX, int deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}
	
	public void setPosition(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean overlaps(Collider c) {
		if(c instanceof ColliderBox) {
			if(x - c.getX() > 0 && x - c.getX() < width && y - c.getY() > 0 && y - c.getY() < height) {
				return true;
			}
		}
		return false;
	}
	
	public boolean overlaps(ColliderBox c) {
		return !(this.x + this.width < c.x || c.x + c.width < x || this.y + this.height < c.y || c.y + c.height < y);
	}

}
