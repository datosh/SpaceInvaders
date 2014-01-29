package game;

import game.util.ColliderBox;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile {
	private final int SCALED_X = 5;
	private final int SCALED_Y = 10;
	
	private boolean alive;
	private int positionX;
	private int positionY;
	//Made the speed attribute a variable. 
	//Enemy projectiles need positive speed => The projectile falls down
	//Player projectiles need negative speed => The projectile flies up
	private int speed;
	//Contains the image
	private Image image;
	private ColliderBox collider; 
	
	public Projectile() {
		alive = false;
	}
	
	public Projectile(int positionX, int positionY, int speed){
		//constructor Projectile
		this.positionX = positionX - SCALED_X / 2;
		this.positionY = positionY - SCALED_Y / 2;
		this.speed = speed;
		alive = true;
		
		try {
			image = ImageIO.read(new File("images/projectile.png")).getScaledInstance(SCALED_X, SCALED_Y, Image.SCALE_SMOOTH); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		collider = new ColliderBox(this.positionX, this.positionY, SCALED_X, SCALED_Y);
	}
	
	//Added the return value, needed to draw the image at the right position
	public int getX() {
		return positionX;
	}
	
	//Added the return value, needed to draw the image at the right position
	public int getY() {
		return positionY;
	}
	
	public ColliderBox getCollider() {
		return collider;
	}
	
	//Added funtion to return the value - need for later
	public boolean isAlive() {
		return alive;
	}
	
	//We need a function to return the image of the projectile
	public Image getImage() {
		return image;
	}
	
	public void toggleAlive() {
		alive = !alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void move(int height){
		//Move the Projectile by speed-pixels
		positionY += speed;
		if(alive) {
			collider.move(0, speed);
		}
		//If the projectile is out of the image, the image is no longer alive
		//attribute alive currently not used, but can be used to update the array
		//of projectiles and delete those that are dead => not alive. 
		if(positionY > height || positionY < 0) {
			alive = false;
		}
	}
}
