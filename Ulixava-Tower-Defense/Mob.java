package ecrosogames.towerdefense;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Mob extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public int xc, yc;
	public int health = 100;
	public int healthSpace = 3, healthHeight = 6;
	public int mobSize = 52;
	public int mobWalk = 0;
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;
	public int mobID = Value.mobAir;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	
	public Mob() {
	}
	
	public void spawnMob(int mobID) {
		for (int y = 0; y < Screen.room.block.length; y++) {
			if (Screen.room.block[y][0].groundID == Value.groundRoad) {
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, mobSize, mobSize);
				xc = 0;
				yc = y;
			}
		}
		this.mobID = mobID;
		this.health = mobSize;
		inGame = true;
	}
	
	public void deleteMob() {
		inGame = false;
		direction = right;
		mobWalk = 0;
		
		Screen.room.block[0][0].getMoney(mobID);
	}
	
	public int walkFrame = 0, walkSpeed = 40;
	
	public void physic() {
		if (walkFrame >= walkSpeed) {
			if (direction == right) {
				x++;
			} else if (direction == upward) {
				y--;
			} else if (direction == downward) {
				y++;
			} else if (direction == left) {
				x--;
			}
			
			mobWalk++;
			if (mobWalk == Screen.room.blockSize) {
				if (direction == right) {
					xc++;
					hasRight = true;
				} else if (direction == upward) {
					yc--;
					hasUpward = true;
				} else if (direction == downward) {
					yc++;
					hasDownward = true;
				} else if (direction == left) {
					xc--;
					hasLeft = true;
				}
				
				if (!hasUpward) {
					try {
						if (Screen.room.block[yc + 1][xc].groundID == Value.groundRoad) {
							direction = downward;
						}
					} catch (Exception e) {
					}
				}
				
				if (!hasDownward) {
					try {
						if (Screen.room.block[yc - 1][xc].groundID == Value.groundRoad) {
							direction = upward;
						}
					} catch (Exception e) {
					}
				}
				
				if (!hasLeft) {
					try {
						if (Screen.room.block[yc][xc + 1].groundID == Value.groundRoad) {
							direction = right;
						}
					} catch (Exception e) {
					}
				}
				
				if (!hasRight) {
					try {
						if (Screen.room.block[yc][xc - 1].groundID == Value.groundRoad) {
							direction = left;
						}
					} catch (Exception e) {
					}
				}
				
				if (Screen.room.block[yc][xc].airID == Value.airTurret) {
					deleteMob();
				}
				
				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}
			walkFrame = 0;
		} else walkFrame++;
	}
	
	public void loseHealth(int amount) {
		health -= amount;
		checkDeath();
	}
	
	public void checkDeath() {
		if (health <= 0) {
			deleteMob();
		}
	}
	
	public boolean isDead() {
		if (inGame) {
			return false;
		} else {
			return true;
		}
	}
	
	public void draw(Graphics g) {
		if (inGame) {
			g.drawImage(Screen.tileset_mob[mobID], x, y, width, height, null);
			
			g.setColor(new Color(200, 0, 0));
			g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);
			
			g.setColor(Color.green);
			g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);
			
			g.setColor(Color.black);
			g.drawRect(x, y - (healthSpace + healthHeight), health, healthHeight);
		}
		
	}
}
