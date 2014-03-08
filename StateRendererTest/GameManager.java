package ecrosogames.staterenderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class GameManager extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private static GameManager manager;
	private GameState state = GameState.PRIMARY;

	private Thread thread;
	private Render1 r1;
	private Render2 r2;
	private boolean running = false;

	public enum GameState {
		PRIMARY, SECONDARY
	}

	public GameManager(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		manager = this;
		addKeyListener(new KeyHandler());
		r1 = new Render1(width, height);
		r2 = new Render2(width, height);
	}
	
	public static GameManager getInstance() {
		return manager;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Test");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public GameState getGameState() {
		return state;
	}

	public void setGameState(GameState state) {
		this.state = state;
	}

	public void run() {
		while (running) {
			update();
			render();
		}
		stop();
	}

	private void update() {
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if (state == GameState.PRIMARY) {
			r1.render(g);
		} else if (state == GameState.SECONDARY) {
			r2.render(g);
		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.dispose();
		bs.show();
	}
}
