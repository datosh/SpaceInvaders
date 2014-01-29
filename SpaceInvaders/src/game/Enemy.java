package game;

import game.util.ColliderBox;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Enemy {
	final private int SCALED_X = 32;
	final private int SCALED_Y = 32;
	final private int PIXELS_PER_ROW = 5;
	final private int ANIMATION_DELAY = 0_600_000_000; //1 second
	final private double PROB_TO_SHOOT = 0.00045f;
	private boolean alive;
	private long lastAnimation;
	private int positionX;
	private int positionY;
	private int movingLeft;		//1 => TRUE; -1 => FALSE
	private int currentImage;
	private int score;
	private Projectile projectile;
	private Image[] image;
	private ColliderBox collider;
	
	Enemy(int x, int y){
		image = new Image[2];
		try {
			image[0] = ImageIO.read(new File("images/enemy_1.png"));
			image[1] = ImageIO.read(new File("images/enemy_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image[0] = image[0].getScaledInstance(SCALED_X, SCALED_Y, Image.SCALE_SMOOTH);
		image[1] = image[1].getScaledInstance(SCALED_X, SCALED_Y, Image.SCALE_SMOOTH);

		this.positionX = x;
		this.positionY = y;
		alive = true;
		projectile = new Projectile();
		movingLeft = 1;
		currentImage = 0;
		lastAnimation = System.nanoTime();
		collider = new ColliderBox(positionX, positionY, SCALED_X, SCALED_Y);
		score = 10;
	}
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	public boolean isAlive(){
		 return alive;
	}
	public Image getImage() {
		return image[currentImage];
	}
	public Projectile getProjectile() {
		return projectile;
	}
	
	public ColliderBox getCollider() {
		return collider;
	}
	
	public int getScore() {
		return score;
	}
	
	public void toggleAlive() {
		alive = !alive;
	}
	 
	public boolean shoot(){
		if(projectile.isAlive())
			return false;
		if(!alive)
			return false;
		
		Random r = new Random();
		double zeroToOne = r.nextDouble();
		if(zeroToOne < PROB_TO_SHOOT) {
			projectile = new Projectile(this.positionX + image[0].getWidth(null) / 2, 
					this.positionY + image[0].getHeight(null), 
					3);
			return true;
		}
		return false;
	}
	 
	//Move left and if a border is hit return the direction
	//for the next row
	public int move(int dx, int windowWidth){
		positionX += dx*movingLeft;
		collider.move(dx*movingLeft, 0);
		if (positionX > windowWidth - SCALED_X) {
			return -1;
		}
		if (positionX < 0) {
			return 1;
		}
		if(System.nanoTime() > lastAnimation + ANIMATION_DELAY) {
			currentImage ^= 1;
			lastAnimation = System.nanoTime();
		}
		
		return 0;
	}
	
	//Change the direction and move down
	public void oneRowDown(int movingLeft) {
		currentImage ^= 1;
		this.movingLeft = movingLeft;
		positionY += PIXELS_PER_ROW;
		collider.move(0, PIXELS_PER_ROW);
	}
	
}
