package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	public InputHandler(Game game)
	{
		game.addKeyListener(this);
	}
	
	public class Key {
		private boolean pressed = false;

		public boolean isPressed()
		{
			return pressed;
		}
		
		public void toggle(boolean isPressed) {
			pressed = isPressed;
		}
	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key left_arrow = new Key();
	public Key up_arrow = new Key();
	public Key right_arrow = new Key();
	public Key down_arrow = new Key();
	public Key space = new Key();
	//1. Create new Key here for example: 
	public Key tab = new Key();

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent arg0) {

	}

	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D) {
			right.toggle(isPressed);
		}
		//2. Add the right KeyEvent at this position for example: 
		if (keyCode == KeyEvent.VK_TAB) {
			tab.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			left_arrow.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_UP) {
			up_arrow.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			right_arrow.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			down_arrow.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(isPressed);
		}
	}
}
