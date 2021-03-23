package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	private boolean[] keys  = new boolean[4];
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		
		keys[0] = false; // w
		keys[1] = false; // s
		keys[2] = false; // d
		keys[3] = false; // a
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					tempObject.setVelY(-5);
					keys[0] = true;
				}
				if(key == KeyEvent.VK_S) {
					tempObject.setVelY(5);
					keys[1] = true;
				}
				if(key == KeyEvent.VK_D) {
					tempObject.setVelX(5);
					keys[2] = true;
				}
				if(key == KeyEvent.VK_A) {
					tempObject.setVelX(-5);
					keys[3] = true;
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) {
					keys[0] = false; 
				}
				if(key == KeyEvent.VK_S) {
					keys[1] = false; 
				}
				if(key == KeyEvent.VK_D) {
					keys[2] = false; 
				}
				if(key == KeyEvent.VK_A) {
					keys[3] = false; 
				}
				
				// fixing the keyboard glitching
				//vertical movement
				if(!keys[0] && !keys[1]) tempObject.setVelY(0);
				
				//horizontal movement
				if(!keys[2] && !keys[3]) tempObject.setVelX(0);
			}
		}
		
		//use escape to exit game
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
	}
}
