package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private int buttonX = (Game.WIDTH / 2) - 95, playY = 150, helpY = 250, quitY = 350;
	private int bWidth = 200, bHeight = 64;
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			// play button
			if(mouseOver(mx, my, buttonX, playY, bWidth, bHeight)) {
				game.gameState = STATE.Select;
				
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
			
			// help button
			if(mouseOver(mx, my, buttonX, helpY, bWidth, bHeight)) {
				game.gameState = STATE.Help;
				AudioPlayer.getSound("menu_sound").play();
			}
			
			// quit button
			if(mouseOver(mx, my, buttonX, quitY, bWidth, bHeight)) {
				System.exit(1);
			}
		}
		
		// back button
		if(game.gameState == STATE.Help) {
			if(mouseOver(mx, my, buttonX, quitY, bWidth, bHeight)) {
				game.gameState = STATE.Menu;
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
		}
		
		// try over button
		if(game.gameState == STATE.End) {
			if(mouseOver(mx, my, buttonX, quitY, bWidth, bHeight)) {
				game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
		}
		
		if(game.gameState == STATE.Select) {
			// normal button
			if(mouseOver(mx, my, buttonX, playY, bWidth, bHeight)) {
				game.gameState = STATE.Game;
				handler.addObject(new Player(100, 100, ID.Player, handler));
				//add objects here
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
				
				game.diff = 0;
				
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
			
			// hard button
			if(mouseOver(mx, my, buttonX, helpY, bWidth, bHeight)) {
				game.gameState = STATE.Game;
				handler.addObject(new Player(100, 100, ID.Player, handler));
				//add objects here
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
				
				game.diff = 1;
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
			
			// back button
			if(mouseOver(mx, my, buttonX, quitY, bWidth, bHeight)) {
				game.gameState = STATE.Menu;
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
		}
			
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Menu) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			g.setColor(Color.white);
			
			g.setFont(fnt);
			g.drawString("Menu", 260, 100);
			
			g.setFont(fnt2);
			g.drawRect(buttonX, 150, bWidth, bHeight);
			g.drawString("Play", 295, 190);
			
			g.drawRect(buttonX, 250, bWidth, bHeight);
			g.drawString("Help", 295, 290);
			
			g.drawRect(buttonX, 350, bWidth, bHeight);
			g.drawString("Quit", 295, 390);
			
		} else if (game.gameState == STATE.Help) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arail", 1, 20);
			g.setColor(Color.white);
			
			g.setFont(fnt);
			g.drawString("Help", 260, 100);
			
			g.setFont(fnt3);
			g.drawString("Use WASD keys to move player and dodge enemies", 50, 200);
			
			g.setFont(fnt2);
			g.drawRect(buttonX, 350, bWidth, bHeight);
			g.drawString("Back", 295, 390);
		} else if (game.gameState == STATE.End) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arail", 1, 20);
			g.setColor(Color.white);
			
			g.setFont(fnt);
			g.drawString("Game Over", 260, 100);
			
			g.setFont(fnt3);
			g.drawString("You lost with a score of: " + hud.getScore(), 50, 200);
			
			g.setFont(fnt2);
			g.drawRect(buttonX, 350, bWidth, bHeight);
			g.drawString("Back", 295, 390);
		} else if (game.gameState == STATE.Select) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			g.setColor(Color.white);
			
			g.setFont(fnt);
			g.drawString("SELECT DIFFICULTY", 85, 100);
			
			g.setFont(fnt2);
			g.drawRect(buttonX, 150, bWidth, bHeight);
			g.drawString("Normal", 295, 190);
			
			g.drawRect(buttonX, 250, bWidth, bHeight);
			g.drawString("Hard", 295, 290);
			
			g.drawRect(buttonX, 350, bWidth, bHeight);
			g.drawString("Back", 295, 390);
			
		}
		
	}

}
