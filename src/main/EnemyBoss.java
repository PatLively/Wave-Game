package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBoss extends GameObject {
	//speed up boss see video 8 around 16min
	
	private Handler handler;
	Random r = new Random();
	
	private int timer = 70;
	private int timer2 = 50;

	public EnemyBoss(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 2;
	}
	
	public Rectangle getBounds() {
		
		return new Rectangle((int)x, (int)y, 96, 96);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(timer <= 0) velY = 0;
		else timer--;
		
		if(timer <=0 ) timer2--;
		if(timer2 <=0) {
			if(velX == 0) velX = 2;
			int spawn = r.nextInt(10);
			if(spawn == 0) handler.addObject(new EnemyBossBullet((int)x + 32, (int)y + 96, ID.BasicEnemy, handler));
			
		}
		
		//if(y <= 0 || y >= Game.HEIGHT-130) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH-105) velX *= -1;
		
		//handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.05f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);
		
	}

}
