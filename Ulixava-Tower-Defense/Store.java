package ecrosogames.towerdefense;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Store {
	
	public static int shopWidth = 8;
	public static int buttonSize = 52;
	public int cellSpace = 2;
	public static int awayFromRoom = 20;
	public static int iconSize = 20;
	public static int iconSpace = 3;
	public static int itemIn = 4;
	public static int heldID = -1;
	public static int realID = -1;
	public static int[] buttonID = { Value.airTowerLazer, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir,
			Value.airTrashcan };
	public static int[] buttonPrice = { 10, 0, 0, 0, 0, 0, 0, 0 };
	
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	
	public boolean holdsItem = false;
	
	public Store() {
		define();
	}
	
	public void click(int mouseButton) {
		if (mouseButton == 1) {
			for (int i = 0; i < button.length; i++) {
				if (buttonID[i] != Value.airAir) {
					if (button[i].contains(Screen.mse)) {
						if (buttonID[i] == Value.airTrashcan) {
							holdsItem = false;
							heldID = Value.airAir;
						} else {
							heldID = buttonID[i];
							holdsItem = true;
							realID = i;
						}
					}
				}
			}
			
			if (holdsItem) {
				if (Screen.coinage >= buttonPrice[realID]) {
					for (int y = 0; y < Screen.room.block.length; y++) {
						for (int x = 0; x < Screen.room.block[0].length; x++) {
							if (Screen.room.block[y][x].contains(Screen.mse)) {
								if (Screen.room.block[y][x].groundID != Value.groundRoad && Screen.room.block[y][x].airID == Value.airAir) {
									Screen.room.block[y][x].airID = heldID;
									Screen.coinage -= buttonPrice[realID];
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void define() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle(Screen.myWidth / 2 - ((shopWidth * (buttonSize + cellSpace)) / 2) + ((buttonSize + cellSpace) * i),
					(Screen.room.block[Screen.room.worldHeight - 1][0].y) + Screen.room.blockSize + cellSpace + awayFromRoom, buttonSize, buttonSize);
		}
		
		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < button.length; i++) {
			if (button[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 150));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
			if (buttonID[i] != Value.airAir) g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width
					- (itemIn * 2), button[i].height - (itemIn * 2), null);
			g.setColor(Color.white);
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (buttonPrice[i] > 0) g.drawString(Integer.toString(buttonPrice[i]), button[i].x + itemIn, button[i].y + itemIn + 10);
		}
		
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonHealth.y + button[0].height - iconSize, buttonCoins.width, buttonCoins.height, null);
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString(Integer.toString(Screen.health), buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + buttonHealth.height * 2 / 3);
		g.drawString(Integer.toString(Screen.coinage), buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + buttonCoins.height * 2 + 5);
		
		if (holdsItem) {
			g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn, Screen.mse.y
					- ((button[0].height - (itemIn * 2)) / 2), button[0].width - (itemIn * 2), button[0].height - (itemIn * 2), null);
		}
	}
}
