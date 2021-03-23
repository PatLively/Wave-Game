package main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	//change health bar color as you die episode #6 
	
	// pretty static so don't need to make new HUD() to reference health
	public static float HEALTH = 100;
	
	private int score = 0;
	private int level = 1;

	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 100);
		
		score++;
	}
	
	public void render(Graphics g) {
		// health bar
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200, 32);
		g.setColor(Color.green);
		g.fillRect(15, 15, (int)HEALTH*2, 32);
		g.setColor(Color.white);
		g.drawRect(15, 15, 200, 32);
		
		// text
		g.drawString("Score: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 80);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
