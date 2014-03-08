package ecrosogames.staterenderer;

import java.awt.Color;
import java.awt.Graphics;

public class Render2 {

	private int width, height;
	
	public Render2(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, width, height);
	}
}
