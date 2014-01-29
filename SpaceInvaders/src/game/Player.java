package game;

import game.util.ColliderBox;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private int positionX;
	private int positionY;
	final private int SCALED_X = 64;
	final private int SCLAED_Y = 64;
	final private static int SHOOTINGDELAY = 500000000; //time in nanoseconds
	final private static int MAXPROJECTILES = 5;
	private long lastShot;
	private Projectile[]projectiles;
	private ColliderBox collider;

	private Image image;
	
	Player(int windowWidth, int windowHeight){
		try {
			image = ImageIO.read(new File("images/player.png")).getScaledInstance(SCALED_X, SCLAED_Y, Image.SCALE_SMOOTH);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastShot = System.nanoTime();
		projectiles = new Projectile[MAXPROJECTILES];
		for(int i = 0; i < MAXPROJECTILES; i++) {
			projectiles[i] = new Projectile();
		}
		positionX = windowWidth / 2 - SCALED_X / 2; 
		positionY = windowHeight - SCLAED_Y;
		collider = new ColliderBox(positionX + 4, positionY + SCLAED_Y / 3 * 2, SCALED_X - 8, SCLAED_Y / 3);
	}
	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	
	public ColliderBox getCollider() {
		return collider;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Projectile[] getProjectiles() {
		return projectiles;
	}
	
	//TODO: WORK ON LIKELYHOOD OF A SHOOT, MAYBE POE APPROACH WITH INIT VALUE 
	//GETS CALLED 60 TIMES PER SECOND. 
	public boolean shoot(){
		if(System.nanoTime() > lastShot + SHOOTINGDELAY) {
			for(int i = 0; i < projectiles.length; i++) {
				if(!projectiles[i].isAlive()) {
					projectiles[i] = new Projectile(this.positionX + this.image.getWidth(null) / 2, 
							this.positionY + this.image.getHeight(null) / 2, 
							-3);
					lastShot = System.nanoTime();
					return true;
				}
			}
		}
		return false;
	}
	
	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		collider.setPosition(x + 4, y + SCALED_X / 3 * 2);
	}
	
	public void move(int deltaX, int deltaY){
		positionX += deltaX;
		positionY += deltaY;
		collider.move(deltaX, deltaY);
	}
	
	public void die(){
		// Game reset if player dies
	}
}
