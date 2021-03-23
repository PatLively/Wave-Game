package main;

import java.awt.Graphics;
import java.util.LinkedList;

import main.Game.STATE;

public class Handler {
	
	private Game game;
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public void tick() {
		for(int i =0; i < object.size(); i++) {
			this.object.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i =0; i < object.size(); i++) {
			this.object.get(i).render(g);
			if (this.object.get(i).getId() == ID.MenuParticle && !(game.gameState == STATE.Menu)) {
				removeObject(this.object.get(i));
			}
		}
	}
	
	public void clearEnemies() {
		for(int i =0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				object.clear();
				addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, this));
			}
		}
	}
	
	public void addObject(GameObject tempObject) {
		this.object.add(tempObject);
	}
	
	public void removeObject(GameObject tempObject) {
		this.object.remove(tempObject);
	}
}
