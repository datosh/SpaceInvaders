package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;	//UniqueID. Needed for compatibility with older version (Not interesting for us) 

	private final boolean DEBUG = false;
	
	public static final int WIDTH = 160;				//Width of the game's window (in pixel)
	public static final int HEIGHT = WIDTH / 4 * 3;		//Calculate the height of the game's window using the width (current format 4:3)
	public static final int SCALE = 3;					//Scales the game for a better view on big monitors. 
	public static final String NAME = "SPACE INVADERS";	//Specifies the name of the window, generally the game's name. 
	
	//Don't really need to look into this
	private JFrame frame;								//Frame in which the game is running. 
	public boolean running = false;						//Used to stop the game, i.e. when the player pauses the game or when the game is over. 
	public int tickCount = 0;							//Used to show the performance of the game in the console. 

	public InputHandler input;

	/* Space Invader's Attributes ___________________________________________________ */
	private static final int ENEMY_ROWS = 8;
	private static final int ENEMY_COLUMNS = 4;
	private static final int ENEMY_MOVEMENT_SPEED = 1;
	private static final Color backgroundColor = Color.BLACK;
	
	private Player player;
	private Enemy[][] enemys;
	
	//movement related
	private int dx;
	private int nextRow;
	
	//for the scoreboard
	private int score;
	private int level;
	private int enemysAlive;
	private int numberPlayerProjectilesAlive;
	private long startingTime;
	

	public Game() {
		//Set the minimum, maximum and preferred size of the window to size we specified => User cannot change the size
		//of the window. Therefore we don't need to worry about dynamic windows sizes. 
		this.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));		
		this.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);									//Creates the Frame. Need as the argument the name of the window/game. 

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//Specifies what happens when the window close button is pressed. 
		frame.setLayout(new BorderLayout());
		frame.setBackground(backgroundColor);

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void init() {
		input = new InputHandler(this);
		player = new Player(this.getWidth(), this.getHeight());
		
		enemys = new Enemy[ENEMY_COLUMNS][ENEMY_ROWS];
		int divider = 25;
		enemys[0][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider);
		enemys[0][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider);
		enemys[0][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider);
		enemys[0][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider);
		enemys[0][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider);
		enemys[0][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider);
		enemys[0][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider);
		enemys[0][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider);
		
		enemys[1][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 3);
		enemys[1][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 3);
		enemys[1][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 3);
		enemys[1][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 3);
		enemys[1][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 3);
		enemys[1][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 3);
		enemys[1][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 3);
		enemys[1][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 3);
		
		enemys[2][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 5);
		enemys[2][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 5);
		enemys[2][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 5);
		enemys[2][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 5);
		enemys[2][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 5);
		enemys[2][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 5);
		enemys[2][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 5);
		enemys[2][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 5);
		
		enemys[3][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 7);
		enemys[3][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 7);
		enemys[3][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 7);
		enemys[3][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 7);
		enemys[3][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 7);
		enemys[3][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 7);
		enemys[3][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 7);
		enemys[3][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 7);
		
		startingTime = System.nanoTime();
		score = 0;
		level = 1;
		enemysAlive = 0;
		numberPlayerProjectilesAlive = 0;
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		// NanoSeconds of one Frame in 60 FPS
		double nsPerTick = 1_000_000_000D / 40D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta--;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}

	// Updated Game Variables
	public void tick() {
		tickCount++;

		/*
		 * 1. MAKE INPUT HANDLING 
		 */
		
		//MOVE LEFT AND RIGHT
		if(input.right_arrow.isPressed() || input.right.isPressed()) {
			dx = 3;
		} else if (input.left_arrow.isPressed() || input.left.isPressed()) {
			dx = -3;
		} else {
			dx = 0;
		}
		 
		//SHOOT
		if(input.space.isPressed()) {
			if(player.shoot()) {
				System.out.println("SHOOT");
			}
		}
		
		/*
		 * 2. MAKE LOGICAL UPDATES 
		 */
		
		player.move(dx, 0);
		
		updateEnemyPositions();
		
		enemysAlive = updateAliveEnemys(enemys);
		numberPlayerProjectilesAlive = numberPlayerProjectilesAlive(player);
		
		
		if(hitPlayer(player, enemys)) {
			gameOver();
		}
		if(allEnemysDead(enemys)) {
			nextRound();
		}
		hitEnemy(player, enemys);
		
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				enemys[i][j].shoot();
				enemys[i][j].getProjectile().move(this.getHeight());
			}
		}
		
		for(int i = 0; i < player.getProjectiles().length; i++) {
			player.getProjectiles()[i].move(this.getHeight());
		}
		

	}
	
	// Prints the Updated Game Variables
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		
		
		/*
		 * 3. DRAW HERE
		 */
		
		//For example:
		
		drawBackground(g);
		drawPlayer(g, player);
		drawEnemys(g, enemys);
		drawProjectiles(g, enemys, player);
		if(DEBUG) {
			drawCollider(g, enemys, player);
		}
		drawScore(g, score, startingTime, level, enemysAlive, numberPlayerProjectilesAlive);
		
		/*
		 * TIL HERE
		 */
		
		g.dispose();
		bs.show();
	}
	
	/* LOGICAL UPDATE METHODS ____________________________________ */

	private void nextRound() {	
		for(int i = 0; i < player.getProjectiles().length; i++) {
			player.getProjectiles()[i].setAlive(false);
		}
		
		int divider = 25;
		enemys[0][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider);
		enemys[0][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider);
		enemys[0][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider);
		enemys[0][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider);
		enemys[0][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider);
		enemys[0][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider);
		enemys[0][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider);
		enemys[0][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider);
		
		enemys[1][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 3);
		enemys[1][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 3);
		enemys[1][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 3);
		enemys[1][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 3);
		enemys[1][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 3);
		enemys[1][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 3);
		enemys[1][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 3);
		enemys[1][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 3);
		
		enemys[2][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 5);
		enemys[2][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 5);
		enemys[2][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 5);
		enemys[2][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 5);
		enemys[2][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 5);
		enemys[2][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 5);
		enemys[2][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 5);
		enemys[2][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 5);
		
		enemys[3][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 7);
		enemys[3][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 7);
		enemys[3][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 7);
		enemys[3][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 7);
		enemys[3][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 7);
		enemys[3][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 7);
		enemys[3][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 7);
		enemys[3][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 7);

		level++;
	}
	
	private void gameOver() {
		for(int i = 0; i < player.getProjectiles().length; i++) {
			player.getProjectiles()[i].setAlive(false);
		}
		
		int divider = 25;
		enemys[0][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider);
		enemys[0][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider);
		enemys[0][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider);
		enemys[0][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider);
		enemys[0][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider);
		enemys[0][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider);
		enemys[0][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider);
		enemys[0][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider);
		
		enemys[1][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 3);
		enemys[1][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 3);
		enemys[1][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 3);
		enemys[1][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 3);
		enemys[1][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 3);
		enemys[1][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 3);
		enemys[1][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 3);
		enemys[1][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 3);
		
		enemys[2][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 5);
		enemys[2][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 5);
		enemys[2][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 5);
		enemys[2][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 5);
		enemys[2][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 5);
		enemys[2][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 5);
		enemys[2][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 5);
		enemys[2][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 5);
		
		enemys[3][0] = new Enemy(this.getWidth() / 11 * 1, this.getWidth() / divider * 7);
		enemys[3][1] = new Enemy(this.getWidth() / 11 * 2, this.getWidth() / divider * 7);
		enemys[3][2] = new Enemy(this.getWidth() / 11 * 3, this.getWidth() / divider * 7);
		enemys[3][3] = new Enemy(this.getWidth() / 11 * 4, this.getWidth() / divider * 7);
		enemys[3][4] = new Enemy(this.getWidth() / 11 * 5, this.getWidth() / divider * 7);
		enemys[3][5] = new Enemy(this.getWidth() / 11 * 6, this.getWidth() / divider * 7);
		enemys[3][6] = new Enemy(this.getWidth() / 11 * 7, this.getWidth() / divider * 7);
		enemys[3][7] = new Enemy(this.getWidth() / 11 * 8, this.getWidth() / divider * 7);

		player.setPosition(213, 306);
		
		level = 0;
		startingTime = System.nanoTime();
		score = 0;
	}

	private void updateEnemyPositions() {
		nextRow = 0;
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(!enemys[i][j].isAlive()) {
					continue;
				}
				//Update all positions and if one reaches the bound the variable is set
				nextRow |= enemys[i][j].move(ENEMY_MOVEMENT_SPEED, this.getWidth());
			}
		}
		if(nextRow == 1 || nextRow == -1) {
			for(int i = 0; i < enemys.length; i++) {
				for(int j = 0; j < enemys[i].length; j++) {
					enemys[i][j].oneRowDown(nextRow);
					enemys[i][j].move(ENEMY_MOVEMENT_SPEED * 2, this.getWidth());
				}
			}
		}
	}
	
	private void hitEnemy(Player player, Enemy[][] enemys){
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				for(int k = 0; k < player.getProjectiles().length; k++) {
					if(player.getProjectiles()[k].getCollider() != null) {
						if(enemys[i][j].getCollider().overlaps(player.getProjectiles()[k].getCollider()) && 
								enemys[i][j].isAlive() && 
								player.getProjectiles()[k].isAlive()) {
							enemys[i][j].toggleAlive();
							player.getProjectiles()[k].toggleAlive();
							score += enemys[i][j].getScore();
						}
					}
				}
			}
		}
	}

	private int numberPlayerProjectilesAlive(Player player) {
		int amount = 0;
		for(int i = 0; i < player.getProjectiles().length; i++) {
			if(player.getProjectiles()[i].isAlive()) {
				amount++;
			}
		}
		return amount;
	}
	
	private int updateAliveEnemys(Enemy[][] enemys) {
		int amount = 0;
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(enemys[i][j].isAlive()) {
					amount++;
				}
			}
		}
		return amount;
	}
	
	private boolean allEnemysDead(Enemy[][] enemys) {
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(enemys[i][j].isAlive()) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean hitPlayer(Player player, Enemy[][] enemys){
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(!enemys[i][j].getProjectile().isAlive()) {
					continue;
				} else if(enemys[i][j].getProjectile().getCollider().overlaps(player.getCollider())) {
					System.out.println("PLAYER HITTED");
					return true;
				}
			}
		}
		return false;
	}

	/* DRAW METHODS _____________________________________________ */
	
	private void drawBackground(Graphics g){
		g.setColor(backgroundColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight()); 
	}
	
	
	private void drawPlayer(Graphics g, Player player){
		g.drawImage(player.getImage(), 
				player.getX(), 
				player.getY(), 
				this);
	}
	
	private void drawEnemys(Graphics g, Enemy[][] enemys){
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(enemys[i][j].isAlive()) {
					g.drawImage(enemys[i][j].getImage(), enemys[i][j].getX(), enemys[i][j].getY(), this);
				}
			}
		}
	}
	
	private void drawProjectiles(Graphics g, Enemy[][] enemys, Player player){
		//Draw Player's Projectiles
		for(int i = 0; i < player.getProjectiles().length; i++) {
			if(player.getProjectiles()[i].isAlive()) {
				g.drawImage(player.getProjectiles()[i].getImage(), 
						player.getProjectiles()[i].getX(), 
						player.getProjectiles()[i].getY(), 
						this);
			}
		}
		
		//Draw Enemys's Projectiles
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				g.drawImage(enemys[i][j].getProjectile().getImage(), 
						enemys[i][j].getProjectile().getX(), 
						enemys[i][j].getProjectile().getY(), 
						this);
			}
		}
	}
	
	private void drawCollider(Graphics g, Enemy[][] enemys, Player player) {
		g.setColor(Color.GREEN);
		
		//Players collider
		g.drawRect(player.getCollider().getX(), 
				player.getCollider().getY(), 
				player.getCollider().getWidth(), 
				player.getCollider().getHeith());
		
		//Enemys collider
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(enemys[i][j].isAlive()) {
					g.drawRect(enemys[i][j].getCollider().getX(), 
							enemys[i][j].getCollider().getY(), 
							enemys[i][j].getCollider().getWidth(), 
							enemys[i][j].getCollider().getHeith());
				}
			}
		}
		
		//Enemy Projectile's collider
		for(int i = 0; i < enemys.length; i++) {
			for(int j = 0; j < enemys[i].length; j++) {
				if(!enemys[i][j].getProjectile().isAlive()) {
					continue;
				}
				g.drawRect(enemys[i][j].getProjectile().getCollider().getX(), 
						enemys[i][j].getProjectile().getCollider().getY(), 
						enemys[i][j].getProjectile().getCollider().getWidth(), 
						enemys[i][j].getProjectile().getCollider().getHeith());
			}
		}
		
		//Players projectile's collider
		for(int i = 0; i < player.getProjectiles().length; i++) {
			if(player.getProjectiles()[i].isAlive()) {
				g.drawRect(player.getProjectiles()[i].getCollider().getX(), 
						player.getProjectiles()[i].getCollider().getY(), 
						player.getProjectiles()[i].getCollider().getWidth(), 
						player.getProjectiles()[i].getCollider().getHeith());
			}
		}
	}

	private void drawScore(Graphics g, int score, long startingTime, int level, int enemysAlive, int numberPlayerProjectilesAlive) {
		//Set Color
		g.setColor(Color.WHITE);
		
		//Draw Alive Projectiles
		g.drawString("Projectiles: " + numberPlayerProjectilesAlive + "/5", 0, 10); //TODO: Replace 5 by dynamic value
		
		//Draw Score
		g.drawString("Score: " + score, 100, 10);
		
		//Draw Time
		int seconds = 0, minutes = 0;
		seconds = (int)((System.nanoTime() - startingTime) / 1_000_000_000);
		minutes = seconds / 60;
		seconds %= 60;
		g.drawString("Time: " + minutes + ":" + seconds, 200, 10);
		
		//Draw Level
		g.drawString("Level: " + level, 300, 10);
		
		//Draw Enemys Alive
		g.drawString("Enemys Left: " + enemysAlive, 400, 10);
		
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
}
