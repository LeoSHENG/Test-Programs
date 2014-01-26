package ecrosogames.towerdefense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public Rectangle towerSquare;
	public int towerSquareSize = 100;
	public int groundID;
	public int airID;
	public int loseTime = 40, loseFrame;
	
	public int shotMob = -1;
	public boolean shooting = false;
	
	public Block(int x, int y, int width, int height, int groundID, int airID) {
		setBounds(x, y, width, height);
		towerSquare = new Rectangle(x - (towerSquareSize / 2), y - (towerSquareSize / 2), width + towerSquareSize, height + towerSquareSize);
		this.groundID = groundID;
		this.airID = airID;
	}
	
	public void draw(Graphics g) {
		g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);
		
		if (airID != Value.airAir) {
			g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
		}
	}
	
	public void physic() {
		if (shotMob != -1 && towerSquare.intersects(Screen.mobs[shotMob])) {
			shooting = true;
		} else {
			shooting = false;
		}
		
		if (!shooting) {
			if (airID != Value.airAir) {
				for (int i = 0; i < Screen.mobs.length; i++) {
					if (Screen.mobs[i].inGame) {
						if (towerSquare.intersects(Screen.mobs[i])) {
							shooting = true;
							shotMob = i;
						}
					}
				}
			}
		}
		
		if (shooting) {
			if (loseFrame >= loseTime) {
				Screen.mobs[shotMob].loseHealth(1);
				loseFrame = 0;
			} else {
				loseFrame++;
			}
			if (Screen.mobs[shotMob].isDead()) {
				shooting = false;
				shotMob = -1;
				
				Screen.killed++;
				
				Screen.hasWon();
			}
		}
	}
	
	public void getMoney(int mobID) {
		Screen.coinage += Value.deathReward[mobID];
	}
	
	public void fight(Graphics g) {
		if (Screen.isDebug) {
			if (airID == Value.airTowerLazer) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
			
		}
		if (shooting) {
			g.setColor(new Color(255, 255, 0));
			g.drawLine(x + (width / 2), y + (height / 2), Screen.mobs[shotMob].x + Screen.mobs[shotMob].width / 2, Screen.mobs[shotMob].y
					+ Screen.mobs[shotMob].height / 2);
		}
	}
}
