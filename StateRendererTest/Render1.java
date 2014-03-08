package ecrosogames.staterenderer;

import java.awt.Color;
import java.awt.Graphics;

public class Render1 {

	private int width, height;
	
	public Render1(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, width, height);
	}
}
