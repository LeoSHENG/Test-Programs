package ecrosogames.towerdefense;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static Image[] tileset_ground = new Image[10];
	public static Image[] tileset_air = new Image[10];
	public static Image[] tileset_res = new Image[10];
	public static Image[] tileset_mob = new Image[10];
	
	public static int myWidth, myHeight;
	public static int coinage = 10, health = 100;
	public static int killed = 0, killsToWin = 0, level = 1, maxLevel = 3;
	public static int winTime = 4000, winFrame = 0;
	
	public Thread thread = new Thread(this);
	
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static boolean isWin = false;
	
	public static Point mse = new Point(0, 0);
	
	public static Room room;;
	public static Save save;
	public static Store store;
	
	public static Mob[] mobs = new Mob[100];
	
	public Screen(Frame frame) {
		frame.addMouseListener(new KeyHandle());
		frame.addMouseMotionListener(new KeyHandle());
		define();
		thread.start();
	}
	
	public static void hasWon() {
		if (killed == killsToWin) {
			isWin = true;
			killed = 0;
		}
	}
	
	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();
		
		coinage = 10;
		health = 100;
		
		for (int i = 0; i < tileset_ground.length; i++) {
			tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
		}
		for (int i = 0; i < tileset_air.length; i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
		}
		
		tileset_res[0] = new ImageIcon("res/cell.png").getImage();
		tileset_res[1] = new ImageIcon("res/heart.png").getImage();
		tileset_res[2] = new ImageIcon("res/coin.png").getImage();
		
		tileset_mob[0] = new ImageIcon("res/mob.png").getImage();
		
		save.loadSave(new File("save/mission" + level + ".txt"));
		
		for (int i = 0; i < mobs.length; i++) {
			mobs[i] = new Mob();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			isFirst = false;
		}
		
		g.setColor(new Color(70, 70, 70));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1, room.block[room.worldHeight - 1][0].y + room.blockSize);
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0, room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize);
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y + room.blockSize, room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize);
		
		room.draw(g);
		
		for (int i = 0; i < mobs.length; i++) {
			if (mobs[i].inGame) {
				mobs[i].draw(g);
			}
		}
		
		store.draw(g);
		
		if (health < 1) {
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(Color.white);
			g.setFont(new Font("Courier New", Font.BOLD, 86));
			g.drawString("Game Over!", 80, 250);
		}
		
		if (isWin) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.black);
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (level == maxLevel) {
				g.drawString("You won the whole game.. you can exit now..", 80, 250);
			} else {
				g.drawString("You Won! New Level Loading...", 80, 250);
			}
		}
	}
	
	public int spawnTime = 1200, spawnFrame = 0;
	
	public void mobSpawner() {
		if (spawnFrame >= spawnTime) {
			spawnFrame = 0;
			for (int i = 0; i < mobs.length; i++) {
				if (!mobs[i].inGame) {
					mobs[i].spawnMob(0);
					break;
				}
			}
		} else spawnFrame++;
	}
	
	@Override
	public void run() {
		while (true) {
			if (health > 0 && !isWin) {
				room.physic();
				
				for (int i = 0; i < mobs.length; i++) {
					if (mobs[i].inGame) mobs[i].physic();
				}
				
				mobSpawner();
			} else {
				if (isWin) {
					if (winFrame >= winTime) {
						if (level == maxLevel) {
							System.exit(0);
						} else {
							level += 1;
							isWin = false;
							define();
						}
						winFrame = 0;
					} else {
						winFrame++;
					}
				}
			}
			repaint();
			
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
