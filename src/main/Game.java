package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 6691247796639148462L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	
	// Initialize
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawn;
	private Menu menu;
	
	public enum STATE {
		Menu,
		Help,
		Game,
		End
	};
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		handler = new Handler(this);
		hud = new HUD();
		spawn = new Spawn(handler, hud);
		menu = new Menu(this, handler, hud);
		r = new Random();
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		new Window(WIDTH, HEIGHT, "Wave Game!", this);
		
		
		
		if(gameState == STATE.Game) {
			//add objects here
			//handler.addObject(new Player(100, 100, ID.Player, handler));
			//handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		} else {
			for(int i = 0; i < 30; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
		if(gameState == STATE.Game) {
			hud.tick();
			spawn.tick();
		} else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End ) {
			menu.tick();
		}
		
		if(HUD.HEALTH <= 0) {
			HUD.HEALTH = 100;
			gameState = STATE.End;
			handler.object.clear();
		}

	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.Game) {
			hud.render(g);
		} else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
