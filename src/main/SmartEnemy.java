package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {
	
	private Handler handler;
	private GameObject player;

	public SmartEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
			}
		}
		
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if (player.getX() < x) {
			velX = -1;
		} else if (player.getX() > x) {
			velX = 1;
		} else {
			velX = 0;
		}
		
		if (player.getY() < y) {
			velY = -1;
		} else if (player.getY() > y) {
			velY = 1;
		} else {
			velY = 0;
		}
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.05f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}

}
