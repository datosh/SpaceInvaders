package game.util;

public abstract class Collider {
	abstract public int getX();
	abstract public int getY();
	abstract public boolean overlaps(Collider c);

}
