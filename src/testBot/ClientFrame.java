package testBot;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class ClientFrame extends JFrame {
	private static final long serialVersionUID = -265801709437606023L;
	
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upPressed;
	public boolean downPressed;
	
	public ClientFrame() {
		addKeyListener(new KeyInputHandler());
	}

	private class KeyInputHandler extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
	} 

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
	}
}
}
